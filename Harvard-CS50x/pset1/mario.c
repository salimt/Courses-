#include <stdio.h>
#include <cs50.h>
/**
 * mario.c
 *
 * salimt
 *
 */
 

int main(void){
    int up = 0;
    printf("basamak sayisi(1-14): ");
    scanf(" %i", &up);
    if(up>15 || up<=0){
       return 1;
    }
    for(int i=1; i<=up; i++){
        int space = up - i;
        for(int j=0; j<space; j++){
            printf(" ");
        }for(int k=0; k<i; k++){
            printf("$");
        }printf(" ");
        for(int f=0; f<i; f++){
            printf("€");
        }printf("\n");
    }return 0;
}