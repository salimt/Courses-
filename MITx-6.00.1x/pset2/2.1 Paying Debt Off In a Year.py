# -*- coding: utf-8 -*-
"""
@author: salimt
"""
balance = 42; annualInterestRate = 0.2; monthlyPaymentRate = 0.04

for month in range(12):
    minimumPayment = balance * monthlyPaymentRate
    unpaidBalance = balance - minimumPayment
    interestRate = annualInterestRate/12 * unpaidBalance
    balance = unpaidBalance + interestRate
print('Remaining balance:', round(balance,2))