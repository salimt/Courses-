// Programming Languages, Dan Grossman
// Section 3: Optional: Closure Idioms Without Closures in C

// Note: This code compiles but has not been carefully tested. 
//       Bug reports welcome.

#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>

// The key point is that function pointers are only code pointers

// Rather than create structs for closures, which would work fine,
// we follow common C practice of having higher-order functions 
// take explicit environment fields as another argument
//  -- if they don't, then they are much less useful 

// void* requires lots of unchecked conversions between types,
// but C has no notion of type variables

typedef struct List list_t;
struct List {
  void * head;
  list_t * tail;
};

list_t * makelist (void * x, list_t * xs) {
  list_t * ans = (list_t *)malloc(sizeof(list_t));
  ans->head = x;
  ans->tail = xs;
  return ans;
}

// as in the Java version, we show simple recursive solutions because
// the loop-based ones require mutation and previous pointers.
// But the more important point is the explicit env field passed to the
// function pointer
list_t * map(void* (*f)(void*,void*), void* env, list_t * xs) {
  if(xs==NULL)
    return NULL;
  return makelist(f(env,xs->head), map(f,env,xs->tail));
}

list_t * filter(bool (*f)(void*,void*), void* env, list_t * xs) {
  if(xs==NULL)
    return NULL;
  if(f(env,xs->head))
    return makelist(xs->head, filter(f,env,xs->tail));
  return filter(f,env,xs->tail);
}

int length(list_t* xs) {
  int ans = 0;
  while(xs != NULL) {
    ++ans;
    xs = xs->tail;
  }
  return ans;
}

// clients of our list implementation:
// [the clients that cast from void* to intptr_t are technically not legal C, 
//  as explained in detail below if curious]

// awful type casts to match what map expects
void* doubleInt(void* ignore, void* i) {
  return (void*)(((intptr_t)i)*2);
}

// assumes list holds intptr_t fields
list_t * doubleAll(list_t * xs) {
  return map(doubleInt, NULL, xs);
}

// awful type casts to match what filter expects
bool isN(void* n, void* i) {
  return ((intptr_t)n)==((intptr_t)i);
}

// assumes list hold intptr_t fields
int countNs(list_t * xs, intptr_t n) {
  return length(filter(isN, (void*)n, xs));
}

/*
  The promised explanation: Some of the client code above tries to use
  a number for the environment by using a number (intptr_t) where a
  pointer (void *) is expected.  This is technically not allowed: any
  pointer (including void*) can be cast to intptr_t (always) and the
  result can be cast back to the pointer type, but that is different
  than starting with an intptr_t and casting it to void* and then back
  to intptr_t.

  It appears there is no legal, portable way to create a number that
  can be cast to void* and back.  People do this sort of thing often,
  but the resulting code is not strictly portable.  So what should we
  do for our closures example besides ignore this and write
  non-portable code using int or intptr_t?
  
  Option 1 is to use an int* for the environment, passing a pointer to the
  value we need.  That is the most common approach and what we need to do for
  environments with more than one value in them anyway.  For the examples above,
  it would work to pass the address of a stack-allocated int, but that works
  only because the higher-order functions we are calling will not store those
  pointers in places where they might be used later after the stack variable
  is deallocated.  So it works fine for examples like map and filter, but would
  not work for callback idioms.  For those, the pointer should refer to memory
  returned by malloc or a similar library function.

  Option 2 is to change our approach to have the higher-order functions use
  intptr_t for the type of the environment instead of void*.  This works in
  general since other code can portably cast from any pointer type to
  intptr_t.  It is a less standard approach -- one commonly sees void* used
  as we have in the code above.
*/
