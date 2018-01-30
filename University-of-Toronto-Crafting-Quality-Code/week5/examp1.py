def raise_an_exception(v):
    raise ValueError(
        "{} is not a valid value.".format(v))

def main():
    raise_an_exception(3)

if __name__ == '__main__':
    try:
        main()
    except ValueError as ve:
        print(ve)


        
