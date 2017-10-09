/**
 * helpers.c
 *
 * Helper functions for Problem Set 3.
 */
 
#include <cs50.h>
#include <stdio.h>
#include "helpers.h"

/**
 * Returns true if value is in array of n values, else false.
 */
bool search(int value, int values[], int n)
{
    if(n<0){
        return 0;
    }for(int i=0; i<n; i++){
        if(value==values[i]){
            return true;
        }
    }
    return false;
}

/**
 * Sorts array of n values.
 */
void sort(int values[], int n){
    int swap, i;
    do{
        swap=0;
    for(i=0; i<n-1; i++){
        if(values[i]>values[i+1]){
            int temp = values[i+1];
            values[i+1] = values[i];
            values[i] = temp;
            swap=1;
            }printf("%d\t", values[i]);
        }printf("%d\n", values[i]);
    }while(swap==1);
}

