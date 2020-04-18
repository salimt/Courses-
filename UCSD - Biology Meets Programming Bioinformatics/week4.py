"""
@author: salimt
"""

import random

"""
The functions in Motif.py will return 0 for an entire motif probability even if only
one of the positions has a 0 probability of existing in the consensus string.

This doesn't make sense because a motif that differs from the consensus string
at every position will also get a total probability of 0.

In order to improve this unfair scoring, bioinformaticians often substitute zeroes
with small numbers called pseudocounts.
"""
# Input:  String Text, an integer k, and profile matrix Profile
# Output: String of most probable pattern
def ProfileMostProbableKmer(text, k, profile):
    maxProb = Pr(text[0:k], profile)
    maxMotif = text[0:0+k]
    for i in range(1, len(text)-k+1):
        tempProb = Pr(text[i:i+k], profile)
        if tempProb > maxProb:
            maxProb = tempProb
            maxMotif = text[i:i+k]
    return maxMotif

# Input:  A set of kmers Motifs
# Output: CountWithPseudocounts(Motifs)
def CountWithPseudocounts(Motifs):
    t = len(Motifs)
    k = len(Motifs[0])
    initialList = {i: [0]*k for i in "ACGT"}
    for i in range(k):
        for j in range(t):
            initialList[Motifs[j][i]][i] = initialList.get(Motifs[j][i])[i]+1
    initialList = {k: [initialList.get(k)[i]+1 for i in range(len(v))] for k,v in initialList.items()}
    return initialList

"""
ProfileWithPseudocounts(Motifs) that takes a list of strings Motifs as input and
returns the profile matrix of Motifs with pseudocounts as a dictionary of lists
"""

# Input:  A set of kmers Motifs
# Output: ProfileWithPseudocounts(Motifs)
def ProfileWithPseudocounts(Motifs):
    t = len(Motifs)
    k = len(Motifs[0])
    profile = {} # output variable
    countMotifs = CountWithPseudocounts(Motifs)
    for key,val in countMotifs.items():
        profile[key] = [i/(t+4) for i in val]
    return profile

# motif1 = "AACGTA"
# motif2 = "CCCGTT"
# motif3 = "CACCTT"
# motif4 = "GGATTA"
# motif5 = "TTCCGG"
# motifs = [motif1, motif2, motif3, motif4, motif5]
#
# print(ProfileWithPseudocounts(motifs))

"""
 Write a function GreedyMotifSearchWithPseudocounts(Dna, k, t) that takes a list
 of strings Dna followed by integers k and t and returns the result of running
 GreedyMotifSearch, where each profile matrix is generated with pseudocounts
 """
# Input:  A list of kmers Dna, and integers k and t (where t is the number of kmers in Dna)
# Output: GreedyMotifSearch(Dna, k, t)
def GreedyMotifSearchWithPseudocounts(Dna, k, t):
    BestMotifs = [Dna[i][0:k] for i in range(0, t)]
    for i in range(len(Dna[0])-k+1):
        Motifs = []
        Motifs.append(Dna[0][i:i+k])
        for j in range(1, t):
            P = ProfileWithPseudocounts(Motifs[0:j])
            Motifs.append(ProfileMostProbableKmer(Dna[j], k, P))
        if Score(Motifs) < Score(BestMotifs):
            BestMotifs = Motifs
    return BestMotifs

# Input:  A set of kmers Motifs
# Output: A consensus string of Motifs.
def Consensus(Motifs):
    finalMotif = []
    motifCounts = CountWithPseudocounts(Motifs)
    for i in range(len(Motifs[0])):
        maxVal = 0
        motif = ""
        for k,v in motifCounts.items():
            if v[i] > maxVal:
                maxVal = v[i]
                motif = k
        finalMotif.append(motif)
        
    return "".join(finalMotif)

# Input:  A set of k-mers Motifs
# Output: The score of these k-mers.
def Score(Motifs):
    consensus = Consensus(Motifs)
    motifCounts = CountWithPseudocounts(Motifs)
    score = [len(Motifs)-motifCounts.get(consensus[i])[i] for i in range(len(Motifs[0]))]
    return sum(score)

