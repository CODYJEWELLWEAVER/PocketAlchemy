# script to validate final*.json data files and merge into singular file
import json 
# import recipe keys
from extract_ingredients import *

final_foundation_json = open("ingredients/data/finalFoundationIngredients.json")
final_foundation_ingredients = json.load(final_foundation_json)

# Ensures data conforms to standard of having a
# - name
# - fdcId
# - calories (optional) will warn if ingredient does not contain caloric data
def validate_data(ingredients: list[dict]) -> bool:
    for ingredient in ingredients:
        if not ingredient.get(NAME_KEY): return False
        if not ingredient.get(FDC_ID_KEY): return False
        if ingredient.get(CALORIES_KEY) == {}:
            # warn when a ingredient doesn't have caloric data
            # only a few ingredients should fall into this category
            print("WARNING:", ingredient.get(NAME_KEY), " does not have caloric data")


    
    return True

##########################
# Begin merge and validate
##########################
is_foundation_data_valid = validate_data(final_foundation_ingredients)

print("Foundation Data is valid: ", is_foundation_data_valid)

# TODO: merge data into final_ingredient_data

if is_foundation_data_valid:
    final_ingredients_data = final_foundation_ingredients
    final_ingredients_json = json.dumps(final_ingredients_data, indent=4)

    with open("ingredients/data/finalIngredients.json", "w") as outfile:
        outfile.write(final_ingredients_json)