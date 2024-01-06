from nltk.tokenize import word_tokenize
from nltk.corpus import stopwords

# custom words to remove from names for foundation names


# builds ingredient name from raw string
def finalizeName(rawIngredientName: str, customStopWords: set[str]) -> str:
    constructedName = ""
    # finalizes extracted recipes
    if not rawIngredientName.startswith("Restaurant"):
        nameWords = rawIngredientName.casefold().rsplit(", ")
        constructedName = []
        numNameWords = len(nameWords)
        if numNameWords >= 4:
            word = nameWords[3]
            constructedName.append(word)
        if numNameWords >= 3:
            word = nameWords[2]
            constructedName.append(word)
        if numNameWords >= 2:
            word = nameWords[1]
            constructedName.append(word)
        if numNameWords >= 1:
            word = nameWords[0]
            constructedName.append(word)

        constructedName = " ".join(str(x) for x in constructedName)

    finalizedName = processName(constructedName, customStopWords)
    
    return finalizedName.casefold()

# filters stop words and duplicates
def processName(name: str, customStopWords: set[str]) -> str:
    stopWords = set(stopwords.words("english"))
    stopWords = stopWords.union(customStopWords)
    tokenizedName = word_tokenize(name)
    filteredName = [
        word for word in tokenizedName if word not in stopWords
    ]
    # remove duplicates
    finalName = []
    [finalName.append(x) for x in filteredName if x not in finalName]

    return " ".join(str(x) for x in finalName)