# Input:  String Text and profile matrix Profile
# Output: Probability value
def Pr(Text, Profile):
    score = [Profile.get(Text[i])[i] for i in range(len(Text))]
    product = 1
    for x in score:
        product *= x
    return product

# k = 3
# t = 5
# Dna = ["GGCGTTCAGGCA", "AAGAATCAGTCA", "CAAGGAGTTCGC", "CACGTCAATCAC", "CAATAATATTCG"]
# print(GreedyMotifSearchWithPseudocounts(Dna, k, t))

# Input:  A profile matrix Profile and a list of strings Dna
# Output: Profile-most probable k-mer from each row of Dna
def Motifs(Profile,k, Dna):
    return [ProfileMostProbableKmer(dna,k,Profile) for dna in Dna]

# Profile = {'A': [0.8, 0.0, 0.0, 0.2],
#            'C': [0.0, 0.6, 0.2, 0.0],
#            'G': [0.2, 0.2, 0.8, 0.0],
#            'T': [0.0, 0.2, 0.0, 0.8]}
#
# Dnas = ["TTACCTTAAC", "GATGTCTGTC", "ACGGCGTTAG", "CCCTAACGAG", "CGTCAGAGGT"]
#
# print(Motifs(Profile, Dnas))

# Input:  A list of strings Dna, and integers k and t
# Output: RandomMotifs(Dna, k, t)
# HINT:   You might not actually need to use t since t = len(Dna), but you may find it convenient
def RandomMotifs(Dna, k, t):
    randMotifs = []
    for dna in Dna:
        randomNum = random.randint(1, abs(k-t))
        randMotifs.append(dna[randomNum: randomNum+k])
    return randMotifs
#
# Dnas = ["TTACCTTAAC", "GATGTCTGTC", "ACGGCGTTAG", "CCCTAACGAG", "CGTCAGAGGT"]
# k = 3
# t = len(Dnas)
# print(RandomMotifs(Dnas, k, t))

# Input:  Positive integers k and t, followed by a list of strings Dna
# Output: return a list of random kmer motifs
def RandomizedMotifSearch(Dna, k, t):
    M = RandomMotifs(Dna, k, t)
    BestMotifs = M
    while True:
        Profile = ProfileWithPseudocounts(M)
        M = Motifs(Profile,k, Dna)
        if Score(M) < Score(BestMotifs):
            BestMotifs = M
        else:
            return BestMotifs 

