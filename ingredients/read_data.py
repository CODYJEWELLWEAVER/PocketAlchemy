import extract_ingredients
import util
import json
import stop_words

# EXTRACT FOUNDATION FOODS
# foundationJson = open("/home/cody/AndroidStudioProjects/PocketAlchemy/ingredients/data/FoodData_Central_foundation_food_json_2023-04-20/foundationFoods.json")
# foundationFoodData: list[dict] = json.load(foundationJson).get("FoundationFoods")

# extractedIngredients = extract_ingredients.extractIngredients(foundationFoodData, stop_words.stopWordsFoundationData)

# for ing in extractedIngredients:
#     print(ing.get("name"))

# foundationIngredientsJson = json.dumps(extractedIngredients, indent=4)

# with open("ingredients/data/foundationIngredients.json", "w") as outfile:
#    outfile.write(foundationIngredientsJson)

# EXTRACT LEGACY FOODS
foundationJson = open("ingredients/data/FoodData_Central_sr_legacy_food_json_2018-04/FoodData_Central_sr_legacy_food_json_2021-10-28.json")
legacyFoodData: list[dict] = json.load(foundationJson).get("SRLegacyFoods")

extractedIngredients = extract_ingredients.extractIngredients(legacyFoodData, stop_words.stopWordsLegacyData)

categories = dict()

for ing in extractedIngredients:
    category = ing.get(extract_ingredients.CATEGORY_KEY)
    if category in categories:
        ingredients = categories.get(category)
        ingredients.append(ing)
        categories[category] = ingredients
    else:
        categories[category] = [ing]

print(categories.keys())

legacyIngredientsJson = json.dumps(extractedIngredients, indent=4)

with open("ingredients/data/legacyIngredients.json", "w") as outfile:
    outfile.write(legacyIngredientsJson)