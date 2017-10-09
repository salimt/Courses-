# -*- coding: utf-8 -*-
"""
@author: salimt
"""

def isWordGuessed(secretWord, lettersGuessed):
    '''
    secretWord: string, the word the user is guessing
    lettersGuessed: list, what letters have been guessed so far
    returns: boolean, True if all the letters of secretWord are in lettersGuessed;
      False otherwise
    '''
    anan = []
    for c in secretWord:
        if c in lettersGuessed:
            anan += c
    if len(anan) != len(secretWord):
        return False
    else:
        return True