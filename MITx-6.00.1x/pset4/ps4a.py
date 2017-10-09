# The 6.00 Word Game

import random
import string
import sys

VOWELS = 'aeiou'
CONSONANTS = 'bcdfghjklmnpqrstvwxyz'
HAND_SIZE = 7

SCRABBLE_LETTER_VALUES = {
    'a': 1, 'b': 3, 'c': 3, 'd': 2, 'e': 1, 'f': 4, 'g': 2, 'h': 4, 'i': 1, 'j': 8, 'k': 5, 'l': 1, 'm': 3, 'n': 1, 'o': 1, 'p': 3, 'q': 10, 'r': 1, 's': 1, 't': 1, 'u': 1, 'v': 4, 'w': 4, 'x': 8, 'y': 4, 'z': 10
}

# -----------------------------------
# Helper code
# (you don't need to understand this helper code)

WORDLIST_FILENAME = "words.txt"

def loadWords():
    """
    Returns a list of valid words. Words are strings of lowercase letters.
    
    Depending on the size of the word list, this function may
    take a while to finish.
    """
    print("Loading word list from file...")
    # inFile: file
    inFile = open(WORDLIST_FILENAME, 'r')
    # wordList: list of strings
    wordList = []
    for line in inFile:
        wordList.append(line.strip().lower())
    print("  ", len(wordList), "words loaded.")
    return wordList

def getFrequencyDict(sequence):
    """
    Returns a dictionary where the keys are elements of the sequence
    and the values are integer counts, for the number of times that
    an element is repeated in the sequence.

    sequence: string or list
    return: dictionary
    """
    # freqs: dictionary (element_type -> int)
    freq = {}
    for x in sequence:
        freq[x] = freq.get(x,0) + 1
    return freq
	

# (end of helper code)
# -----------------------------------

#
# Problem #1: Scoring a word
#
def getWordScore(word, n):
    """
    Returns the score for a word. Assumes the word is a valid word.

    The score for a word is the sum of the points for letters in the
    word, multiplied by the length of the word, PLUS 50 points if all n
    letters are used on the first turn.

    Letters are scored as in Scrabble; A is worth 1, B is worth 3, C is
    worth 3, D is worth 2, E is worth 1, and so on (see SCRABBLE_LETTER_VALUES)

    word: string (lowercase letters)
    n: integer (HAND_SIZE; i.e., hand size required for additional points)
    returns: int >= 0
    """
    total = 0
    #print(len(word), n)
    for i in word:
        """SCRABBLE_LETTER_VALUES"""
        total += (SCRABBLE_LETTER_VALUES.get(i))
    total *= (len(word))
    if len(word) == n:
        total += 50    
    #print(total)
    return total

#test_getWordScore()
#print(getWordScore('kids', 5))     #36
#print(getWordScore('onomatopoeia', 12))  #242
#print(getWordScore('dogs', 4))   #74

#
# Problem #2: Make sure you understand how this function works and what it does!
#
def displayHand(hand):
    """
    Displays the letters currently in the hand.

    For example:
    >>> displayHand({'a':1, 'x':2, 'l':3, 'e':1})
    Should print out something like:
       a x x l l l e
    The order of the letters is unimportant.

    hand: dictionary (string -> int)
    """
    for letter in hand.keys():
        for j in range(hand[letter]):
             print(letter,end=" ")       # print all on the same line
    print()   
#
# Problem #2: Make sure you understand how this function works and what it does!
#
def dealHand(n):
    """
    Returns a random hand containing n lowercase letters.
    At least n/3 the letters in the hand should be VOWELS.

    Hands are represented as dictionaries. The keys are
    letters and the values are the number of times the
    particular letter is repeated in that hand.

    n: int >= 0
    returns: dictionary (string -> int)
    """
    hand={}
    numVowels = n // 3
    
    for i in range(numVowels):
        x = VOWELS[random.randrange(0,len(VOWELS))]
        hand[x] = hand.get(x, 0) + 1
        
    for i in range(numVowels, n):    
        x = CONSONANTS[random.randrange(0,len(CONSONANTS))]
        hand[x] = hand.get(x, 0) + 1
        
    return hand

#
# Problem #2: Update a hand by removing letters
#
def updateHand(hand, word):
    """
    Assumes that 'hand' has all the letters in word.
    In other words, this assumes that however many times
    a letter appears in 'word', 'hand' has at least as
    many of that letter in it. 

    Updates the hand: uses up the letters in the given word
    and returns the new hand, without those letters in it.

    Has no side effects: does not modify hand.

    word: string
    hand: dictionary (string -> int)    
    returns: dictionary (string -> int)
    """
    hand2 = hand.copy() # not to mutate hand
    for i in word:
        try:
            if hand2[i] == 0:
                pass
            else:
                hand2[i] -= 1
        except KeyError:
            return False
    return hand2
    
    

#print(updateHand({'a': 0, 'q': 1, 'u': 1, 'l': 2, 'm': 1, 'i': 1}, 'quail'))
#{'a': 0, 'q': 0, 'u': 0, 'i': 0, 'm': 1, 'l': 1}
#print(updateHand({'a': 2, 'r': 2, 't': 2, 'p': 3, 'l': 2, 'c': 2}, 'claptrap'))
#{'a': 0, 'r': 1, 'p': 1, 't': 1, 'c': 1, 'l': 1}

#
# Problem #3: Test word validity
#
def isValidWord(word, hand, wordList):
    """
    Returns True if word is in the wordList and is entirely
    composed of letters in the hand. Otherwise, returns False.

    Does not mutate hand or wordList.
   
    word: string
    hand: dictionary (string -> int)
    wordList: list of lowercase strings
    """
    #print(updateHand(hand, word))
    
    #print(getFrequencyDict(word))
    
    hand2 = hand.copy()
    total = 0
    for i in word:
        if i in getFrequencyDict(hand2):
            total += 1
            hand2[i] -= 1
            if hand2[i] < 0:
                return False
        else:
            return False
    if total == len(word) and word in wordList:
        return True
    else:
        return False
    
