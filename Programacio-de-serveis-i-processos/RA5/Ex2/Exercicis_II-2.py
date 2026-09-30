def euclides_ampliat(a, b):

while b !=0:
    q = a // b #cuocient
    r = a % b #residu

    #actualitzem a i b
    a = b
    b = r

#Quan b es 0, a es el MCD
return a, u, v


#Apartat 2a
mcd1, u1, v1 = euclides_ampliat(291, 252)
print(f"a) mcd(291, 252): u = {u1}, v = {v1}")

#Apartat 2b
mcd2, u2, v2 = euclides_ampliat(16261, 85652)
print(f"b) mcd(16261, 85652): u = {u2}, v = {v2}")
