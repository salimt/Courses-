# Breaking the Vigenère Cipher
_this was the final homework for Duke University's Java Programming: Arrays, Lists, and Structured Data._

This is a simple Vigenere cipher which works in 8 languages. Read more here: http://en.wikipedia.org/wiki/Vigenère_cipher
The supported languages are; English, French, German, Dutch, Danish, Portuguese, Spanish and Italian.

The Vigenère cipher is a method of encrypting alphabetic text by using a series of different Caesar ciphers based on the letters of a keyword. It is a simple form of polyalphabetic substitution.
The idea is that you can use this encryption with only a pen and paper, and it doesn't hurt to have a tabula recta as well. The key will only be repeated over alphabetic and numeric characters, so spaces and punctuation are ignored. In fact, it's better to not use any punctuation as it could give hints as to the nature of the message and the key. Note that the message can be broken using letter frequency analysis because of the repeating nature of the key.

in VigenereBreaker.java change the encrypted file name under breakVigenere().

> ### **Example for a German encrypted text:**

Encrypted: 
Ziöft uguk; – vavchury!
Htköc lei zühk, jeg lüvf zvf jftöe.
Lzuntcw sluow Qqqvo jeg Lfowteköwe!
Hzh kvueryzucuhz.

Decrypted:
 <a><img src="https://i.imgur.com/rWtySOH.png"/></a> 
