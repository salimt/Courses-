#include <stdio.h>
#include <cs50.h>
#include <string.h>
#include <ctype.h>
 
/**
 * initials.c
 * 
 * salimt
 * 
 */ 
 
int main(void){
    string name = GetString();
    printf("%c", toupper(name[0]));
    if(name!=NULL){
        for(int i=0, n=strlen(name); i<n; i++){
            if(name[i]==' '){
                printf("%c", toupper(name[i+1]));
            }
        }
    }printf("\n");
}