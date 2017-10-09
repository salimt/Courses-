#include <cs50.h>
#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/**
 * caesar.c
 *
 * Encrypts messages using the Caesar cipher. Retrieves the message to encrypt via user prompt.
 * 
 * Usage: ./caesar key
 * 
 * where key is a non-negative integer that is the cipher key.
 * 
 * salimt
 */

int main(int argc, string argv[]){
    int key = atoi(argv[1]);
    if((key == '\0') || isalpha(key) || argc!=2){
        printf("You did not enter a command line argument for the Caesar encryption key!\n");
        return 1;
    }
    printf("Please enter the phrase you would like to encrypt: ");
    string txt = GetString();
    printf("Encrypted: ");
    for(int k=0, n=strlen(txt); k<n; k++){
        char c=txt[k];
        if(isupper(c)){
            c=((c-'A'+key)%26)+'A';
        }if(islower(c)){
            c=((c-'a'+key)%26)+'a';
        }putchar(c);
    }putchar('\n');
    return 0;
}
