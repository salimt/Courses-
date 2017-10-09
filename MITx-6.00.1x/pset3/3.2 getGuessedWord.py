# -*- coding: utf-8 -*-
"""
@author: salimt
"""

def getGuessedWord(secretWord, lettersGuessed):
    '''
    secretWord: string, the word the user is guessing
    lettersGuessed: list, what letters have been guessed so far
    returns: string, comprised of letters and underscores that represents
      what letters in secretWord have been guessed so far.
    '''
    anan = []
    for i in secretWord:
        if i in lettersGuessed:
            anan += i
        else:
            anan += '_'
    anan = (' '.join(''.join(map(str,w)) for w in anan))
    return anan