abecedari = "abcdefghijklmnopqrstuvwxyz"

frase_a = "qf anhytwnf ufwf qtx fzifhjx"
frase_b = "swg pwpec vg cvtcrg nc ogfkqetkfcf"
frase_c = "ls wlyyv kls ovyalshuv up jvtl up klqh jvtly"

clau = 1

def desxifrar (frase, clau):
    resultat = ""
    for i in frase:
        if i in abecedari:
            posicio_actual = abecedari.index(i)
            nova_posicio = (posicio_actual - clau) % len(abecedari)
            resultat += abecedari[nova_posicio]
        else:
            resultat += i
    return resultat

for clau in range(1, len(abecedari) + 1):
    print(f"=== CLAU {clau} ===")
    print("A:", desxifrar(frase_a, clau))
    print("B:", desxifrar(frase_b, clau))
    print("C:", desxifrar(frase_c, clau))
    print()
    print (desxifrar(frase_a, clau))