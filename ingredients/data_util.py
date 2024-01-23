"""
Helper functions for extracting and processing ingredient data.
Contents: 
    - 
"""
from typing import Optional
from constants import *
import regex



"""
Extracts ingredient data from raw json files into
dictionaries that contain necessary fields to build 
a PocketAlchemy Ingredient.
"""
def extract_ingredients(jsonData) -> list[dict]:
    extracted_ingredients = list()
    for food in jsonData:
        ingredient = dict()
        # get description
        description: str = food.get(DESCRIPTION_KEY)
        # get category
        category: str = food.get("foodCategory").get(DESCRIPTION_KEY)
        # get fdcId for future reference
        fdc_id: str = food.get(FDC_ID_KEY)
        # get list of food nutrients
        nutrient_list: list = food.get("foodNutrients")

        # nutrient info
        calories: dict = {}
        protein: dict = {}
        fat: dict = {}
        carbs: dict = {}
        fiber: dict = {}
        sugars: dict = {}
        sodium: dict = {}
        measures: list[dict] = []

        # portion info
        food_portions = food.get("foodPortions")
        for food_portion in food_portions:
            measure = {}
            measure_value = food_portion.get("value")
            measure[MEASURE_UNIT_KEY] = food_portion.get("measureUnit").get("abbreviation")
            if measure[MEASURE_UNIT_KEY] != "undetermined" and measure_value != None:
                measure[MEASURE_VALUE_KEY] = measure_value / measure_value
                measure[MEASURE_GWEIGHT_KEY] = round(food_portion.get("gramWeight") / measure_value, 4)
                measures.append(measure)

        # get nutrient data 
        for nutrient in nutrient_list:
            nutrient_name: str = nutrient.get("nutrient").get("name")
            unit_name: str = nutrient.get("nutrient").get("unitName")
            amount = nutrient.get("amount")

            match nutrient_name:
                case "Energy" if unit_name == "kcal":
                    calories[MEASURE_VALUE_KEY] = amount
                    calories[MEASURE_UNIT_KEY] = unit_name
                case "Energy (Atwater General Factors)":
                    calories[MEASURE_VALUE_KEY] = amount
                    calories[MEASURE_UNIT_KEY] = unit_name
                case "Protein": 
                    protein[MEASURE_VALUE_KEY] = amount
                    protein[MEASURE_UNIT_KEY] = unit_name
                case "Total lipid (fat)" | "Total fat (NLEA)":
                    fat[MEASURE_VALUE_KEY] = amount
                    fat[MEASURE_UNIT_KEY] = unit_name
                case "Carbohydrate, by difference":
                    carbs[MEASURE_VALUE_KEY] = amount
                    carbs[MEASURE_UNIT_KEY] = unit_name
                case "Fiber, total dietary":
                    fiber[MEASURE_VALUE_KEY] = amount
                    fiber[MEASURE_UNIT_KEY] = unit_name
                case "Sugars, Total":
                    sugars[MEASURE_VALUE_KEY] = amount
                    sugars[MEASURE_UNIT_KEY] = unit_name
                case "Sodium, Na":
                    sodium[MEASURE_VALUE_KEY] = amount
                    sodium[MEASURE_UNIT_KEY] = unit_name
                case _:
                    pass
        
        # insert ingredient values
        ingredient[DESCRIPTION_KEY] = description
        ingredient[FDC_ID_KEY] = fdc_id 
        ingredient[CATEGORY_KEY] = category 
        ingredient[CALORIES_KEY] = calories 
        ingredient[PROTEIN_KEY] = protein 
        ingredient[CARBS_KEY] = carbs 
        ingredient[FAT_KEY] = fat 
        ingredient[SODIUM_KEY] = sodium 
        ingredient[SUGARS_KEY] = sugars 
        ingredient[FIBER_KEY] = fiber
        ingredient[MEASURES_KEY] = measures
        ingredient[KEYWORDS_KEY] = [] # keyword generation happens after description doctoring

        if ingredient[DESCRIPTION_KEY] != "":
            extracted_ingredients.append(ingredient)

    return extracted_ingredients



