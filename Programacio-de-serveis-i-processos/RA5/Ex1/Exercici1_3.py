from collections import Counter

text_xifrat = "rm vq mfmcjhq rm vq kxrmwxtq xtq kxyx tm tqzjq"

frequencies = Counter(text_xifrat.replace(" ", ""))

print(frequencies.most_common())

lletres_xifrades = "rmvqkxwtyfcjhz"
lletres_reals = "delaporscnmigb"

taula = str.maketrans(lletres_xifrades, lletres_reals)
text_desxifrat = text_xifrat.translate(taula)

print("Text desxifrat:", text_desxifrat)