#Dna = ["GCGCCCCGCCCGGACAGCCATGCGCTAACCCTGGCTTCGATGGCGCCGGCTCAGTTAGGGCCGGAAGTCCCCAATGTGGCAGACCTTTCGCCCCTGGCGGACGAATGACCCCAGTGGCCGGGACTTCAGGCCCTATCGGAGGGCTCCGGCGCGGTGGTCGGATTTGTCTGTGGAGGTTACACCCCAATCGCAAGGATGCATTATGACCAGCGAGCTGAGCCTGGTCGCCACTGGAAAGGGGAGCAACATC",
#"CCGATCGGCATCACTATCGGTCCTGCGGCCGCCCATAGCGCTATATCCGGCTGGTGAAATCAATTGACAACCTTCGACTTTGAGGTGGCCTACGGCGAGGACAAGCCAGGCAAGCCAGCTGCCTCAACGCGCGCCAGTACGGGTCCATCGACCCGCGGCCCACGGGTCAAACGACCCTAGTGTTCGCTACGACGTGGTCGTACCTTCGGCAGCAGATCAGCAATAGCACCCCGACTCGAGGAGGATCCCG",
#"ACCGTCGATGTGCCCGGTCGCGCCGCGTCCACCTCGGTCATCGACCCCACGATGAGGACGCCATCGGCCGCGACCAAGCCCCGTGAAACTCTGACGGCGTGCTGGCCGGGCTGCGGCACCTGATCACCTTAGGGCACTTGGGCCACCACAACGGGCCGCCGGTCTCGACAGTGGCCACCACCACACAGGTGACTTCCGGCGGGACGTAAGTCCCTAACGCGTCGTTCCGCACGCGGTTAGCTTTGCTGCC",
#"GGGTCAGGTATATTTATCGCACACTTGGGCACATGACACACAAGCGCCAGAATCCCGGACCGAACCGAGCACCGTGGGTGGGCAGCCTCCATACAGCGATGACCTGATCGATCATCGGCCAGGGCGCCGGGCTTCCAACCGTGGCCGTCTCAGTACCCAGCCTCATTGACCCTTCGACGCATCCACTGCGCGTAAGTCGGCTCAACCCTTTCAAACCGCTGGATTACCGACCGCAGAAAGGGGGCAGGAC",
#"GTAGGTCAAACCGGGTGTACATACCCGCTCAATCGCCCAGCACTTCGGGCAGATCACCGGGTTTCCCCGGTATCACCAATACTGCCACCAAACACAGCAGGCGGGAAGGGGCGAAAGTCCCTTATCCGACAATAAAACTTCGCTTGTTCGACGCCCGGTTCACCCGATATGCACGGCGCCCAGCCATTCGTGACCGACGTCCCCAGCCCCAAGGCCGAACGACCCTAGGAGCCACGAGCAATTCACAGCG",
#"CCGCTGGCGACGCTGTTCGCCGGCAGCGTGCGTGACGACTTCGAGCTGCCCGACTACACCTGGTGACCACCGCCGACGGGCACCTCTCCGCCAGGTAGGCACGGTTTGTCGCCGGCAATGTGACCTTTGGGCGCGGTCTTGAGGACCTTCGGCCCCACCCACGAGGCCGCCGCCGGCCGATCGTATGACGTGCAATGTACGCCATAGGGTGCGTGTTACGGCGATTACCTGAAGGCGGCGGTGGTCCGGA",
#"GGCCAACTGCACCGCGCTCTTGATGACATCGGTGGTCACCATGGTGTCCGGCATGATCAACCTCCGCTGTTCGATATCACCCCGATCTTTCTGAACGGCGGTTGGCAGACAACAGGGTCAATGGTCCCCAAGTGGATCACCGACGGGCGCGGACAAATGGCCCGCGCTTCGGGGACTTCTGTCCCTAGCCCTGGCCACGATGGGCTGGTCGGATCAAAGGCATCCGTTTCCATCGATTAGGAGGCATCAA",
#"GTACATGTCCAGAGCGAGCCTCAGCTTCTGCGCAGCGACGGAAACTGCCACACTCAAAGCCTACTGGGCGCACGTGTGGCAACGAGTCGATCCACACGAAATGCCGCCGTTGGGCCGCGGACTAGCCGAATTTTCCGGGTGGTGACACAGCCCACATTTGGCATGGGACTTTCGGCCCTGTCCGCGTCCGTGTCGGCCAGACAAGCTTTGGGCATTGGCCACAATCGGGCCACAATCGAAAGCCGAGCAG",
#"GGCAGCTGTCGGCAACTGTAAGCCATTTCTGGGACTTTGCTGTGAAAAGCTGGGCGATGGTTGTGGACCTGGACGAGCCACCCGTGCGATAGGTGAGATTCATTCTCGCCCTGACGGGTTGCGTCTGTCATCGGTCGATAAGGACTAACGGCCCTCAGGTGGGGACCAACGCCCCTGGGAGATAGCGGTCCCCGCCAGTAACGTACCGCTGAACCGACGGGATGTATCCGCCCCAGCGAAGGAGACGGCG",
#"TCAGCACCATGACCGCCTGGCCACCAATCGCCCGTAACAAGCGGGACGTCCGCGACGACGCGTGCGCTAGCGCCGTGGCGGTGACAACGACCAGATATGGTCCGAGCACGCGGGCGAACCTCGTGTTCTGGCCTCGGCCAGTTGTGTAGAGCTCATCGCTGTCATCGAGCGATATCCGACCACTGATCCAAGTCGGGGGCTCTGGGGACCGAAGTCCCCGGGCTCGGAGCTATCGGACCTCACGATCACC"]
#
## set t equal to the number of strings in Dna, k equal to 15, and N equal to 100.
#t=len(Dna)
#k=15
#N=100
## Call RandomizedMotifSearch(Dna, k, t) N times, storing the best-scoring set of motifs
## resulting from this algorithm in a variable called BestMotifs
#M = RandomizedMotifSearch(Dna, k, t)
#BestMotifs = M
#for i in range(N):
#    if Score(M) < Score(BestMotifs):
#        BestMotifs = M
## Print the BestMotifs variable
#print(BestMotifs)
## Print Score(BestMotifs)
#print(Score(BestMotifs))

