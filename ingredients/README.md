# Ingredients
---
This folder contains python scripts for extracting ingredient data from USDA FoodData resources to be included in PocketAlchemy. 

##### Source Data Files
[USDA FoodData](https://fdc.nal.usda.gov/download-datasets.html)

##### Contents
1. /extract_data.py
    * Extracts data for sources and writes Json files.
2. /extract_ingredients.py
    * Contains helpers for extracting field data from resources.
3. /util.py
    * Utilities for processing names.
4. /foundationIngredients.json
    * Ingredients extracted from USDA FoodData Foundation Foods with extract_data_script.py.
5. /finalFoundationIngredients.json
    * Manually refined version of foundationIngredients.json.
