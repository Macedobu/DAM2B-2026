def mcd_estes(a, b):
    # Pas 1: Set u = 1, g = a, x = 0, and y = b
    # g = dividend, y = divisor, u i x per anar fent el seguiment de coeficients
    u = 1
    g = a
    x = 0
    y = b

    # Pas 2.1: If y = 0
    # Comprovem el mcd
    while y != 0:

        # Pas 3: Divide g by y with remainder, g = qy + t, with 0 ≤ t < y
        # q = quocient, t = residu
        q = g // y
        t = g % y

        # Pas 4: Set s = u − qx
        # Calculem el coeficient temporal "s" per al nou residu "t"
        s = u - q * x

        # Pas 5: Set u = x and g = y
        # Passem el valor antic de y (b) a ser el nou g (a)
        # El coeficient que acompanyava a y (x) passa a ser el de g (u)
        u = x
        g = y

        # Pas 6: Set x = s and y = t
        # El residu que acabem de calcular (t) passa a ser el nou divisor (y)
        # El coeficient temporal "s" es desa a "x"
        x = s
        y = t

    # Pas 2.2: Set v = (g − au)/b and return the values (g, u, v)
    # Trobem el coeficient "v" que acompanya a "b" i retornem la solució
    v = (g - a * u) // b
    return g, u, v

# Impresió
def imprimir (a, b, mcd, u, v):
    print("- - - - - - - - - - - - -")
    print(f"mcd({a}, {b}) = {mcd}")
    print(f"Solució particular: u = {u}, v = {v}")
    print(f"Comprovació: {a}*({u}) + {b}*({v}) = {a*u + b*v}")

# Comprovació
a, b = 291, 252
mcd, u, v = mcd_estes(a,b)
imprimir (a, b, mcd, u, v)

a, b = 16261, 85652
mcd, u, v = mcd_estes(a,b)
imprimir (a, b, mcd, u, v)
print("- - - - - - - - - - - - -")