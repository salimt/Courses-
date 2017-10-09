import cs50

def main():
    while True:
        print("number: ", end="")
        n = cs50.get_int()
        if n <= 0 or n >= 23:
            print("0-23 allowed!")
            break
        i = 1
        for i in range(n):
            space = n - i
            for j in range(space):
                print(" ", end="")
            for f in range(i+1):
                print("#", end="")
            print("", end="")
            for k in range(i+1):
                print("#", end="")
            print()
        print("congrats!")
        break
    return n

if __name__ == "__main__":
    main()