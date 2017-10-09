# -*- coding: utf-8 -*-
"""
@author: salimt
"""
balance = 3329; annualInterestRate = 0.2;

monthlyFixedPayment = 0
previous = balance
monthlyInterestRate = annualInterestRate/12
while balance > 0:
    for month in range(12):
        unpaidBalance = balance - monthlyFixedPayment
        interestRate = monthlyInterestRate * unpaidBalance
        balance = unpaidBalance + interestRate
    if balance > 0:
        balance = previous
        monthlyFixedPayment += 10
    else:
        print(monthlyFixedPayment)
#print('Remaining balance:', round(balance,2))