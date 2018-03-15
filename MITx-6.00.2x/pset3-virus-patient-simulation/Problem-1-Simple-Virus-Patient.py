# -*- coding: utf-8 -*-
"""
@author: salimt
"""

# Part B: Problem 1
#  Bookmark this page
# Part B: Problem 1: Implementing a Simple Simulation (No Drug Treatment)
# 15.0/15.0 points (graded)
# We start with a trivial model of the virus population - the patient does not take any drugs and the viruses do not acquire resistance to
# drugs. We simply model the virus population inside a patient as if it were left untreated.

# SimpleVirus class
# To implement this model, you will need to fill in the SimpleVirus class, which maintains the state of a single virus particle. You will
# implement the methods __init__, getMaxBirthProb, getClearProb,doesClear, and reproduce according to the specifications. Use
# random.random() for generating random numbers to ensure that your results are consistent with ours.

# Hint: random seed
# During debugging, you might want to use random.seed(0) so that your results are reproducible.

# The reproduce method in SimpleVirus should produce an offspring by returning a new instance of SimpleVirus with probability:
# self.maxBirthProb * (1 - popDensity). This method raises a NoChildException if the virus particle does not reproduce. For a reminder on
# raising execptions, review the Python docs.

# self.maxBirthProb is the birth rate under optimal conditions (the virus population is negligible relative to the available host cells so
# there is ample nourishment available). popDensity is defined as the ratio of the current virus population to the maximum virus
# population for a patient and should be calculated in the update method of the Patient class.

# Patient class
# You will also need to implement the Patient class, which maintains the state of a virus population associated with a patient.

# The update method in the Patient class is the inner loop of the simulation. It modifies the state of the virus population for a single
# time step and returns the total virus population at the end of the time step. At every time step of the simulation, each virus
# particle has a fixed probability of being cleared (eliminated from the patient's body). If the virus particle is not cleared, it is
# considered for reproduction. If you utilize the population density correctly, you shouldn't need to provide an explicit check that the
# virus population exceeds maxPop when you are calculating how many offspring are added to the population -- you just calculate the new
# population density and use that for the next call to update.

# Unlike the clearance probability, which is constant, the probability of a virus particle reproducing is a function of the virus
# population. With a larger virus population, there are fewer resources in the patient's body to facilitate reproduction, and the
# probability of reproduction will be lower. One way to think of this limitation is to consider that virus particles need to make use of
# a patient's cells to reproduce; they cannot reproduce on their own. As the virus population increases, there will be fewer available
# host cells for viruses to utilize for reproduction.

# To summarize, update should first decide which virus particles are cleared and which survive by making use of the doesClear method of
# each SimpleVirus instance, then update the collection of SimpleVirus instances accordingly. With the surviving SimpleVirus instances,
# update should then call the reproduce method for each virus particle. Based on the population density of the surviving SimpleVirus
# instances, reproduce should either return a new instance of SimpleVirus representing the offspring of the virus particle, or raise a
# NoChildException indicating that the virus particle does not reproduce during the current time step. The update method should update
# the attributes of the patient appropriately under either of these conditions. After iterating through all the virus particles, the
# update method returns the number of virus particles in the patient at the end of the time step.

# Hint: mutating objects
# Be very wary about mutating an object while iterating over its elements. It is best to avoid this entirely (consider introducing
# additional "helper" variables). See the 6.00.2x Style Guide for more information.

# Note that the mapping between time steps and actual time will vary depending on the type of virus being considered, but for this
# problem set, think of a time step as a simulated hour of time.

# About the grader: When defining a Patient class member variable to store the viruses list representing the virus population, please
# use the name self.viruses in order for your code to be compatible with the grader and to pass one of the tests.

# If you want to use numpy arrays, you should import numpy as np and use np.METHOD_NAME in your code.


# Enter your definitions for the SimpleVirus and Patient classes in this box.
#
# PROBLEM 1
#

class NoChildException(Exception):
    """
    NoChildException is raised by the reproduce() method in the SimpleVirus
    and ResistantVirus classes to indicate that a virus particle does not
    reproduce. You can use NoChildException as is, you do not need to
    modify/add any code.
    """

'''
End helper code
'''

#random.seed(5)

