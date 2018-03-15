# Problem Set 3: Simulating the Spread of Disease and Virus Population Dynamics 

import random
import pylab
#from ps3b_precompiled_36 import *

''' 
Begin helper code
'''

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

#virus = SimpleVirus(1.0, 0.65)
#patient = Patient([virus], 100)
#print(patient.getTotalPop())
#for i in range(100):
#    patient.update()
#print(patient.getTotalPop())
#print(patient.update())
#print(patient.update(), patient.getTotalPop())
#viruses = [SimpleVirus(0.53, 0.63), SimpleVirus(0.54, 0.12)]
#P1 = Patient(viruses, 6)
#print(P1.getTotalPop())


#
# PROBLEM 2
#
def simulationWithoutDrug(numViruses, maxPop, maxBirthProb, clearProb,
                          numTrials):
    """
    Run the simulation and plot the graph for problem 3 (no drugs are used,
    viruses do not have any drug resistance).    
    For each of numTrials trial, instantiates a patient, runs a simulation
    for 300 timesteps, and plots the average virus population size as a
    function of time.

    numViruses: number of SimpleVirus to create for patient (an integer)
    maxPop: maximum virus population for patient (an integer)
    maxBirthProb: Maximum reproduction probability (a float between 0-1)        
    clearProb: Maximum clearance probability (a float between 0-1)
    numTrials: number of simulation runs to execute (an integer)   
    """
    virus = [SimpleVirus(maxBirthProb, clearProb) for virus in range(numViruses)]
    patient = Patient(virus, maxPop)
    listo = []
    for trial in range(numTrials):       
        for i in range(300):
            listo.append(patient.update()/numTrials)

    pylab.plot(listo, label = "SimpleVirus")
    pylab.title("SimpleVirus simulation")
    pylab.xlabel("Time Steps")
    pylab.ylabel("Average Virus Population")
    pylab.legend(loc = "best")
    pylab.show()

#print(simulationWithoutDrug(1, 10, 1.0, 0.0, 1))
#print(simulationWithoutDrug(1, 90, 0.8, 0.1, 1))
#print(simulationWithoutDrug(100, 200, 0.2, 0.8, 1))


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
        self.maxBirthProb = maxBirthProb
        self.clearProb = clearProb                
        self.resistances = resistances
        self.mutProb = mutProb
        SimpleVirus.__init__(self, maxBirthProb, clearProb)


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
            
        if len(counter) == len(activeDrugs):
            if random.random() <= self.maxBirthProb * (1 - popDensity) or random.random() <= self.mutProb:
                if self.mutProb*len(self.resistances) == len(self.resistances)//2 and (len(self.resistances) > 1):
                    return ResistantVirus(self.maxBirthProb, self.clearProb, \
                           {key:self.resistances[key] for i, key in enumerate(self.resistances) if i % 2 == 0}, self.mutProb) 
                if random.random() <= self.mutProb:
                    self.resistances.update((k, False) if self.resistances[k] == True else (k, True) for k, v in self.resistances.items())  
#                for drug, resistance in self.resistances.items():
#                    if random.random() <= self.mutProb:
#                        if self.resistances[drug] == False:
#                            self.resistances[drug] = True
#                        else:
#                            self.resistances[drug] = False
                return ResistantVirus(self.maxBirthProb, self.clearProb, self.resistances, self.mutProb)
            else:
                raise NoChildException
        else:
            raise NoChildException
    
    
class ResistantVirus(SimpleVirus):  
 
    def __init__(self, maxBirthProb, clearProb, resistances, mutProb):
        SimpleVirus.__init__(self, maxBirthProb, clearProb)
        self.mutProb = mutProb
        self.resistances = resistances
 
    def isResistantTo(self, drug):
        return self.resistances.get(drug, False)
 
    def reproduce(self, popDensity, activeDrugs):
        if (all(self.isResistantTo(d) for d in activeDrugs) and
            random.random() <= self.maxBirthProb * (1 - popDensity)):
            resistances = {k:v if random.random() > self.mutProb else not v
                           for k, v in self.resistances.items()}
            return ResistantVirus(self.maxBirthProb, self.clearProb, 
                                  resistances, self.mutProb)
        raise NoChildException
        
        
# Problem 4: b) TreatedPatient
class TreatedPatient(Patient):
    
    def __init__(self, viruses, maxPop):
        Patient.__init__(self, viruses, maxPop)
        self.drugs =[]
 
    def addPrescription(self, newDrug):
        if newDrug not in self.drugs:
            self.drugs.append(newDrug)
 
    def getPrescriptions(self):
        return self.drugs
 
    def getResistPop(self, drugResist):
        return len([v for v in self.viruses if all(v.isResistantTo(d) 
                                                   for d in drugResist)])
 
    def update(self):
        self.viruses = [v for v in self.viruses if not v.doesClear()]
        popDensity = len(self.viruses) / float(self.maxPop)
        for v in self.viruses[:]:
            try:
                self.viruses.append(v.reproduce(popDensity,
                                                self.getPrescriptions()))
            except NoChildException:
                pass
        return len(self.viruses)
    
    
    
    
    
def simulationWithDrug(numViruses, maxPop, maxBirthProb, clearProb, resistances,
                       mutProb, numTrials):
    import numpy as np
    
    data_without_drug = np.zeros(300)
    data_with_drug = np.zeros(300)
    for trial in range(numTrials):        
        virus = [ResistantVirus(maxBirthProb, clearProb, resistances, mutProb) for virus in range(numViruses)]
        patient = TreatedPatient(virus, maxPop)
        virusNum, resistantVirNum = [], []
        for i in range(150):
            virusNum.append(patient.update())
            resistantVirNum.append(patient.getResistPop(['guttagonol']))
            
        patient.addPrescription('guttagonol')
        for i in range(150):
            virusNum.append(patient.update())
            resistantVirNum.append(patient.getResistPop(['guttagonol']))
        data_without_drug = data_without_drug + virusNum
        data_with_drug = data_with_drug + resistantVirNum
    dataWithout_avg = data_without_drug / numTrials
    dataWith_avg = data_with_drug/ numTrials
#    print([float("{:0.1f}".format(x)) for x in dataWithout_avg])

    pylab.plot([float("{:0.1f}".format(x)) for x in dataWithout_avg], label = "ResistantVirus without Drug")
    pylab.plot([float("{:0.1f}".format(x)) for x in dataWith_avg], label = "ResistantVirus with Drug")
    pylab.title("ResistantVirus simulation")
    pylab.xlabel("Time Steps")
    pylab.ylabel("Average Virus Population")
    pylab.legend(loc = "best")
    pylab.show()
    
    
print(simulationWithDrug(75, 100, .8, 0.1, {"guttagonol": True}, 0.8, 1))