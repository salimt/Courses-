#include <cs50.h>
#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/**
 * vigenere.c
 *
 * Encrypts messages using the Vigenere cipher. Retrieves the message to encrypt via user prompt.
 * 
 * Usage: ./vigenere keyword
 * 
 * where keyword is the cipher keyword that is entirely composed of alphabetical characters.
 * 
 * salimt
 */

int main(int argc, string argv[]) {
    if (argc != 2) {
        printf("missing key argument\n");
        return 1;
    }
    string key = argv[1];
    int klen = strlen(key);
    printf("%lu is the length of the key\n", strlen(key));
    for (int i = 0; i < klen; i++) {
        if (!isalpha((unsigned char)key[i])) {
            printf("only letters allowed\n");
            return 1;
        }
        key[i] = tolower((unsigned char)key[i]) - 'a';
    }
    printf("Please enter the phrase you would like to encrypt: ");
    string txt = GetString();
    printf("%lu is the length of the phrase.\n", strlen(txt));
    printf("Encrypted: ");
    for (int i = 0, j = 0; i<strlen(txt); i++) {
        int c = (unsigned char)txt[i];
        if (isupper(c)) {
            c = (c - 'A' + key[j++ % klen]) % 26 + 'A';
        }if (islower(c)) {
            c = (c - 'a' + key[j++ % klen]) % 26 + 'a';
        }putchar(c);
    }
    putchar('\n');
    return 0;
}