#
# PROBLEM 1
#
class SimpleVirus(object):

    """
    Representation of a simple virus (does not model drug effects/resistance).
    """
    def __init__(self, maxBirthProb, clearProb):
        """
        Initialize a SimpleVirus instance, saves all parameters as attributes
        of the instance.        
        maxBirthProb: Maximum reproduction probability (a float between 0-1)        
        clearProb: Maximum clearance probability (a float between 0-1).
        """
        self.maxBirthProb = maxBirthProb
        self.clearProb = clearProb

    def getMaxBirthProb(self):
        """
        Returns the max birth probability.
        """
        return self.maxBirthProb

    def getClearProb(self):
        """
        Returns the clear probability.
        """
        return self.clearProb

    def doesClear(self):
        """ Stochastically determines whether this virus particle is cleared from the
        patient's body at a time step. 
        returns: True with probability self.getClearProb and otherwise returns
        False.
        """
        if random.random() <= self.clearProb:
            return True
        return False
    
    def reproduce(self, popDensity):
        """
        Stochastically determines whether this virus particle reproduces at a
        time step. Called by the update() method in the Patient and
        TreatedPatient classes. The virus particle reproduces with probability
        self.maxBirthProb * (1 - popDensity).
        
        If this virus particle reproduces, then reproduce() creates and returns
        the instance of the offspring SimpleVirus (which has the same
        maxBirthProb and clearProb values as its parent).         

        popDensity: the population density (a float), defined as the current
        virus population divided by the maximum population.         
        
        returns: a new instance of the SimpleVirus class representing the
        offspring of this virus particle. The child should have the same
        maxBirthProb and clearProb values as this virus. Raises a
        NoChildException if this virus particle does not reproduce.         
        """
        if random.random() <= self.maxBirthProb * (1 - popDensity):
            return SimpleVirus(self.maxBirthProb, self.clearProb)
        raise NoChildException

#v1 = SimpleVirus(1.0, 0.0)
#v2 = SimpleVirus(0.0, 0.0)
#v3 = SimpleVirus(0.0, 1.0)
#v4 = SimpleVirus(1.0, 1.0)
#print(v1.doesClear()) #False
#print(v2.doesClear()) #False
#print(v3.doesClear()) #True
#print(v4.doesClear()) #True


class Patient(object):
    """
    Representation of a simplified patient. The patient does not take any drugs
    and his/her virus populations have no drug resistance.
    """    

    def __init__(self, viruses, maxPop):
        """
        Initialization function, saves the viruses and maxPop parameters as
        attributes.

        viruses: the list representing the virus population (a list of
        SimpleVirus instances)

        maxPop: the maximum virus population for this patient (an integer)
        """
        self.viruses = viruses
        self.maxPop = maxPop
        self.totalVirPop = len(viruses)

    def getViruses(self):
        """
        Returns the viruses in this Patient.
        """
        return self.viruses


    def getMaxPop(self):
        """
        Returns the max population.
        """
        return self.maxPop


    def getTotalPop(self):
        """
        Gets the size of the current total virus population. 
        returns: The total virus population (an integer)
        """
        
        return self.totalVirPop


    def update(self):
        """
        Update the state of the virus population in this patient for a single
        time step. update() should execute the following steps in this order:
        
        - Determine whether each virus particle survives and updates the list
        of virus particles accordingly.   
        
        - The current population density is calculated. This population density
          value is used until the next call to update() 
        
        - Based on this value of population density, determine whether each 
          virus particle should reproduce and add offspring virus particles to 
          the list of viruses in this patient.                    

        returns: The total virus population at the end of the update (an
        integer)
        """
        stayingViruses = []
        
        for i in range(len(self.viruses)):
            if not self.viruses[i].doesClear():
                stayingViruses.append(self.viruses[i])
            
        self.viruses = stayingViruses
        
        if len(stayingViruses) == 0:
            self.totalVirPop = 0
        self.totalVirPop += len(self.viruses)
        
        popDensity = len(self.viruses) / self.getMaxPop()
        
        for j in range(len(self.viruses)):           
            try: 
                self.viruses[j].reproduce(popDensity)
            except NoChildException: 
                return NoChildException()
            
        return len(self.viruses)