# script for loading USDA source files and extracting
# neccessary ingredient data: Name, 
#!/usr/bin/python3.12
import json

# open source files to read from
foundationJson = open("data/FoodData_Central_foundation_food_json_2023-04-20/foundationFoods.json")
foundationFoods: list[dict] = json.load(foundationJson).get("FoundationFoods")

# key for Energy (Atwater Specific Factors)
ENERGY_ASF_KEY = "Energy (Atwater Specific Factors)"
MEASURE_VALUE_KEY = "value"
MEASURE_UNIT_KEY = "unit"

NAME_KEY = "name"
FDC_ID_KEY = "fdcId"
CATEGORY_KEY = "category"
CARBS_KEY = "carbs"
CALORIES_KEY = "calories"
SODIUM_KEY = "sodium"
FAT_KEY = "fat"
FIBER_KEY = "fiber"
SUGARS_KEY = "sugars"
PROTEIN_KEY = "protein"
PORTION_VALUE_KEY = "portion-value"
PORTION_ABBR_KEY = "portion-abbr"
PORTION_GWEIGHT_KEY = "portion-gweight"
PORTIONS_KEY = "portions"

# extracted food info
extractedIngredients: list[dict] = list()
# tracking names for dev
names: list[str] = list()

def nameFilter(s: str) -> bool:
    if s.startswith("with") or s.startswith("not"):
        return False
    elif s.startswith("from") or s.startswith("commercial"):
        return False
    elif s == "nuts" or s == "seeds":
        return False
    else:
        return True


for food in foundationFoods:
    ingredient = dict()
    name = food.get("description")
    # for testing
    names.append(name)
    # get category
    category = food.get("foodCategory").get("description")
    # get fdcId for future reference
    fdcId = food.get("fdcId")
    # get list of food nutrients
    nutrientList = food.get("foodNutrients")

    calories: dict = {}
    protein: dict = {}
    fat: dict = {}
    carbs: dict = {}
    fiber: dict = {}
    sugars: dict = {}
    sodium: dict = {}
    portions: list[dict] = []

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
            case "Energy (Atwater Specific Factors)": 
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
    
    ingredient[NAME_KEY] = name 
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

    extractedIngredients.append(ingredient)

finalIngredients: list[dict] = list()

# finalizes extracted recipes
for ingredient in extractedIngredients:
    # only take ingredients not dishes
    name = ingredient[NAME_KEY]
    if not name.startswith("Restaurant"):
        nameWords = name.casefold().rsplit(", ")
        constructedName = []
        numNameWords = len(nameWords)
        if numNameWords >= 3:
            word = nameWords[2]
            if nameFilter(word):
                constructedName.append(word)
        if numNameWords >= 2:
            word = nameWords[1]
            if nameFilter(word):
                constructedName.append(word)
        if numNameWords >= 1:
            word = nameWords[0]
            if nameFilter(word):
                constructedName.append(word)

        # update ingredient names
        ingredient[NAME_KEY] = " ".join(str(x) for x in constructedName)
        finalIngredients.append(ingredient)

finalIngredientsJson = json.dumps(finalIngredients, indent=4)

with open("data/finalIngredients.json", "w") as outfile:
    outfile.write(finalIngredientsJson)
    
