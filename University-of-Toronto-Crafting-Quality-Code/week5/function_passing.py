def function_runner(f):
    """ (function) -> NoneType

    Call f.
    """

    f()


def function_1():
    print("function_1 was called.")


def function_2():
    print("function_2 was called.")


if __name__ == '__main__':
    function_runner(function_1)
    function_runner(function_2)
