# -*- coding: utf-8 -*-
"""
Bisection Search 
@author: salimt
"""

high = 100
low = 0
ans = int((high + low)/2)
numGuesses = 0
print('Please think of a number between 0 and 100!')       
while ans <= 100:
    print("Is your secret number ", ans, "?")
    guess = input("Enter 'h' to indicate the guess is too high. Enter 'l' to indicate the guess is too low. Enter 'c' to indicate I guessed correctly. ")
    print('low = ', low, ' high = ', high, ' ans = ', ans)
    numGuesses += 1
    if guess == 'h':
        high = ans
    elif guess == 'l':
        low = ans
    elif guess == 'c':
        print("Game over. Your secret number was: ", ans)
        break
    else:
        print('Sorry, I did not understand your input.')
        pass
    ans = int((high + low)/2)
print('It took me ' + str(numGuesses) + ' steps to guess it!')
    
    