abecedari = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
substitucio = "NFVEKIOTMSAUWLDHRGCPBQXJYZ"
frase = "ENEMIC A LES PORTES"

def encriptar(text, abc, sub):
    resultat = ""

    for i in text:
        if i in abc:
            posicio = abc.index(i)
            resultat += sub[posicio]
        else:
            resultat += i
    return resultat

frase_encriptada = encriptar (frase, abecedari, substitucio)
print(frase_encriptada)