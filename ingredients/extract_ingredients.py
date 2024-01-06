# script for loading USDA source files and extracting
# neccessary ingredient data
#!/usr/bin/python3.11
import json
import util

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

def extractIngredients(jsonData, customStopWords) -> list[dict]:
    extractedIngredients = list()
    for food in jsonData:
        ingredient = dict()
        name = food.get("description")
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
        
        ingredient[NAME_KEY] = util.finalizeName(name, customStopWords)
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

        if ingredient[NAME_KEY] != "":
            extractedIngredients.append(ingredient)

    return extractedIngredients
    
