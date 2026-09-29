def mcd_euclides(a,b):
    while b !=0:
        a, b = b, a % b #va per posicions a = b, b = a%b
    return a

#Apartat 1a
res_a = mcd_euclides(291, 252)
print(f"a) mcd(291, 252) = {res_a}")

#Apartat 1b
res_b = mcd_euclides(16261, 85652)
print(f"b) mcd(16261, 85652) = {res_b}")