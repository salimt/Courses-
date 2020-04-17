# -*- coding: utf-8 -*-
"""
@author: salimt
"""

# Input:  A set of kmers Motifs
# Output: Count(Motifs)
def Count(Motifs):
    count = {} # initializing the count dictionary
    for motif in Motifs:
        for i in range(len(motif)):
            motifChar = motif[i]
            if motifChar not in count:
                count[motifChar] = [0] * len(motif)
                count[motifChar][i] = count.get(motifChar)[i] + 1
            else:
                count[motifChar][i] = count.get(motifChar)[i] + 1  
    return count

def Profile(Motifs):
    t = len(Motifs)
    motifCounts = Count(Motifs)
    profile = {i: [0]*len(Motifs[0]) for i in "ATCG"}

    for key,val in motifCounts.items():
        profile[key] = [i/t for i in val]

    return profile


#print (Profile(["ACGTTA",
#                 "AGGTGA",
#                 "ACGTTT",
#                 "ATGCTA",
#                 "TCGCGA",
#                 "AAGCTA"]))
#print (Profile(["GTC","CCC","ATA","GCT"]))

def Consensus(Motifs):
    finalMotif = []
    motifCounts = Count(Motifs)
    for i in range(len(Motifs[0])):
        maxVal = 0
        motif = ""
        for k,v in motifCounts.items():
            if v[i] > maxVal:
                maxVal = v[i]
                motif = k
        finalMotif.append(motif)
        
    return "".join(finalMotif)
#print(Consensus(["ACGTTA"]))

#Profile(["AACGTA","CCCGTT"])


def Score(Motifs):
    
    consensus = Consensus(Motifs)
    motifCounts = Count(Motifs)
    score = [len(Motifs)-motifCounts.get(consensus[i])[i] for i in range(len(Motifs[0]))]
    
    return sum(score)   
#print (Consensus(["ACGTTA","AAGAGA","AGGTGA","AGGTCA","ACGCGA","ATGCTA"]))

# Input:  String Text and profile matrix Profile
# Output: Pr(Text, Profile)
def Pr(Text, Profile):
    score = [Profile.get(Text[i])[i] for i in range(len(Text))]
    product = 1
    for x in score:
        product *= x
    return product

Profile1 = { 'A': [0.4,0.3,0.0,0.1,0.0,0.9], 'C': [0.2,0.3,0.0,0.4,0.0,0.1],'G': [0.1,0.3,1.0,0.1,0.5,0.0],'T': [0.3,0.1,0.0,0.4,0.5,0.0] }
#Text = ["ACGTTA","AGGTGA","ACGTTT","ATGCTA","TCGCGA","AAGCTA"]
Text = "CAGTGA"
print(Pr(Text,Profile1))

def ProfileMostProbableKmer(text, k, profile):
    maxProb = Pr(text[0:k], profile)
    maxMotif = text[0:0+k]
    for i in range(1, len(text)-k+1):
        tempProb = Pr(text[i:i+k], profile)
        if tempProb > maxProb:
            maxProb = tempProb
            maxMotif = text[i:i+k]
    return maxMotif
# Input:  A list of kmers Dna, and integers k and t (where t is the number of kmers in Dna)
# Output: GreedyMotifSearch(Dna, k, t)


def GreedyMotifSearch(Dna, k, t):
   
    BestMotifs = [Dna[i][0:k] for i in range(0, t)]
    for i in range(len(Dna[0])-k+1):
        Motifs = []
        Motifs.append(Dna[0][i:i+k])
        for j in range(1, t):
            P = Profile(Motifs[0:j])
            Motifs.append(ProfileMostProbableKmer(Dna[j], k, P))
        if Score(Motifs) < Score(BestMotifs):
            BestMotifs = Motifs
    return BestMotifs

## Copy the ten strings occurring in the hyperlinked DosR dataset below.
#Dna = [
#"GCGCCCCGCCCGGACAGCCATGCGCTAACCCTGGCTTCGATGGCGCCGGCTCAGTTAGGGCCGGAAGTCCCCAATGTGGCAGACCTTTCGCCCCTGGCGGACGAATGACCCCAGTGGCCGGGACTTCAGGCCCTATCGGAGGGCTCCGGCGCGGTGGTCGGATTTGTCTGTGGAGGTTACACCCCAATCGCAAGGATGCATTATGACCAGCGAGCTGAGCCTGGTCGCCACTGGAAAGGGGAGCAACATC",
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
## set t equal to the number of strings in Dna and k equal to 15
#t = len(Dna)
#k = 15
#
## Call GreedyMotifSearch(Dna, k, t) and store the output in a variable called Motifs
#Motifs = GreedyMotifSearch(Dna,k,t)
#
## Print the Motifs variable
#print(Motifs)
## Print Score(Motifs)
#print(Score(Motifs))
