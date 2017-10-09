# [Problem Set 5: Mispellings](http://docs.cs50.net/2016/fall/psets/5/pset5.html)

### Objectives
- Allow you to design and implement your own data structure.
- Optimize your code’s (real-world) running time.

### Spell Checking

Alright, the challenge now before you is to implement load, check, size, and unload as efficiently as possible, in such a way that TIME IN load, TIME IN check, TIME IN size, and TIME IN unload are all minimized. To be sure, it’s not obvious what it even means to be minimized, inasmuch as these benchmarks will certainly vary as you feed speller different values for dictionary and for text. But therein lies the challenge, if not the fun, of this problem set. This problem set is your chance to design. Although we invite you to minimize space, your ultimate enemy is time.

In speller.c, we’ve put together a program that’s designed to spell-check a file after loading a dictionary of words from disk into memory. Unfortunately, we didn’t quite get around to implementing the loading part. Or the checking part. Both (and a bit more) we leave to you!

Within the default dictionary, mind you, are 143,091 words, all of which must be loaded into memory! In fact, take a peek at that file to get a sense of its structure and size. Notice that every word in that file appears in lowercase (even, for simplicity, proper nouns and acronyms). From top to bottom, the file is sorted lexicographically, with only one word per line (each of which ends with \n). No word is longer than 45 characters, and no word appears more than once.

So that you can test your implementation of speller, we’ve also provided you with a whole bunch of texts, among them the script from Austin Powers: International Man of Mystery, a sound bite from Ralph Wiggum, three million bytes from Tolstoy, some excepts from Machiavelli and Shakespeare, the entirety of the King James V Bible, and more.

### Results
Left = staff solution, right = my code (time in seconds): 💪

![My code vs. staff solution](http://i.imgur.com/J4j3BRS.png)
