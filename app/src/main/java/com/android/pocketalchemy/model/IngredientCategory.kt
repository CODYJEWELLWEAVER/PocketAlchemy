package com.android.pocketalchemy.model

import com.android.pocketalchemy.R

enum class IngredientCategory(val labelResId: Int) {
    ALL(R.string.all_categories_label),
    BAKED_GOODS(R.string.baked_goods_label),
    BEEF(R.string.beef_label),
    BEVERAGES(R.string.beverages_label),
    CEREAL(R.string.cereal_label),
    DAIRY_N_EGGS(R.string.dairy_n_eggs_label),
    FATS_N_OILS(R.string.fats_n_oils_label),
    FISH_N_SHELLFISH(R.string.fish_n_shellfish_label),
    FRUITS(R.string.fruits_label),
    GRAINS_N_PASTA(R.string.grains_n_pasta_label),
    LAMB_N_GAME_PRODUCTS(R.string.lamb_n_game_label),
    LEGUMES(R.string.legumes_label),
    NUTS_N_SEEDS(R.string.nuts_and_seeds_label),
    POULTRY(R.string.poultry_label),
    PORK(R.string.pork_label),
    SAUSAGES_N_LUNCH_MEATS(R.string.sausage_n_lunch_meats_label),
    SNACKS(R.string.snacks_label),
    SOUPS_N_SAUCES(R.string.soups_n_sauces_label),
    SPICES_N_HERBS(R.string.spices_n_herbs_label),
    SWEETS(R.string.sweets_label),
    VEGETABLES(R.string.vegetables_label);
}