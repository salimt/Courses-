import cs50

def main():
    summ = 0
    coin = [25, 10, 5, 1]
    while True:
        print("How much change is owed? Note: 6.22 represents 6 dollars and 22 cents.: ", end="")
        amount = cs50.get_float()
        break
    cents = round(amount * 100.0)
    for i in range (4):
        summ = summ + cents / coin[i]
        cents = cents % coin[i]
    summ = int(summ)
    print("{}".format(summ))
        
if __name__ == "__main__":
    main()