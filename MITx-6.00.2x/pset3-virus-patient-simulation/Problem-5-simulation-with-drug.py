# -*- coding: utf-8 -*-
"""
@author: salimt
"""
# Part B: Problem 5
#  Bookmark this page
# Part B: Problem 5: Running and Analyzing a Simulation With a Drug
# 10.0/10.0 points (graded)
# In this problem, we will use the implementation you filled in for Problem 4 to run a simulation. You will create a TreatedPatient
# instance with the following parameters, then run the simulation:
# viruses, a list of 100 ResistantVirus instances
# maxPop, maximum sustainable virus population = 1000

# Each ResistantVirus instance in the viruses list should be initialized with the following parameters:
# maxBirthProb, maximum reproduction probability for a virus particle = 0.1
# clearProb, maximum clearance probability for a virus particle = 0.05
# resistances, The virus's genetic resistance to drugs in the experiment = {'guttagonol': False}
# mutProb, probability of a mutation in a virus particle's offspring = 0.005

# Run a simulation that consists of 150 time steps, followed by the addition of the drug, guttagonol, followed by another 150 time steps.
# You should make use of the function simulationWithDrug(numViruses, maxPop, maxBirthProb, clearProb, resistances, mutProb, numTrials).
# As with problem 3, perform up to 100 trials and make sure that your results are repeatable and representative.

# Create one plot that records both the average total virus population and the average population of guttagonol-resistant virus particles
# over time.

# A few good questions to consider as you look at your plots are: What trends do you observe? Are the trends consistent with your
# intuition? Feel free to discuss the answers to these questions in the forum, to fully cement your understanding of this problem set,
# processing and interpreting data.

# Again, as in Problem 2, you can use the provided .pyc file to check that your implementation of the TreatedPatient and ResistantVirus
# classes work as expected.

# If you want to use numpy arrays, you should import numpy as np and use np.METHOD_NAME in your code.
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


#print(simulationWithDrug(75, 100, .8, 0.1, {"guttagonol": True}, 0.8, 1))