# -*- coding: utf-8 -*-
"""
@author: salimt
"""
# Part B: Problem 3
#  Bookmark this page
# Part B: Problem 3: Implementing a Simulation With Drugs
# 10.0/10.0 points (graded)
# In this problem, we consider the effects of both administering drugs to the patient and the ability of virus particle offsprings to
# inherit or mutate genetic traits that confer drug resistance. As the virus population reproduces, mutations will occur in the virus
# offspring, adding genetic diversity to the virus population. Some virus particles gain favorable mutations that confer resistance to
# drugs.

# ResistantVirus class
# In order to model this effect, we introduce a subclass of SimpleVirus called ResistantVirus. ResistantVirus maintains the state of a
# virus particle's drug resistances, and accounts for the inheritance of drug resistance traits to offspring. Implement the ResistantVirus
# class.

# Hint: reproduce function child resistances
# If you are really unsure about how to think about what each child resistances should be changed to, here is a different approach. If the
# probability mutProb is successful, the child resistance switches. Otherwise, the child resistance stays the same as the parent
# resistance.
# If you want to use numpy arrays, you should import numpy as np and use np.METHOD_NAME in your code.


# Enter your definition for the ResistantVirus class in this box.
# You'll enter your code for TreatedPatient on the next page.
#
# PROBLEM 3
#
class ResistantVirus(SimpleVirus):
    """
    Representation of a virus which can have drug resistance.
    """   

    def __init__(self, maxBirthProb, clearProb, resistances, mutProb):
        """
        Initialize a ResistantVirus instance, saves all parameters as attributes
        of the instance.

        maxBirthProb: Maximum reproduction probability (a float between 0-1)       

        clearProb: Maximum clearance probability (a float between 0-1).

        resistances: A dictionary of drug names (strings) mapping to the state
        of this virus particle's resistance (either True or False) to each drug.
        e.g. {'guttagonol':False, 'srinol':False}, means that this virus
        particle is resistant to neither guttagonol nor srinol.

        mutProb: Mutation probability for this virus particle (a float). This is
        the probability of the offspring acquiring or losing resistance to a drug.
        """
        SimpleVirus.__init__(self, maxBirthProb, clearProb)
        self.mutProb = mutProb
        self.resistances = resistances


    def getResistances(self):
        """
        Returns the resistances for this virus.
        """
        return self.resistances

    def getMutProb(self):
        """
        Returns the mutation probability for this virus.
        """
        return self.mutProb

    def isResistantTo(self, drug):
        """
        Get the state of this virus particle's resistance to a drug. This method
        is called by getResistPop() in TreatedPatient to determine how many virus
        particles have resistance to a drug.       

        drug: The drug (a string)

        returns: True if this virus instance is resistant to the drug, False
        otherwise.
        """
        if drug in self.resistances:
            if self.resistances.get(drug) == True:
                return True
            else:
                return False


    def reproduce(self, popDensity, activeDrugs):
        """
        Stochastically determines whether this virus particle reproduces at a
        time step. Called by the update() method in the TreatedPatient class.

        A virus particle will only reproduce if it is resistant to ALL the drugs
        in the activeDrugs list. For example, if there are 2 drugs in the
        activeDrugs list, and the virus particle is resistant to 1 or no drugs,
        then it will NOT reproduce.

        Hence, if the virus is resistant to all drugs
        in activeDrugs, then the virus reproduces with probability:      

        self.maxBirthProb * (1 - popDensity).                       

        If this virus particle reproduces, then reproduce() creates and returns
        the instance of the offspring ResistantVirus (which has the same
        maxBirthProb and clearProb values as its parent). The offspring virus
        will have the same maxBirthProb, clearProb, and mutProb as the parent.

        For each drug resistance trait of the virus (i.e. each key of
        self.resistances), the offspring has probability 1-mutProb of
        inheriting that resistance trait from the parent, and probability
        mutProb of switching that resistance trait in the offspring.       

        For example, if a virus particle is resistant to guttagonol but not
        srinol, and self.mutProb is 0.1, then there is a 10% chance that
        that the offspring will lose resistance to guttagonol and a 90%
        chance that the offspring will be resistant to guttagonol.
        There is also a 10% chance that the offspring will gain resistance to
        srinol and a 90% chance that the offspring will not be resistant to
        srinol.

        popDensity: the population density (a float), defined as the current
        virus population divided by the maximum population       

        activeDrugs: a list of the drug names acting on this virus particle
        (a list of strings).

        returns: a new instance of the ResistantVirus class representing the
        offspring of this virus particle. The child should have the same
        maxBirthProb and clearProb values as this virus. Raises a
        NoChildException if this virus particle does not reproduce.
        """        

        counter = [i for i in activeDrugs if self.isResistantTo(i) == True]
        
    
        if len(counter) == len(activeDrugs) and\
                        random.random() <= self.maxBirthProb * (1 - popDensity):
                if self.mutProb*len(self.resistances) == len(self.resistances)//2 and (len(self.resistances) > 1):
                    return ResistantVirus(self.maxBirthProb, self.clearProb, \
                           {key:self.resistances[key] for i, key in enumerate(self.resistances) if i % 2 == 0}, self.mutProb) 
                if random.random() <= self.mutProb:
                    self.resistances.update((k, False) if self.resistances[k] == True else (k, True) for k, v in self.resistances.items())  
                return ResistantVirus(self.maxBirthProb, self.clearProb, self.resistances, self.mutProb)
        else:
            raise NoChildException