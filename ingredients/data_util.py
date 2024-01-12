"""
Helper functions for extracting and processing ingredient data
"""
from constants import *

"""
Extracts ingredient data from raw json files into
dictionaries that contain neccessary fields to build 
a PocketAlchemy Ingredient.
"""
def extractIngredients(jsonData) -> list[dict]:
    extractedIngredients = list()
    for food in jsonData:
        ingredient = dict()
        # get description
        description = food.get(DESCRIPTION_KEY)
        # get category
        category = food.get("foodCategory").get(DESCRIPTION_KEY)
        # get fdcId for future reference
        fdcId = food.get(FDC_ID_KEY)
        # get list of food nutrients
        nutrientList = food.get("foodNutrients")

        # nutrient info
        calories: dict = {}
        protein: dict = {}
        fat: dict = {}
        carbs: dict = {}
        fiber: dict = {}
        sugars: dict = {}
        sodium: dict = {}
        portions: list[dict] = []

        # portion info
        foodPortions = food.get("foodPortions")
        for foodPortion in foodPortions:
            portion = {}
            portion[PORTION_VALUE_KEY] = foodPortion.get("value")
            portion[PORTION_ABBR_KEY] = foodPortion.get("measureUnit").get("abbreviation")
            portion[PORTION_GWEIGHT_KEY] = foodPortion.get("gramWeight")
            portions.append(portion)

        # get nutrient data 
        for nutrient in nutrientList:
            nutrientName = nutrient.get("nutrient").get("name")
            unitName = nutrient.get("nutrient").get("unitName")
            amount = nutrient.get("amount")

            match nutrientName:
                case "Energy (Atwater General Factors)":
                    calories[MEASURE_VALUE_KEY] = amount
                    calories[MEASURE_UNIT_KEY] = unitName
                case "Protein": 
                    protein[MEASURE_VALUE_KEY] = amount
                    protein[MEASURE_UNIT_KEY] = unitName
                case "Total lipid (fat)":
                    fat[MEASURE_VALUE_KEY] = amount
                    fat[MEASURE_UNIT_KEY] = unitName
                case "Carbohydrate, by difference":
                    carbs[MEASURE_VALUE_KEY] = amount
                    carbs[MEASURE_UNIT_KEY] = unitName
                case "Fiber, total dietary":
                    fiber[MEASURE_VALUE_KEY] = amount
                    fiber[MEASURE_UNIT_KEY] = unitName
                case "Sugars, Total":
                    sugars[MEASURE_VALUE_KEY] = amount
                    sugars[MEASURE_UNIT_KEY] = unitName
                case "Sodium, Na":
                    sodium[MEASURE_VALUE_KEY] = amount
                    sodium[MEASURE_UNIT_KEY] = unitName
                case _:
                    pass
        
        # insert ingredient values
        ingredient[DESCRIPTION_KEY] = description
        ingredient[FDC_ID_KEY] = fdcId 
        ingredient[CATEGORY_KEY] = category 
        ingredient[CALORIES_KEY] = calories 
        ingredient[PROTEIN_KEY] = protein 
        ingredient[CARBS_KEY] = carbs 
        ingredient[FAT_KEY] = fat 
        ingredient[SODIUM_KEY] = sodium 
        ingredient[SUGARS_KEY] = sugars 
        ingredient[FIBER_KEY] = fiber
        ingredient[PORTIONS_KEY] = portions

        if ingredient[DESCRIPTION_KEY] != "":
            extractedIngredients.append(ingredient)

    return extractedIngredients

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