"""
START OF FILTERING METHODS
"""

"""
Checks if a name (description) has any references 
to brands.
"""
def description_has_brand(description: str) -> bool:
    for brand_name in BRAND_NAMES:
        lowercase_description = description.lower()
        if lowercase_description.find(brand_name) != -1:
            return True
        
    return False


    
"""
Returns a filtered list of ingredients. 
Filters by name (description) and category, if name contains 
a brand name or an excluded category, will 
filter it out.
"""
def filter_ingredients(ingredients: list[dict]) -> list[dict]:
    filtered_ingredients = []

    for ingredient in ingredients:
        category: str = ingredient.get(CATEGORY_KEY)
        description: str = ingredient.get(DESCRIPTION_KEY) 
        # check if category is in excluded list
        is_category_excluded: bool = category in EXCLUDED_CATEGORIES
        # check if description contains a brand name
        is_branded: bool = description_has_brand(description)
        if not is_branded and not is_category_excluded:
            filtered_ingredients.append(ingredient)

    return filtered_ingredients



"""
START OF DESCRIPTION PROCESSING METHODS
"""

def replace_all(
    description: str, 
    pattern_strs: list[str], 
    replacement: str) -> str:
    for pattern_str in pattern_strs:
        pattern = regex.compile(pattern_str)
        description = pattern.sub(replacement, description)

    return description



""""
Given an ingredient, processes the 
description to remove unnecessary and awkward 
words. Updates the processed ingredient dictionary.
"""
def update_description(ingredient: dict):
    description: str = ingredient.get(DESCRIPTION_KEY)
    category = ingredient.get(CATEGORY_KEY)
    # category-ignorant description processing
    for pattern_string in DESCRIPTION_PATTERNS:
        pattern = regex.compile(pattern_string)
        # Not all Frankfurter entries are in a meat category 
        # so I put this replacement here to avoid repetition.
        if pattern_string == "Frankfurter":
            description = pattern.sub("Hot dog", description)
        else:
            # replace patterns with ""
            description = pattern.sub("", description)

    # category specific replacements
    if category == "Baked Products":
        description = replace_all(description, BAKED_PATTERNS, "")
    # Vegetables
    elif category == "Vegetables and Vegetable Products":
        for pattern_string in VEGETABLE_PATTERNS:
            pattern = regex.compile(pattern_string)
            if pattern_string == ", cooked, boiled":
                description = pattern.sub(", boiled", description)
            else:
                description = pattern.sub("", description)
        split_desc = description.split(", ")
        if len(split_desc) >= 2:
            if split_desc[1] == "raab":
                # fix spelling of broccoli rabe
                split_desc[1] = "rabe"
                description = ", ".join(split_desc)
    # Beef and Pork specific processing
    elif category in MEAT_CATEGORIES:
        for pattern_string in MEAT_PATTERNS:
            pattern = regex.compile(pattern_string)
            if pattern_string == r",.separable lean only":
                # reduce wordiness
                description = pattern.sub(", lean", description)
            elif pattern_string == r",*?.([0-9]{1,}%).*?fat":
                # reduce lean% / fat% to lean%
                description = pattern.sub(r", \1 lean", description)
            elif pattern_string == r",.Australian":
                # replace Australian with AUS
                description = pattern.sub(", AUS", description)
            elif pattern_string == r",.New.Zealand":
                # replace Australian with AUS
                description = pattern.sub(", NZ", description)
            else:
                description = pattern.sub("", description)
    # Beverages
    elif category == "Beverages":
        for pattern_string in BEVERAGE_PATTERNS:
            pattern = regex.compile(pattern_string)
            if pattern_string == r"Alcoholic.[bB]everage[s]*":
                # reduce wordiness
                description = pattern.sub("Alcohol", description)
            else:
                description = pattern.sub("", description)
    # Cereals
    elif category == "Breakfast Cereals":
        description = replace_all(description, CEREAL_PATTERNS, "")
    # Dairy, Egg 
    elif category == "Dairy and Egg Products":
        description = replace_all(description, DAIRY_EGG_PATTERNS, "")
    # Fat, Oil 
    elif category == "Fats and Oils":
        for pattern_string in FAT_OIL_PATTERNS:
            pattern = regex.compile(pattern_string)
            description = pattern.sub("", description)
            split_desc: list[str] = description.split(",")
            # flip basic oil names i.e. Oil, olive -> olive oil
            if len(split_desc) == 2 and split_desc[0] == "Oil":
                description = split_desc[1] + " oil"
    # FISH, SHELLFISH
    elif category == "Finfish and Shellfish Products":
        description = replace_all(description, FISH_SHELLFISH_PATTERNS, "")
    # FRUIT
    elif category == "Fruits and Fruit Juices":
        description = replace_all(description, FRUIT_PATTERNS, "")
    # LEGUMES
    elif category == "Legumes and Legume Products":
        description = replace_all(description, LEGUME_PATTERNS, "")
    # NUTS, SEEDS
    elif category == "Nut and Seed Products":
        description = replace_all(description, NUT_SEED_PATTERNS, "")
    # SNACKS
    elif category == "Snacks":
        description = replace_all(description, SNACK_PATTERNS, "")
    # Soups, Sauces
    elif category == "Soups, Sauces, and Gravies":
        description = replace_all(description, SOUP_SAUCE_PATTERNS, "")
    # Sweets
    elif category == "Sweets":
        description = replace_all(description, SWEETS_PATTERNS, "")
        split_desc = description.split(", ")

        match (split_desc[0]):
            case "Candies":
                split_desc[0] = "Candy"
            case "Ice creams":
                split_desc[0] = "Ice Cream"
            case "Puddings":
                split_desc[0] = "Pudding"
            case "Desserts":
                split_desc[0] = "Dessert"
            case "Syrups":
                split_desc[0] = "Syrup"
            case "Sugars":
                split_desc[0] = "Sugar"
            case "Egg custards": 
                split_desc[0] = "Egg Custard"
            case "Frozen Yogurts":
                split_desc[0] = "Frozen Yogurt"
            case "Frostings":
                split_desc[0] = "Frosting"
            case "Sweeteners": 
                split_desc[0] = "Sweetener"
            case _: pass
        
        description = ", ".join(split_desc)

    # clean up white space
    description: str = description.strip()

    # remove trailing commas
    if description.endswith(','):
        description = description[0:len(description)-1]
    # update desc.
    ingredient[DESCRIPTION_KEY] = description
    # add keywords for updated desc.
    ingredient[KEYWORDS_KEY] = generate_keywords(description)


