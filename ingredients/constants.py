# Constants for use in extracting USDA FoodData ingredient
# Contents:
#   - Field keys for ingredient dict. 
#   - Categories (29, 43)
#       - To exclude
#       - Meat categories
#   - Patterns to either remove or replace from ingredient descriptions 
#   - Brand names to exclude from data 


# FIELD KEYS
DESCRIPTION_KEY = "description"
KEYWORDS_KEY = "keywords"
FDC_ID_KEY = "fdcId"
CATEGORY_KEY = "category"
CARBS_KEY = "carbs"
CALORIES_KEY = "calories"
SODIUM_KEY = "sodium"
FAT_KEY = "fat"
FIBER_KEY = "fiber"
SUGARS_KEY = "sugars"
PROTEIN_KEY = "protein"
MEASURE_VALUE_KEY = "value"
MEASURE_UNIT_KEY = "unit"
MEASURE_GWEIGHT_KEY = "gWeight"
MEASURES_KEY = "measures"
# END FIELD KEYS

# USDA FOOD DATA CATEGORIES TO EXCLUDE IN FINAL DATA
EXCLUDED_CATEGORIES = [
    "Fast Foods",
    "Meals, Entrees, and Side Dishes",
    "Baby Foods",
    "Restaurant Foods",
]

# CATEGORIES OF MEAT PRODUCTS
MEAT_CATEGORIES = [
    "Beef Products", 
    "Pork Products", 
    "Lamb, Veal, and Game Products",
    "Poultry Products",
    "Sausages and Luncheon Meats",
]

# COMMON PATTERNS (AND STRINGS) TO REMOVE FROM DESCRIPTIONS
# WARNING: THESE ARE CASE SENSITIVE
DESCRIPTION_PATTERNS = [
    r".\([^,]{25,}\),*", # match () with non ',' chars in between
    r".\([^\)]{25,}\),*", # match () with non ')' chars in between
    "with high vitamin C with other added vitamins,",
    "heated in oven",
    ", not fortified",
    ", with aspartame",
    ", refrigerated",
    "shelf stable",
    "salt added in processing,",
    "unprepared",
    r"includes from concentrate,*\s*",
    "Frankfurter", # replace with Hot dog
    ", unenriched",
    ", unheated",
    "Spices, ", # only pattern to remove from spices
    r",.bleached", # only pattern to remove from grains
]

# START OF CATEGORY SPECIFIC PATTERNS TO REMOVE/REPLACE 
# SOME "PATTERNS" are simply strings for increased readability
# WARNING: CHANGES HERE NEED TO BE IMPLEMENTED IN REPLACEMENT 
# LOGIC IN update_description

VEGETABLE_PATTERNS = [
    ", regular pack",
    ", drained solids",
    r",.drained[^,]*",
    ", with salt",
    ", mature",
    ", flesh and skin",
    ", all varieties",
    ", as purchased",
    ", year round average",
    ", restaurant",
    r",.\(globe or french\)",
    ", cooked, boiled", # replace with ', boiled'
]

BAKED_PATTERNS = [
    r", commercially prepared,*",
    r", ready.*?-to-heat",
    "Leavening agents, ",
    ", ready-to-bake or -fry",
    r".made with.*?fat",
    ", without lemon juice and rind",
    ", standard snack-type",
    ", made with margarine",
    r".\(includes honey buns\)",
    ", higher fat",
    r".\(includes lemon-flavored\)",
]

# MEAT PRODUCT STOP PATTERNS
MEAT_PATTERNS = [
    " trimmed to",
    ", all grades",
    r",*.separable lean and fat",
    ", variety meats and by-products",
    ", bone-in",
    ", boneless",
    r",.separable lean only", # REPLACE WITH 'lean',
    ", restaurant",
     r",.regular \(approximately 11\% fat\)",
    r",.reduced sodium, added ascorbic acid, includes SPAM, 25\% less sodium",
    r",.deli meat \(96\%fat free, water added\)", # must be before fat/lean reduction pattern
    r",*?.([0-9]{1,}%).*?fat", # matches with fat/lean percentages
    r".\(ribs.*?\)",
    ", oriental style",
    r",.Australian", # replace with AUS
    r",.New.Zealand", # replace with NZ
    ", imported",
    "Game meat, ",
    ", broilers or fryers",
    ", original seasoning",
    ", meat and skin",
    ", with added solution",
    ", all classes",
    ", prepackaged or deli",
    ", includes Spam Lite",
    r",.heated[^,]*",
    ", broiler or fryers",
]

BEVERAGE_PATTERNS = [
    r"^Beverages,.",
    ", prepared from item 14028",
    r"Alcoholic.[bB]everage[s]*", # replace with Alcohol
]

CEREAL_PATTERNS = [
    ", fortified",
    r"Cereals,.",
    r"Cereals.ready-to-eat,.",
]

DAIRY_EGG_PATTERNS = [
    ", pasteurized process",
    ", large or small curd",
    ", fluid",
    ", filled",
]

FAT_OIL_PATTERNS = [
    ", industrial",
    ", salad or cooking",
]