"""
The function should divide each value in Probabilities by the sum of all values
in  Probabilities, then return the resulting dictionary
"""

# Input: A dictionary Probabilities, where keys are k-mers and values are the
# probabilities of these k-mers (which do not necessarily sum up to 1)
# Output: A normalized dictionary where the probability of each k-mer was
# divided by the sum of all k-mers' probabilities
def Normalize(Probabilities):
    sumProb = sum(Probabilities.values())
    output = {k: v/sumProb  for k,v in Probabilities.items()}
    return output


# Probabilities = {'A': 0.15, 'B': 0.6, 'C': 0.225, 'D': 0.225, 'E': 0.3}
# print(Normalize(Probabilities))

"""
This function takes a dictionary Probabilities whose keys are k-mers and whose
values are the probabilities of these k-mers. The function should return a
randomly chosen k-mer key with respect to the values in Probabilities
"""

# Input:  A dictionary Probabilities whose keys are k-mers and whose values are the probabilities of these kmers
# Output: A randomly chosen k-mer with respect to the values in Probabilities
def WeightedDie(Probabilities):
    rand = random.uniform(0, 1)
    for k,v in Probabilities.items():
        rand-=v
        if rand<=0:
            return k

# Probabilities = {'AA': 0.2, 'AT': 0.4, 'CC': 0.1, 'GG': 0.1, 'TT': 0.2}
# print(WeightedDie(Probabilities))

"""
Now that we can simulate a weighted die roll over a collection of probabilities
of strings, we need to make this function into a subroutine of a larger function
that randomly chooses a k-mer from a string Text based on a profile matrix profile
"""

# Input:  A string Text, a profile matrix Profile, and an integer k
# Output: ProfileGeneratedString(Text, profile, k)
def ProfileGeneratedString(Text, profile, k):
    n = len(Text)
    probabilities = {} 
    for i in range(0,n-k+1):
        probabilities[Text[i:i+k]] = Pr(Text[i:i+k], profile)
    probabilities = Normalize(probabilities)
    return WeightedDie(probabilities)

"""
RandomizedMotifSearch may change all t strings in Motifs in a single iteration.
This strategy may prove reckless, since some correct motifs (captured in Motifs)
may potentially be discarded at the next iteration.

GibbsSampler is a more cautious iterative algorithm that discards a single k-mer
from the current set of motifs at each iteration and decides to either keep it
or replace it with a new one.
"""

def GibbsSampler(Dna, k, t, N):
    Motifs = RandomMotifs(Dna, k, t)
    BestMotifs = Motifs
    for i in range(1,N):
        i = random.randint(0,t-1)
        Profile = ProfileWithPseudocounts(Motifs)
        Mi = ProfileGeneratedString(Dna[i], Profile, k)
        if Score(Motifs) < Score(BestMotifs):
            BestMotifs = Motifs
        else:
            return BestMotifs 

