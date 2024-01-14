# Ingredients
---
This folder contains code to extract, process, and upload ingredient data to firestore.
USDA FoodData Foundation Foods, and SR Legacy Food datasets are used to create the
ingredient database. 

##### Source Data Files
[USDA FoodData](https://fdc.nal.usda.gov/download-datasets.html)

##### Contents
1. extract_data.ipynb
    * Notebook containing flow for extracting data.
2. process_data.ipynb
    * Notebook with flow for filtering and processing data.
3. data_util.py
    * Utilities for working with datasets.
4. constants.py
    * Constants used for data processing.
5. visualizations.ipynb
    * Visualizations for data.
6. validate_data.ipynb
    * Performs basic checks for ingredient data and then writes batched data for uploading.
7. data/
    * raw/ _- source datasets_
    * intermediate/ _- generated datasets (unfinished)_
    * final/ _- fully processed and validated datasets (batched)_

### Process
The process used for creating the ingredient dataset for PocketAlchemy is as follows:
1. USDA FoodData is read by extract_data.ipynb. Each ingredient is mapped to a dictionary modeling the ingredient model class in _Ingredient.kt_. The merged data is written to an itermediate file called _extracted_data.json_.
2. process_data.ipynb filters out branded data, doctors descriptions, and then tries to merge entries with the same description. Processed data is written to _processed_data.json_.
3. Data is validated by _validate_data.ipynb_, if data is valid it's then written in batches to _data/final_. See notebook for more detailed info on batching.
4. Data is uploaded in batches by _upload_data.ipynb_. 

