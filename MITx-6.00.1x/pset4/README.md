# Python based Scrubble Game
## Computer also can play besides User

#### Test Cases to Understand the Code: 

> compPlayHand({'a': 1, 'p': 2, 's': 1, 'e': 1, 'l': 1}, wordList, 6)
>> Current Hand: a p p s e l
>>> "appels" earned 110 points. Total: 110 points
>>>> Total score: 110 points.

> compPlayHand({'a': 2, 'c': 1, 'b': 1, 't': 1}, wordList, 5)
>> Current Hand: a a c b t "acta" 
>>> earned 24 points. Total: 24 points 
>>>> Current Hand: b Total score: 24 points. 

> compPlayHand({'a': 2, 'e': 2, 'i': 2, 'm': 2, 'n': 2, 't': 2}, wordList, 12)
>> Current Hand: a a e e i i m m n n t t
>>> "immanent" earned 96 points. Total: 96 points
>>>> Current Hand: a e t i
>>>>> "ait" earned 9 points. Total: 105 points
>>>>>> Current Hand: e
>>>>>>> Total score: 105 points.
