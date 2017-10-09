def fibin(n):
    global numFibCalls
    numFibCalls += 1
    if n == 1:
        return 1
    elif n == 2:
        return 2
    else:
        return fibin(n-1) + fibin(n-2)
    
def fib_efficient(n, d):
    global numFibCalls
    numFibCalls += 1
    if n in d:
        return d[n]
    else:
        d[n] = fib_efficient(n-1, d) + fib_efficient(n-2, d)
        #print(d[n])
        return d[n]
    
def fib(n):
    li = [1, 2]
    for i in range (3, n + 1):
        li += [li[-1] + li[-2]]
    return li[n-1]


def fibs(n):
    aDict = {1:1, 2:2}
    for i in range (3, n + 1):
        aDict[i] = aDict[i - 1] + aDict[i - 2] 
    return aDict[n]
    
numFibCalls = 0
fibArg = 12
print(fibin(fibArg))
print('function calls', numFibCalls)

numFibCalls = 0
d = {1:1, 2:2}
print(fib_efficient(fibArg, d))
print('function calls', numFibCalls)