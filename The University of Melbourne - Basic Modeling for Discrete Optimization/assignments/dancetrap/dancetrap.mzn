% Beauty Trap (20/20)
% Perform the most seductive dance 
% @author: salimt
include "regular.mzn";

enum LEGS = {spin, leap, waltz, curtsey, prep, stand};
enum ARMS = {beckon, out, up, wrapped, neutral};
enum FACE = {smile, wink, batt, think, glow, blank};

int: maxlen;
set of int: STEP = 1..maxlen;
array[LEGS] of int: maxlegs;
array[ARMS] of int: maxarms;
array[FACE] of int: maxface;
constraint assert(maxlegs[stand] >= maxlen, "maxlegs[stand] smaller than maxlen");
constraint assert(maxarms[neutral] >= maxlen, "maxarms[neutral] smaller than maxlen");
constraint assert(maxface[blank] >= maxlen, "maxface[blank] smaller than maxlen");
array[LEGS,ARMS] of int: dance_value;
array[ARMS,FACE] of int: entice_value;
constraint assert(dance_value[stand,neutral] = 0, "incorrect dance_value array");
constraint assert(entice_value[neutral,blank] = 0, "incorrect entice_value array");
int: boredom; % how bored each step make the viewer

var STEP: len;
array[STEP] of var LEGS: legs;
array[STEP] of var ARMS: arms;
array[STEP] of var FACE: face;

% CHECK ANY COMBO CANNOT BE BELOW 0
constraint forall(i in STEP)(dance_value[legs[i], arms[i]] >= 0  /\
                             entice_value[arms[i], face[i]] >= 0);

% FINAL STAND
constraint forall(i in len+1..maxlen)(legs[i] == stand   /\
                                      arms[i] == neutral /\
                                      face[i] == blank);

% CONSTRAINT THE NUMBER OF MOVEMENTS CAN BE DONE
constraint forall(i in STEP)((sum(j in STEP)(face[i]=face[j])) <= maxface[face[i]] /\
                             (sum(j in STEP)(legs[i]=legs[j])) <= maxlegs[legs[i]] /\
                             (sum(j in STEP)(arms[i]=arms[j])) <= maxarms[arms[i]]);


% CONSTRAINT FOR LEG MOVEMENTS
constraint forall(i in 1..maxlen-3)(legs[i]=waltz -> (sum(j in i..i+3)(legs[i]=legs[j]) < 4));
constraint forall(i in STEP)(legs[i]=waltz ->
                            forall(j in i+1..maxlen)(legs[j]=curtsey ->
                                   exists([legs[k]=stand | k in i+1..j-1])));
                                   
constraint regular([legs[i] | i in STEP], 7, 6, d2, 1, {7});
array[1..7, LEGS] of 0..7: d2 =
     [|2,0,4,5,6,7
      |0,0,0,5,6,7
      |2,0,4,0,0,7
      |2,0,4,0,6,7
      |2,0,4,5,6,7
      |2,3,4,5,6,7
      |2,0,4,5,6,7|];                          

% CONSTRAINT FOR ARM MOVEMENTS
constraint regular([arms[i] | i in STEP], 6, 5, d, 1, {6});
array[1..6, ARMS] of 0..6: d =
     [|0,3,4,5,6
      |0,3,4,5,6
      |2,0,4,0,6
      |2,3,0,0,6
      |0,3,4,0,6
      |0,3,4,5,6|];   


% CONSTRAINT FOR FACE MOVEMENTS
constraint forall(i in 1..maxlen-2)(face[i]!=blank -> (sum(j in i..i+2)(face[i]=face[j]) < 3));
constraint regular([face[i] | i in STEP], 7, 6, d3, 1, {7});
array[1..7, FACE] of 0..7: d3 =
     [|2,3,4,5,6,7
      |2,3,4,0,6,7
      |2,3,4,5,6,7
      |2,3,4,0,6,7
      |2,3,4,5,6,7
      |2,3,4,5,6,7
      |2,3,4,5,6,7|];
      

var int: obj;
constraint obj = sum(i in STEP)(dance_value[legs[i], arms[i]] + entice_value[arms[i], face[i]])
               - (boredom * len);
solve maximize obj;

output["len = \(len); \nlegs = \(legs); \narms = \(arms); \nface = \(face); \nobj = \(obj);"];