#Dna =["GCGCCCCGCCCGGACAGCCATGCGCTAACCCTGGCTTCGATGGCGCCGGCTCAGTTAGGGCCGGAAGTCCCCAATGTGGCAGACCTTTCGCCCCTGGCGGACGAATGACCCCAGTGGCCGGGACTTCAGGCCCTATCGGAGGGCTCCGGCGCGGTGGTCGGATTTGTCTGTGGAGGTTACACCCCAATCGCAAGGATGCATTATGACCAGCGAGCTGAGCCTGGTCGCCACTGGAAAGGGGAGCAACATC", "CCGATCGGCATCACTATCGGTCCTGCGGCCGCCCATAGCGCTATATCCGGCTGGTGAAATCAATTGACAACCTTCGACTTTGAGGTGGCCTACGGCGAGGACAAGCCAGGCAAGCCAGCTGCCTCAACGCGCGCCAGTACGGGTCCATCGACCCGCGGCCCACGGGTCAAACGACCCTAGTGTTCGCTACGACGTGGTCGTACCTTCGGCAGCAGATCAGCAATAGCACCCCGACTCGAGGAGGATCCCG", "ACCGTCGATGTGCCCGGTCGCGCCGCGTCCACCTCGGTCATCGACCCCACGATGAGGACGCCATCGGCCGCGACCAAGCCCCGTGAAACTCTGACGGCGTGCTGGCCGGGCTGCGGCACCTGATCACCTTAGGGCACTTGGGCCACCACAACGGGCCGCCGGTCTCGACAGTGGCCACCACCACACAGGTGACTTCCGGCGGGACGTAAGTCCCTAACGCGTCGTTCCGCACGCGGTTAGCTTTGCTGCC", "GGGTCAGGTATATTTATCGCACACTTGGGCACATGACACACAAGCGCCAGAATCCCGGACCGAACCGAGCACCGTGGGTGGGCAGCCTCCATACAGCGATGACCTGATCGATCATCGGCCAGGGCGCCGGGCTTCCAACCGTGGCCGTCTCAGTACCCAGCCTCATTGACCCTTCGACGCATCCACTGCGCGTAAGTCGGCTCAACCCTTTCAAACCGCTGGATTACCGACCGCAGAAAGGGGGCAGGAC", "GTAGGTCAAACCGGGTGTACATACCCGCTCAATCGCCCAGCACTTCGGGCAGATCACCGGGTTTCCCCGGTATCACCAATACTGCCACCAAACACAGCAGGCGGGAAGGGGCGAAAGTCCCTTATCCGACAATAAAACTTCGCTTGTTCGACGCCCGGTTCACCCGATATGCACGGCGCCCAGCCATTCGTGACCGACGTCCCCAGCCCCAAGGCCGAACGACCCTAGGAGCCACGAGCAATTCACAGCG", "CCGCTGGCGACGCTGTTCGCCGGCAGCGTGCGTGACGACTTCGAGCTGCCCGACTACACCTGGTGACCACCGCCGACGGGCACCTCTCCGCCAGGTAGGCACGGTTTGTCGCCGGCAATGTGACCTTTGGGCGCGGTCTTGAGGACCTTCGGCCCCACCCACGAGGCCGCCGCCGGCCGATCGTATGACGTGCAATGTACGCCATAGGGTGCGTGTTACGGCGATTACCTGAAGGCGGCGGTGGTCCGGA", "GGCCAACTGCACCGCGCTCTTGATGACATCGGTGGTCACCATGGTGTCCGGCATGATCAACCTCCGCTGTTCGATATCACCCCGATCTTTCTGAACGGCGGTTGGCAGACAACAGGGTCAATGGTCCCCAAGTGGATCACCGACGGGCGCGGACAAATGGCCCGCGCTTCGGGGACTTCTGTCCCTAGCCCTGGCCACGATGGGCTGGTCGGATCAAAGGCATCCGTTTCCATCGATTAGGAGGCATCAA", "GTACATGTCCAGAGCGAGCCTCAGCTTCTGCGCAGCGACGGAAACTGCCACACTCAAAGCCTACTGGGCGCACGTGTGGCAACGAGTCGATCCACACGAAATGCCGCCGTTGGGCCGCGGACTAGCCGAATTTTCCGGGTGGTGACACAGCCCACATTTGGCATGGGACTTTCGGCCCTGTCCGCGTCCGTGTCGGCCAGACAAGCTTTGGGCATTGGCCACAATCGGGCCACAATCGAAAGCCGAGCAG", "GGCAGCTGTCGGCAACTGTAAGCCATTTCTGGGACTTTGCTGTGAAAAGCTGGGCGATGGTTGTGGACCTGGACGAGCCACCCGTGCGATAGGTGAGATTCATTCTCGCCCTGACGGGTTGCGTCTGTCATCGGTCGATAAGGACTAACGGCCCTCAGGTGGGGACCAACGCCCCTGGGAGATAGCGGTCCCCGCCAGTAACGTACCGCTGAACCGACGGGATGTATCCGCCCCAGCGAAGGAGACGGCG", "TCAGCACCATGACCGCCTGGCCACCAATCGCCCGTAACAAGCGGGACGTCCGCGACGACGCGTGCGCTAGCGCCGTGGCGGTGACAACGACCAGATATGGTCCGAGCACGCGGGCGAACCTCGTGTTCTGGCCTCGGCCAGTTGTGTAGAGCTCATCGCTGTCATCGAGCGATATCCGACCACTGATCCAAGTCGGGGGCTCTGGGGACCGAAGTCCCCGGGCTCGGAGCTATCGGACCTCACGATCACC"]
#
## set t equal to the number of strings in Dna, k equal to 15, and N equal to 100
#t = len(Dna)
#k = 15
#N = 100
#
#
## Call GibbsSampler(Dna, k, t, N) 20 times and store the best output in a variable called BestMotifs
#M = GibbsSampler(Dna, k, t, N)
#BestMotifs = M
#for i in range(20):
#    if Score(GibbsSampler(Dna, k, t, N)) < Score(BestMotifs):
#        BestMotifs = M
## Print the BestMotifs variable
#print(BestMotifs)
## Print Score(BestMotifs)
#print(Score(BestMotifs))
            






