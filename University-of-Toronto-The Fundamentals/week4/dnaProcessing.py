def get_length(dna):
    """ (str) -> int

    Return the length of the DNA sequence dna.

    >>> get_length('ATCGAT')
    6
    >>> get_length('ATCG')
    4
    """
    return len(dna)
#print(get_length('SALIM'))

def is_longer(dna1, dna2):
    """ (str, str) -> bool

    Return True if and only if DNA sequence dna1 is longer than DNA sequence
    dna2.

    >>> is_longer('ATCG', 'AT')
    True
    >>> is_longer('ATCG', 'ATCGGA')
    False
    """
    return len(dna1) > len(dna2)
#print(is_longer('ATCG', 'ATCGGA'), is_longer('ATCG', 'AT'))


def count_nucleotides(dna, nucleotide):
    """ (str, str) -> int

    Return the number of occurrences of nucleotide in the DNA sequence dna.

    >>> count_nucleotides('ATCGGC', 'G')
    2
    >>> count_nucleotides('ATCTA', 'G')
    0
    """
    return dna.count(nucleotide)
#print(count_nucleotides('ATCTA', 'G'), count_nucleotides('ATCGGC', 'G'))


def contains_sequence(dna1, dna2):
    """ (str, str) -> bool

    Return True if and only if DNA sequence dna2 occurs in the DNA sequence
    dna1.

    >>> contains_sequence('ATCGGC', 'GG')
    True
    >>> contains_sequence('ATCGGC', 'GT')
    False

    """
    return dna2 in dna1
#print(contains_sequence('ATCGGC', 'GT'), contains_sequence('ATCGGC', 'GG'))

def is_valid_sequence(s):
    """ (str) -> bool
    
    The parameter is a potential DNA sequence. Return True if and 
    only if the DNA sequence is valid (that is, it contains no 
    characters other than 'A', 'T', 'C' and 'G'). 
    
    >>> is_valid_sequence("ATTCCGGGA")
    True
    >>> is_valid_sequence("SALIMAAAA")
    False
    """
    nucs = "ATCG"
    counterL = []
    total = 0
    for i in s:
        if i in nucs and not i in counterL:
            total += 1
            counterL += i
        elif i not in nucs: return False
    return (total >= 4) and (nucs == "ATCG")
#print(is_valid_sequence("ATTCCGGGA"), is_valid_sequence("SALIMAAAA"))

def insert_sequence(s1, s2, s3):
    """ (str, str, int) -> str
    
    The first two parameters are DNA sequences and the 
    third parameter is an index. Return the DNA sequence obtained
    by inserting the second DNA sequence into the first DNA sequence
    at the given index. (You can assume that the index is valid.)
    
    >>> insert_sequence("CCGG", "AT", 2)
    CCATGG
    >>> insert_sequence("SALIM", "AL", 3)
    SALALIM
    """
    return s1[:s3] + s2 + s1[s3:]
#print(insert_sequence("CCGG", "AT", 2), insert_sequence("SALIM", "AL", 3))

def get_complement(s):
    """ (str) -> str
    
    The first parameter is a nucleotide ('A', 'T', 'C' or 'G'). 
    Return the nucleotide's complement. We have intentionally not 
    given you any examples for this function. The Problem Domain section 
    explains what a nucleotide is and what a complement is.
    
    """
    output = ''
    for i in s:
        if i == 'A': output += 'T'           
        if i == 'T': output += 'A'            
        if i == 'G': output += 'C'           
        if i == 'C': output += 'G'          
    return output
#print("".join(get_complement("AATCCGG")))

def get_complementary_sequence(s):
    """ (str) -> str
    
    The parameter is a DNA sequence. Return the DNA sequence that
    is complementary to the given DNA sequence. For example, if you 
    call this function with 'AT' as the argument, it should return 'TA'.
    
    >>> get_complementary_sequence('ATCGGACT')
    TAGCCTGA
    >>> get_complementary_sequence('GCACTCC')
    CGTGAGG
    """
    complementary_seq = [get_complement(i) for i in s]
    return "".join(complementary_seq)
print(get_complementary_sequence('GCACTCC'))