FISH_SHELLFISH_PATTERNS = [
    "Fish,",
    "Mollusks,",
    "Crustaceans,",
]

FRUIT_PATTERNS = [
    ", canned or bottled",
    ", diluted with 3 volume water",
    ", with added ascorbic acid",
    ", not from concentrate",
    ", without added ascorbic acid",
    ", solids and liquids",
    ", without added sugar",
]

LEGUME_PATTERNS = [
    r"\sprepared with calcium sulfate and magnesium chloride",
    ", boiled, with salt",
    r",*? mature seeds",
    ", solids and liquids",
]

NUT_SEED_PATTERNS = [
    r"Nuts,.",
    r"Seeds,.",
    r",.from roasted and toasted kernels \(most common type\)",
    r".\(desiccated\)",
    r"\(glandless\)",
    r"\(decorticated\)",
    r",.with salt added",
]

SNACK_PATTERNS = [
    "Snacks, ",
    r",.made with partially hydrogenated.*?oil",
    r",.made from dried potatoes.*?,",
    ", chopped and formed",
    ", salted",
]

SOUP_SAUCE_PATTERNS = [
    r",.prepared with water or ready-to.*?serve",
    ", ready-to-serve",
    ", single brand",
    ", restaurant-prepared",
    "restaurant",
]

SWEETS_PATTERNS = [
    ", NFSMI Recipe No. C-32",
    ", prepared-from-recipe",
    ", ready-to-eat",
    ", regular",
]

# END CATEGORY SPECIFIC STOP PATTERNS

# BRAND NAMES
BRAND_NAMES = [
    "archway",
    "andrea's",
    "continental mills",
    "krusteaz",
    "goya",
    "crunchmaster",
    "george weston",
    "glutino",
    "heinz",
    "weight watcher",
    "interstate brands",
    "keebler",
    "kraft",
    "martha white foods",
    "mary's gone crackers",
    "mckee baking",
    "mission foods",
    "nabisco",
    "la ricura",
    "pepperidge farm",
    "pillsbury",
    "rudi's",
    "sage valley",
    "schar",
    "udi's",
    "van's",
    "bud light",
    "budweiser",
    "abbot",
    "arizona",
    "coca-cola",
    "nestle",
    "cytosport",
    "dannon",
    "amp",
    "monster",
    "red bull",
    "rockstar",
    "vault",
    "fuze",
    "gerolsteiner",
    "lipton brisk",
    "minute maid",
    "motts",
    "nestea",
    "ocean spray",
    "ovaltine",
    "quaker",
    "pepsico",
    "powerade",
    "gatorade",
    "propel",
    "slimfast",
    "snapple",
    "v8",
    "calistoga",
    "crystal geyser",
    "evian",
    "pepsi",
    "perrier",
    "poland spring",
    "wendy's",
    "zevia",
    "alpen",
    "puffins",
    "familia",
    "general mills",
    "health valley",
    "malt-o-meal",
    "post",
    "mom's best",
    "nature's path",
    "ralston",
    "uncle sam",
    "sun country",
    "weetabix",
    "cream of rice",
    "cream of wheat",
    "wheatena",
    "tinkyada",
    "ancient harvest",
    "de boles",
    "uncle ben",
    "kamut",
    "lifeway",
    "reddi wip",
    "chobani",
    "bolthouse",
    "naked juice",
    "odwalla",
    "house foods",
    "mori-nu",
    "silk",
    "vitasoy",
    "chosen roaster",
    "planters",
    "hormel",
    "oscar mayer",
    "luna bar",
    "mars snackfood",
    "power bar",
    "slim-fast",
    "south beach",
    "balance",
    "clif bar",
    "farley candy",
    "fritolay",
    "kashi",
    "kellogg",
    "m&m mars",
    "nutri-grain",
    "hain celestial",
    "benecol",
    "smart balance",
    "smart beat",
    "campbell's",
    "frito's",
    "tostitos",
    "bull's-eye",
    "kc masterpiece",
    "open pit",
    "sweet baby ray's",
    "texas pete",
    "tuong ot",
    "buitoni",
    "classico",
    "mezzetta",
    "tabasco",
    "smart soup",
    "healthy choice",
    "swanson",
    "5th avenue",
    "almond joy",
    "heath bites",
    "hershey's",
    "kit kat",
    "krackel",
    "mounds",
    "mr. goodbar",
    "reese's",
    "rolo",
    "special dark",
    "symphony",
    "toblerone",
    "tootsie roll",
    "twizzlers",
    "york",
    "klondike",
    "popsicle",
    "fudgsicle",
    "breyers",
    "schiff,tiger",
    "equal",
    "splenda",
    "smucker",
    "bimbo bakeries",
    "caramello",
    "zespri",
    "hain",
    "whatchamacallit",
    "creamsicle",
    "hershey",
    "sprite",
    "muscle milk",
    "amber",
    "full throttle",
    "crunch bar",
    "sunkist",
    "concord",
    "real lemon",
    "la moderna rikis",
    "gamesa sabrosas",
]