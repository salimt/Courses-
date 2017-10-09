#include <stdio.h>
#include <math.h>
#include <cs50.h>

/**
 * greedy.c
 *
 * salimt
 *
 * Asks the user how much change is owed (in $) and prints the minimum number of coins with which said change can be made.
 */
 

int main(void){
    float n;
    int count = 0, amount=0;
    do{
        printf("How much change is owed? Note: 6.22 represents 6 dollars and 22 cents.\n");
        scanf(" %f", &n);
    }while(n<0);
    n *= 100.0;
    amount = (int)round(n);
    while(amount>=100) {count++; amount-=100;}
    while(amount>=50) {count++; amount-=50;}
    while(amount>=25) {count++; amount-=25;}
    while(amount>=10) {count++; amount-=10;}
    while(amount>=5) {count++; amount-=5;}
    while(amount>=1) {count++; amount-=1;}
    while(amount==0) {printf("Total %d coins needed.\n", count); return 0;}
}