"""               
    if updateHand(hand, word):
        return word in wordList
    else:
        return False
"""
#print(isValidWord('rapture', {'u': 1, 'p': 2, 'e': 1, 't': 1, 'a': 3, 'r': 1}, 'rapture'))
#False
#print(isValidWord('chayote', {'y': 1, 'u': 2, 'o': 2, 'c': 2, 't': 2, 'z': 1, 'h': 1, 'a': 1}, 'chayote'))
#False
#print(isValidWord('hammer', {'e': 1, 'm': 2, 'h': 1, 'r': 1, 'a': 1}, 'hammer'))
#True

#
# Problem #4: Playing a hand
#

def calculateHandlen(hand):
    """ 
    Returns the length (number of letters) in the current hand.
    
    hand: dictionary (string-> int)
    returns: integer
    """
    wordLen = 0
    for i in hand:
        #try:
        if hand[i] > 0:
            wordLen += hand.get(i)
            #wordLen += 1
        else:
            pass
    return wordLen
            
#print(calculateHandlen({'b': 1, 'a': 1, 'c': 0})) #2
#print(calculateHandlen({'o': 1, 'w': 3, 'i': 1, 'p': 1, 'c': 1, 'z': 1, 'v': 3, 'h': 1, 'l': 1})) #13

def playHand(hand, wordList, n):
    """
    Allows the user to play the given hand, as follows:

    * The hand is displayed.
    * The user may input a word or a single period (the string ".") 
      to indicate they're done playing
    * Invalid words are rejected, and a message is displayed asking
      the user to choose another word until they enter a valid word or "."
    * When a valid word is entered, it uses up letters from the hand.
    * After every valid word: the score for that word is displayed,
      the remaining letters in the hand are displayed, and the user
      is asked to input another word.
    * The sum of the word scores is displayed when the hand finishes.
    * The hand finishes when there are no more unused letters or the user
      inputs a "."

      hand: dictionary (string -> int)
      wordList: list of lowercase strings
      n: integer (HAND_SIZE; i.e., hand size required for additional points)
      
    """
    # BEGIN PSEUDOCODE <-- Remove this comment when you code this function; do your coding within the pseudocode (leaving those comments in-place!)
    # Keep track of the total score
    total = 0
    
    # As long as there are still letters left in the hand:
    handLen = calculateHandlen(hand)
    while handLen:
    
        # Display the hand
        #print(hand)
        #print(displayHand(hand))
        print('Current hand: ',  end=""), displayHand(hand)                                    
        
        # Ask user for input
        word = input('Enter word, or a "." to indicate that you are finished: ')
        
        # If the input is a single period:
        if word == '.':
        
            # End the game (break out of the loop)
            handLen = 0
            print('Goodbye! ', end="")  
            
        # Otherwise (the input is not a single period):
        else:
        
            # If the word is not valid:
            if not isValidWord(word, hand, wordList):
            
                # Reject invalid word (print a message followed by a blank line)
                print('Invalid word, please try again.')
                print()

            # Otherwise (the word is valid):
            else:

                # Tell the user how many points the word earned, and the updated total score, in one line followed by a blank line
                point = getWordScore(word, n)
                total += point
                print('" ' + word + ' "', 'earned', point, 'points. Total: ', total, 'points')                
                print()

                # Update the hand
                hand = updateHand(hand, word)
                handLen = calculateHandlen(hand)
            if handLen == 0:        
                handLen = 0
                print('Run out of letters. ', end="")                
                #print(hand)
                
    # Game is over (user entered a '.' or ran out of letters), so tell user the total score
    print('Total score: ' + str(total) + ' points.')
    print()
    playGame(wordList)


#wordList = loadWords()
#playHand({'h':1, 'i':1, 'c':1, 'z':1, 'm':2, 'a':1}, wordList, 7) #him, fast
#playHand({'w':1, 's':1, 't':2, 'a':1, 'o':1, 'f':1}, wordList, 7) #tow,fast 
#playHand({'n':1, 'e':1, 't':1, 'a':1, 'r':1, 'i':2}, wordList, 7) #inertia
#playHand(({'a': 1, 'z': 1}), wordList, 2) #zo -> invalid #za -> finish


#
# Problem #5: Playing a game
# 


def playGame(wordList):
    """
    Allow the user to play an arbitrary number of hands.

    1) Asks the user to input 'n' or 'r' or 'e'.
      * If the user inputs 'n', let the user play a new (random) hand.
      * If the user inputs 'r', let the user play the last hand again.
      * If the user inputs 'e', exit the game.
      * If the user inputs anything else, tell them their input was invalid.
 
    2) When done playing the hand, repeat from step 1 
    """
    
    totalGame = 0
    while True:
        print()
        user = input('Enter n to deal a new hand, r to replay the last hand, or e to end game: ')
        user = user.lower()
        if user == 'n':
            handCopy = dealHand(HAND_SIZE)
            playHand(handCopy, wordList, HAND_SIZE)
            totalGame += 1
        elif user == 'r':
            if totalGame == 0:
                print('You have not played a hand yet. Please play a new hand first!')
                continue
            else:
                playHand(handCopy, wordList, HAND_SIZE)
        elif user == 'e':
            break
        else:
            print('Invalid command.')
            continue
   
#wordList = loadWords()   
#playGame(wordList)


#
# Build data structures used for entire session and play game
#
if __name__ == '__main__':
    wordList = loadWords()
    playGame(wordList)
