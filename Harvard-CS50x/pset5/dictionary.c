#include <stdbool.h>
#include <stdio.h>
#include <ctype.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

#include "dictionary.h"

// create nodes for linked list
typedef struct node
{
   bool is_word;
   struct node* children[27];
}node;
node* root;
int ln = 27;
// create hash function
int hash(const char c)  
{
    if (c == '\'') 
    {
        return 26;    
    }
    else 
    {
        return tolower(c) % 'a';
    }
}

// create global variable to count size
int total_nodes = 0;

/**
 * Returns true if word is in dictionary else false.
 */
bool check(const char* word)  
{
    node *cursor=root;
    for(int i=0; word[i]!='\0'; i++){
        int h = hash(word[i]);
        if(cursor->children[h]==NULL){
            return false;
        }else{
            cursor=cursor->children[h];
        }
    }
    // once at end of input word, check if is_word is true
    return cursor->is_word;
}
/**
 * Loads dictionary into memory. Returns true if successful else false.
 */
bool load(const char* dictionary)  
{
    FILE *fp=fopen(dictionary ,"r");
    
    root=malloc(sizeof(node));
    node *cursor=root;
    
    for(int c=fgetc(fp); c!=EOF; c=fgetc(fp)){
        if(c=='\n'){
            cursor->is_word=true;
            total_nodes++;
            cursor=root;
        }else{
            int h = hash(c);
            if(cursor->children[h]==NULL){
                cursor->children[h]=malloc(sizeof(node));
            }cursor=cursor->children[h];
        }
    }
    fclose(fp);
    return true;
}
/**
 * Returns number of words in dictionary if loaded else 0 if not yet loaded.
 */
unsigned int size(void)
{
    // TODO
    return total_nodes;
}

/**
 * Unloads dictionary from memory. Returns true if successful else false.
 */
bool free_nodes(node* cursor)
{
    for(int i=0; i<27; i++){
        if(cursor->children[i]!=NULL){
            free_nodes(cursor->children[i]);
        }
    }
    free(cursor);
    return true;
}

/**
 * Unloads dictionary from memory.  Returns true if successful else false.
 */
bool unload(void)
{
    // Start at root
    return free_nodes(root);
}