"""
Tries to resolve multiple entries with the same 
description to a single entry. Some duplicates are
a result of combining Foundation and Legacy USDA FoodData
datasets, if this is the case the foundation food is returned.
Some duplicates are a result of doctoring the name, when this is 
the case all ingredients will be returned, to prevent arbitrarily losing data,
as I intended to link to the USDA reference from each ingredient in the app,
so I'm preserving duplicates since they will reference similar products,
allowing the user to investigate the differences themselves, rather than arbitrarily
deciding what ingredient(s) to include. Foundation foods can be
identified by a 7 digit FDC ID, legacy foods 
have a 6 digit FDC ID.

Returns list of ingredient dicts.
"""
def resolve_multiple(ingredients: list[dict]) -> list[dict]:
    for ingredient in ingredients:
        # check for foundation food
        num_id_digits = len(str(ingredient.get(FDC_ID_KEY)))
        if num_id_digits == 7:
            return [ ingredient ]
    # if no foundation ingredient is present returns all
    # to prevent arbitrary exclusions of similar entries
    return ingredients


"""
Generates list of keywords for ingredient description.
"""
def generate_keywords(description: str) -> list[str]:
    description_words = description.lower().split(", ")
    keywords = []
    in_order_description = " ".join(description_words[:4])
    for i in range(0,2):
        for j in range(i, len(in_order_description)):
            keywords.append(in_order_description[i:j+1])
# reverse first two words of description and create key words
    reversed_description = " ".join(reversed(description_words[:2]))
    for i in range(0,2):
        for j in range(i, len(reversed_description)):
            keywords.append(reversed_description[i:j+1])
    return keywords