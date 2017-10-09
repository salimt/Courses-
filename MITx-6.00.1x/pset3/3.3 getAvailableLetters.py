# -*- coding: utf-8 -*-
"""
@author: salimt
"""

def getAvailableLetters(lettersGuessed):
    '''
    lettersGuessed: list, what letters have been guessed so far
    returns: string, comprised of letters that represents what letters have not
      yet been guessed.
    '''
    alp = 'abcdefghijklmnopqrstuvwxyz'
    #print(len(alp))
    alps = []
    #print(alp)
    for i in alp:
        if i in lettersGuessed:
            continue
        else:
            alps += i
    alps = (''.join(''.join(map(str,w)) for w in alps))
    return alps