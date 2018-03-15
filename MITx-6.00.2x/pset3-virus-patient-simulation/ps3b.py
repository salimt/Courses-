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
                
                
        
#virus = ResistantVirus(1.0, 1.0, {}, 0.0)
#patient = TreatedPatient([virus], 100)
#for i in range(100):
#    patient.update()
#print(patient.getTotalPop())


#virus1 = ResistantVirus(1.0, 0.0, {"drug1": True}, 0.0)
#virus2 = ResistantVirus(1.0, 0.0, {"drug1": False}, 0.0)
#patient = TreatedPatient([virus1, virus2], 1000000)
#patient.addPrescription("drug1")
##print(patient.update())
#n  = 0
#for i in range(5):
#    if patient.update():
#        n += 1
#print(n)
    
#
# PROBLEM 4
#
def simulationWithDrug(numViruses, maxPop, maxBirthProb, clearProb, resistances,
                       mutProb, numTrials):
    """
    Runs simulations and plots graphs for problem 5.

    For each of numTrials trials, instantiates a patient, runs a simulation for
    150 timesteps, adds guttagonol, and runs the simulation for an additional
    150 timesteps.  At the end plots the average virus population size
    (for both the total virus population and the guttagonol-resistant virus
    population) as a function of time.

    numViruses: number of ResistantVirus to create for patient (an integer)
    maxPop: maximum virus population for patient (an integer)
    maxBirthProb: Maximum reproduction probability (a float between 0-1)        
    clearProb: maximum clearance probability (a float between 0-1)
    resistances: a dictionary of drugs that each ResistantVirus is resistant to
                 (e.g., {'guttagonol': False})
    mutProb: mutation probability for each ResistantVirus particle
             (a float between 0-1). 
    numTrials: number of simulation runs to execute (an integer)
    
    """
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