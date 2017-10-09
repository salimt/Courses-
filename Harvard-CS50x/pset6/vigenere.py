import cs50
import sys

def main():
    if len(sys.argv) != 2:
        print("missing command-line argument")
        exit(0)
    
    key = sys.argv[1]
    keylen = len(key)
    if sys.argv[1].isalpha() == False:
        print("Only letters allowed!")
        exit(1)
    
    print("plaintext: ", end="")
    plaintext = cs50.get_string()

    ciphertext = []
    j = 0
    for i in range(len(plaintext)):
        if j >= keylen:
            j = 0
        k = ord(key[j]) % 97
        c = ord(plaintext[i])
        if plaintext[i].isalpha()==True:
            if plaintext[i].isupper()==True:
                c = (c + k - 65) % 26 + 65
                ciphertext.append(chr(c))
                j += 1
            if plaintext[i].islower()==True:
                c = (c + k - 97) % 26 + 97
                ciphertext.append(chr(c))
                j += 1            
        else:
            ciphertext.append(chr(c))
    
    print("ciphertext: ", end="")
    print("".join(ciphertext))
    exit(2)
    
if __name__ == "__main__":
    main()