##############################################################################

# Input:  A list of strings Dna, and integers k and t
# Output: RandomMotifs(Dna, k, t)
# HINT:   You might not actually need to use t since t = len(Dna), but you may find it convenient
def RandomMotifs_Quizz():
    # place your code here.
    randomMotifs = []
    
    randomMotifs.append("CCA")
    randomMotifs.append("CCT")
    randomMotifs.append("CTT")
    randomMotifs.append("TTG")
    
    return randomMotifs


# Input:  Positive integers k and t, followed by a list of strings Dna
# Output: RandomizedMotifSearch(Dna, k, t)
def RandomizedMotifSearch_Quizz(Dna, k, t):
    # insert your code here

    M = RandomMotifs_Quizz()
    BestMotifs = M
 
    Profile = ProfileWithPseudocounts(M)
    M = Motifs(Profile, 3, Dna)
    print (M)
        
    print (Score(M))
    print (Score(BestMotifs))
    
    return


import sys

# 3. Assume we are given the following strings Dna:
DNA1 = "AAGCCAAA"
DNA2 = "AATCCTGG"
DNA3 = "GCTACTTG"
DNA4 = "ATGTTTTG"

Dna = [ DNA1, DNA2, DNA3, DNA4 ]


# Then, assume that RandomizedMotifSearch begins by randomly choosing the following 3-mers Motifs of Dna:
"""
CCA
CCT
CTT
TTG
"""

# What are the 3-mers after one iteration of RandomizedMotifSearch? 
# In other words, what are the 3-mers Motifs(Profile(Motifs), Dna)? 
# Please enter your answer as four space-separated strings.


# set t equal to the number of strings in Dna and k equal to 3
k = 3
t = 4
print(RandomizedMotifSearch_Quizz(Dna, k, t))



#Randomized algorithms that are not guaranteed to return exact solutions, but do quickly find approximate solutions, are named after the city of ___.
#Monte Carlo

#Randomized algorithms are exact solutions, but not fast
#Las Vegas

#Randomized algorithms are in between exact solutions, but in between fast
#Atlantic City


#Given the following code in Python:
#import random
#y=random.randint(1,10)
#if y>=1 and y < 3:
#print("A")
#elif y>=3 and y<=7:
#print("B")
#else: print("C")
#What is the probability (represented as a decimal) that "B" will be printed?
#0.5


#Which of the following motif-finding algorithms is guaranteed to find an optimum solution? In other words, which of the following are not heuristics? (Select all that apply.)
#BruteForce



#Given the following "un-normalized" set of probabilities (i.e., that do not necessarily sum to 1):
#0.22 0.54 0.58 0.36 0.3
#What is the normalized set of probabilities? (Enter your answer as a sequence of space-separated numbers.)
#0.11 0.27 0.29 0.18 0.15