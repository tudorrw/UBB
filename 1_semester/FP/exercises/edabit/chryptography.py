from hashlib import sha256
from functools import reduce


class Encryption:
    
    def __init__(self, encrypt):
        self.encrypt = encrypt

    def change_wovels_to_numbers(self):
        wovels = {'a': 0, 'e': 1, 'i': 2, 'o': 2, 'u': 3}
        lst = list(map(lambda ch: str(wovels[ch]) if ch in wovels else ch, list(self.encrypt[::-1])))
        modified= ''.join(lst)
        return modified + 'aca'


    def get_sha256_hash(self):
        return sha256(self.encrypt.encode()).hexdigest()


    def move(self):
        return ''.join(map(lambda x: chr(ord(x) + 1), list(self.encrypt)))


    def hamming_code(self):
        binary_letters = list(map(lambda x: bin(x).replace('b', ''), list(map(ord, list(self.encrypt)))))
        triple = list(map(lambda binary_string: ''.join(list(map(lambda ch: ch * 3, binary_string))), binary_letters))

        return reduce(lambda x, y: x + y, triple)
    

    def digital_cipher(self, key):
        quotient = len(self.encrypt) // len(str(key))
        remainder = len(self.encrypt) % len(str(key))
        string = str(key) * quotient + str(key)[:remainder]
        return list(map(lambda ch, k: ord(ch) - 96 + int(k), self.encrypt, string))


    def caesar_cipher(self, value):
        alphabet = "abcdefghijklmnopqrstuvwxyz"
        return ''.join(map(lambda x: x if ord(x) == 32 else (alphabet[ord(x) - 97 + value] if (ord(x) - 97 + value) < 26 else alphabet[ord(x) - 97 + value - 26]), self.encrypt))


    # to check
    def message_glitch(self):
        lst = []
        i = 0
        while i < len(self.encrypt):
            if self.encrypt[i:i+2].isdigit():
                lst.append(self.encrypt[i:i+2])
                i += 1
            else:
                lst.append(self.encrypt[i])
            i += 1

        return ''.join(map(lambda x: chr(int(x) + 96) if x.isdigit() else x, lst))


    # to check
    def atbash_cipher(self):
        return ''.join(map(lambda x: x if not x.isalpha() else (chr(219 - ord(x)) if x.islower() else chr(155 - ord(x))), self.encrypt))


    # to check
    def paul_cipher(self):
        upper_letters = list(map(lambda x: x.upper() if x.isalpha() else x, self.encrypt))
        i = len(upper_letters) - 1

        while i:
            j = i - 1
            if upper_letters[i].isalpha():
                while not upper_letters[j].isalpha():
                    j -= 1
                sum_of_letters = ord(upper_letters[i]) + ord(upper_letters[j])
                upper_letters[i] = chr(sum_of_letters - 64) if sum_of_letters - 64 < 90 else chr(sum_of_letters - 90)
            i -= 1
        return ''.join(map(str, upper_letters))

    def secret_password(self):
        if len(self.encrypt) == 9 and self.encrypt.islower() and self.encrypt.isalpha():
            part_1 = str(ord(self.encrypt[0]) - 96) + self.encrypt[1] + str(ord(self.encrypt[2]) -96)
            part_2 = self.encrypt[3:6][::-1]
            part_3 = ''.join(map(lambda x: chr(ord(x) + 1) if ord(x) + 1 < 123 else chr(ord(x) - 25), self.encrypt[6:9]))

            return part_2 +  part_3 + part_1 

        return "BANG! BANG! BANG!"




class Decryption:
    def __init__(self, decrypt):
        self.decrypt = decrypt
    
    def digital_decipher(self, key):
        quotient = len(self.decrypt) // len(str(key))
        remainder = len(self.decrypt) % len(str(key))
        string = str(key) * quotient + str(key)[:remainder]
        return ''.join(map(lambda num, k: chr(num - int(k) + 96),self.decrypt, string))

    def missing_number(self):
        for i in range(1, 26):
            if i not in self.decrypt:
                return chr(64 + i)
    
    # to check
    def morse(self):
        d = {"A":".-","B":"-...","C":"-.-.","D":"-..","E":".",
        "F":"..-.","G":"--.","H":"....","I":"..","J":".---",
        "K":"-.-","L":".-..","M":"--","N":"-.","O":"---",
        "P":".--.","Q":"--.-","R":".-.","S":"...","T":"-",
        "U":"..-","V":"...-","W":".--","X":"-..-","Y":"-.--","Z":"--..",
        " ":"....."}
        if self.decrypt[0].isalpha():
            return ' '.join(map(lambda x: d[chr(ord(x) - 32)] if x.islower() else d[x], self.decrypt))
        else:
            return ''.join(map(lambda x: list(d.keys())[list(d.values()).index(x)], self.decrypt.split(' ')))

    # to check
    def dif_ciph(self):
        if type(self.decrypt) is list:
            for i in range(1, len(self.decrypt)):
                self.decrypt[i] += self.decrypt[i - 1]
            return ''.join(map(chr, self.decrypt))
        else:
            ascii_codes = list(map(ord, self.decrypt))
            for i in range(len(ascii_codes) - 1, 0, -1):
                ascii_codes[i] -= ascii_codes[i - 1]
            return ascii_codes



if __name__ == "__main__":

    e1 = Encryption("Christmas is the 25th of December")
    print(e1.change_wovels_to_numbers())
    print(e1.get_sha256_hash())
    print(e1.move())
    print(e1.hamming_code())
    print(e1.digital_cipher(1939))
    print(e1.atbash_cipher())
    
    e2 = Encryption("fruuhfw")
    print(e2.caesar_cipher(23))

    e3 = Encryption("T21e19d1y's m1r11e20i14g m5e20i14g w9l12 14o23 2e i14 20h5 3o14f5r5n3e r15o13.")
    print(e3.message_glitch())

    e4 = Encryption("mAtT")
    print(e4.paul_cipher())

    e5 = Encryption("mubash zrh")
    print(e5.secret_password())

    print('***********')

    d1 = Decryption([20, 12, 18, 30, 21])
    print(d1.digital_decipher(1939))

    d2 = Decryption([19, 12, 14, 21, 22, 3, 15, 20, 9, 16, 24, 17, 11, 10, 13, 18, 7, 8, 4, 5, 1, 6, 25, 23, 26])
    print(d2.missing_number())

    d3 = Decryption("Barack Obama")
    print(d3.morse())

    d4 = Decryption("-... .- .-. .- -.-. -.- ..... --- -... .- -- .-")
    print(d4.morse())

    d5 = Decryption("Sunshine")
    print(d5.dif_ciph())
    print(len("rwgeg"))
