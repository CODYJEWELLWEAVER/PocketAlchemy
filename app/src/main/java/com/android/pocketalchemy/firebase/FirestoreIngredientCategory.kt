package com.android.pocketalchemy.firebase

import com.android.pocketalchemy.model.IngredientCategory
import com.android.pocketalchemy.model.IngredientCategory.ALL
import com.android.pocketalchemy.model.IngredientCategory.BAKED_GOODS
import com.android.pocketalchemy.model.IngredientCategory.BEEF
import com.android.pocketalchemy.model.IngredientCategory.BEVERAGES
import com.android.pocketalchemy.model.IngredientCategory.CEREAL
import com.android.pocketalchemy.model.IngredientCategory.DAIRY_N_EGGS
import com.android.pocketalchemy.model.IngredientCategory.FATS_N_OILS
import com.android.pocketalchemy.model.IngredientCategory.FISH_N_SHELLFISH
import com.android.pocketalchemy.model.IngredientCategory.FRUITS
import com.android.pocketalchemy.model.IngredientCategory.GRAINS_N_PASTA
import com.android.pocketalchemy.model.IngredientCategory.LAMB_N_GAME_PRODUCTS
import com.android.pocketalchemy.model.IngredientCategory.LEGUMES
import com.android.pocketalchemy.model.IngredientCategory.NUTS_N_SEEDS
import com.android.pocketalchemy.model.IngredientCategory.PORK
import com.android.pocketalchemy.model.IngredientCategory.POULTRY
import com.android.pocketalchemy.model.IngredientCategory.SAUSAGES_N_LUNCH_MEATS
import com.android.pocketalchemy.model.IngredientCategory.SNACKS
import com.android.pocketalchemy.model.IngredientCategory.SOUPS_N_SAUCES
import com.android.pocketalchemy.model.IngredientCategory.SPICES_N_HERBS
import com.android.pocketalchemy.model.IngredientCategory.SWEETS
import com.android.pocketalchemy.model.IngredientCategory.VEGETABLES

object FirestoreIngredientCategory {
    private const val BAKED_CATEGORY_NAME = "Baked Products"
    private const val BEEF_CATEGORY_NAME = "Beef Products"
    private const val BEVERAGES_CATEGORY_NAME = "Beverages"
    private const val CEREAL_CATEGORY_NAME = "Breakfast Cereals"
    private const val DAIRY_N_EGGS_CATEGORY_NAME = "Dairy and Egg Products"
    private const val FATS_N_OILS_CATEGORY_NAME = "Fats and Oils"
    private const val FISH_N_SHELLFISH_CATEGORY_NAME = "Finfish and Shellfish Products"
    private const val FRUITS_CATEGORY_NAME = "Fruits and Fruit Juices"
    private const val GRAINS_AND_PASTA_CATEGORY_NAME = "Cereal Grains and Pasta"
    private const val LAMB_N_GAME_CATEGORY_NAME = "Lamb, Veal, and Game Products"
    private const val LEGUME_CATEGORY_NAME = "Legumes and Legume Products"
    private const val NUTS_N_SEEDS_CATEGORY_NAME = "Nut and Seed Products"
    private const val POULTRY_CATEGORY_NAME = "Poultry Products"
    private const val PORK_CATEGORY_NAME = "Pork Products"
    private const val SAUSAGES_N_LUNCH_MEATS_CATEGORY_NAME = "Sausages and Luncheon Meats"
    private const val SNACKS_CATEGORY_NAME = "Snacks"
    private const val SOUPS_N_SAUCES_CATEGORY_NAME = "Soups, Sauces, and Gravies"
    private const val SPICES_N_HERBS_CATEGORY_NAME = "Spices and Herbs"
    private const val SWEETS_CATEGORY_NAME = "Sweets"
    private const val VEGETABLES_CATEGORY_NAME = "Vegetables and Vegetable Products"

    fun getCategoryName(category: IngredientCategory): String? {
        return when(category) {
            ALL -> null
            BAKED_GOODS -> BAKED_CATEGORY_NAME
            BEEF -> BEEF_CATEGORY_NAME
            BEVERAGES -> BEVERAGES_CATEGORY_NAME
            CEREAL -> CEREAL_CATEGORY_NAME
            DAIRY_N_EGGS -> DAIRY_N_EGGS_CATEGORY_NAME
            FATS_N_OILS -> FATS_N_OILS_CATEGORY_NAME
            FISH_N_SHELLFISH -> FISH_N_SHELLFISH_CATEGORY_NAME
            FRUITS -> FRUITS_CATEGORY_NAME
            GRAINS_N_PASTA -> GRAINS_AND_PASTA_CATEGORY_NAME
            LAMB_N_GAME_PRODUCTS -> LAMB_N_GAME_CATEGORY_NAME
            LEGUMES -> LEGUME_CATEGORY_NAME
            NUTS_N_SEEDS -> NUTS_N_SEEDS_CATEGORY_NAME
            POULTRY -> POULTRY_CATEGORY_NAME
            PORK -> PORK_CATEGORY_NAME
            SAUSAGES_N_LUNCH_MEATS -> SAUSAGES_N_LUNCH_MEATS_CATEGORY_NAME
            SNACKS -> SNACKS_CATEGORY_NAME
            SOUPS_N_SAUCES -> SOUPS_N_SAUCES_CATEGORY_NAME
            SPICES_N_HERBS -> SPICES_N_HERBS_CATEGORY_NAME
            SWEETS -> SWEETS_CATEGORY_NAME
            VEGETABLES -> VEGETABLES_CATEGORY_NAME
        }
    }
}