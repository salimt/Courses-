import sys
import cs50
def main():
    
    if len(sys.argv) != 2:
        print("missing command-line argument")
        exit(0)
    if sys.argv[1].isdigit() == False:
        print("only digits allowed!")
        exit(1)
    key = int(sys.argv[1])
    #key = sys.argv[1]
    #keylen = len(sys.argv[1])
    print("plainext: ", end="")
    txt = cs50.get_string()
    #print ("ciphertext: ", end="")
    #jey = 0
    ciphertext=[]
    for i in range(len(txt)):
        #j = ord(key[jey%keylen])
        c = ord(txt[i])
        if (65<=c<=90) == True:
            c = ((c - 65 + key) % 26 ) + 65
            ciphertext.append(chr(c))
            #print(chr(c),end ="")
        elif (97<=c<=122) == True:
            c = ((c - 97 + key) % 26 ) + 97
            ciphertext.append(chr(c))
            #print(chr(c),end ="")
        else:
            #print(txt[i],end="")
            ciphertext.append(chr(c))
        #jey+=1
    #print()
    print("ciphertext: ", end="")
    print("".join(ciphertext))

if __name__ == "__main__":
    main()