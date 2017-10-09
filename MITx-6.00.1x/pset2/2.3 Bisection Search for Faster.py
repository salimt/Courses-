# -*- coding: utf-8 -*-
"""
@author: salimt
"""
balance = 320000; annualInterestRate = 0.2;

monthlyInterestRate = annualInterestRate / 12
monthlyPaymentLowerBound = balance / 12
print('low', monthlyPaymentLowerBound)
monthlyPaymentUpperBound = balance * (1 + monthlyInterestRate) ** 12 / 12
print('high', monthlyPaymentUpperBound)
init_balance = balance
while balance > 0:
    fixedPayment = (monthlyPaymentLowerBound + monthlyPaymentUpperBound) / 2
    #print('fixed', fixedPayment)
    for month in range(12):
        unpaidBalance = balance - fixedPayment
        interestRate = monthlyInterestRate * unpaidBalance
        balance = unpaidBalance + interestRate
        print(balance)
    if balance < 0:
        monthlyPaymentUpperBound = fixedPayment
        balance = init_balance
    else:
        monthlyPaymentLowerBound = fixedPayment
        balance = init_balance
    print('final', round(fixedPayment,2))
    break
