# -*- coding: utf-8 -*-
"""
@author: salimt
"""
# Part B: Problem 4
#  Bookmark this page
# Part B: Problem 4: TreatedPatient Class
# 10.0/10.0 points (graded)
# We also need a representation for a patient that accounts for the use of drug treatments and manages a collection of ResistantVirus
# instances. For this, we introduce the TreatedPatient class, which is a subclass of Patient. TreatedPatient must make use of the new
# methods in ResistantVirus and maintain the list of drugs that are administered to the patient.

# Drugs are given to the patient using the TreatedPatient class's addPrescription() method. What happens when a drug is introduced? The
# drugs we consider do not directly kill virus particles lacking resistance to the drug, but prevent those virus particles from
# reproducing (much like actual drugs used to treat HIV). Virus particles with resistance to the drug continue to reproduce normally.
# Implement the TreatedPatient class.

# Hint: reproduce function child resistances
# If you are really unsure about how to think about what each child resistances should be changed to, here is a different approach. If
# the probability mutProb is successful, the child resistance switches. Otherwise, the child resistance stays the same as the parent
# resistance.

# If you want to use numpy arrays, you should import numpy as np and use np.METHOD_NAME in your code.


# Enter your definitions for the ResistantVirus and TreatedPatient classes in this box.

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


  
#virus = ResistantVirus(1.0, 0.0, {"drug2": True}, 1)
#virus = ResistantVirus(1.0, 0.0, {'drug1':True, 'drug2': True, 'drug3': True, 'drug4': True, 'drug5': True, 'drug6': True}, 0.5)
#n = 0
#for i in range(10):
#    print(len(virus.resistances), virus.resistances)
#    if virus.reproduce(0,{}).getResistances()['drug2']:
#        n+=1
#print(n)
#virus = ResistantVirus(1.0, 0.0, {"drug1":True, "drug2":False}, 0.0)
#print(virus.reproduce(0, ["drug1"]), "drug1") #produce
#print(virus.reproduce(0, ["drug2"]), "drug2") #nochildexception
#v1 = ResistantVirus(1.0, 0.0, {"drug2": True}, 1.0)
#print(v1.reproduce(0, ['drug2']))

                      
#virus = ResistantVirus(1.0, 0.0, {'drug1':True, 'drug2': True, 'drug3': True, 'drug4': True, 'drug5': True, 'drug6': True}, 0.5)
#for i in range(10):
#    virus.reproduce(0, [])
#    print(virus.resistances)
#print(virus.isResistantTo('drug1'))
#print(virus.reproduce(0, []))
           

class TreatedPatient(Patient):
    """
    Representation of a patient. The patient is able to take drugs and his/her
    virus population can acquire resistance to the drugs he/she takes.
    """

    def __init__(self, viruses, maxPop):
        """
        Initialization function, saves the viruses and maxPop parameters as
        attributes. Also initializes the list of drugs being administered
        (which should initially include no drugs).              

        viruses: The list representing the virus population (a list of
        virus instances)

        maxPop: The  maximum virus population for this patient (an integer)
        """
        self.PrescriptionedDrugs = []
        Patient.__init__(self, viruses, maxPop)


    def addPrescription(self, newDrug):
        """
        Administer a drug to this patient. After a prescription is added, the
        drug acts on the virus population for all subsequent time steps. If the
        newDrug is already prescribed to this patient, the method has no effect.

        newDrug: The name of the drug to administer to the patient (a string).

        postcondition: The list of drugs being administered to a patient is updated
        """
        
        if newDrug not in self.PrescriptionedDrugs:
            self.PrescriptionedDrugs.append(newDrug)
        else:
            pass


    def getPrescriptions(self):
        """
        Returns the drugs that are being administered to this patient.

        returns: The list of drug names (strings) being administered to this
        patient.
        """

        return self.PrescriptionedDrugs


    def getResistPop(self, drugResist):
        """
        Get the population of virus particles resistant to the drugs listed in
        drugResist.       

        drugResist: Which drug resistances to include in the population (a list
        of strings - e.g. ['guttagonol'] or ['guttagonol', 'srinol'])

        returns: The population of viruses (an integer) with resistances to all
        drugs in the drugResist list.
        """
        resistantViruses = 0
        for virus in self.viruses:
            if all(virus.isResistantTo(drug) for drug in drugResist):
                resistantViruses += 1
        return resistantViruses


    def update(self):
        """
        Update the state of the virus population in this patient for a single
        time step. update() should execute these actions in order:

        - Determine whether each virus particle survives and update the list of
          virus particles accordingly

        - The current population density is calculated. This population density
          value is used until the next call to update().

        - Based on this value of population density, determine whether each 
          virus particle should reproduce and add offspring virus particles to 
          the list of viruses in this patient.
          The list of drugs being administered should be accounted for in the
          determination of whether each virus particle reproduces.

        returns: The total virus population at the end of the update (an
        integer)
        """

        for virus in self.viruses:
            if virus.doesClear():
                self.viruses.remove(virus)

        popDensity = len(self.viruses) / self.getMaxPop()
        
        stayingViruses = self.viruses.copy()
        
        for virus in stayingViruses:
#            if all(virus.isResistantTo(drug) for drug in virus.resistances.keys()):
            try: 
                virus.reproduce(popDensity, virus.resistances)
                self.viruses.append(virus)
            except NoChildException: 
                continue

        return len(self.viruses)