#!/usr/bin/env python3
import subprocess
import collections
import re
import operator
import csv

def error_csv():
	errors = {}
	with open("syslog.log", "r") as file:
		for line in file.readlines():

			val = re.search(r"ERROR ([\w ']*)", line)
			if val:
				val = val.group(1)[:-1]
			if val == None:
				continue
			if val not in errors:
				errors[val] = 1
			else:
				errors[val] = errors.get(val)+1
		sorted_errors = collections.orderedDict(sorted(errors.items(), key=operator.itemgetter(1), reverse=True))
		with open("error_message.csv", "w") as f:
			writer = csv.DictWriter(f, fieldnames=["Error", "Count"])
			writer.writeheader()
			[f.write("{0}, {1}\n".format(key,value)) for key,value in sorted_errors.items()]
		return sorted_errors

print(error_csv)

def user_csv():
	userData = {}
	with open("syslog.log", "r") as file:
		for line in file.readlines():
			val=line[line.find("(")+1:line.find(")")]
			if val not in userdata:
				userData[val] = [0]*2
			if line.find("ERROR"):
				errVal = userData.get(val)[1]+1
			if line.find("INFO"):
				infVal = userData.get(Val[0])+1
			userdata[val] = [infVal, errVal]
		sorted_userData = collections.orderedDict(sorted(userData.items(), key=operator.itemgetter(0)))
		with open("user_statistics.csv", "w") as f:
			[f.write("{0}, {1}\n".format(key,value)) for key,value in sorted_userData.items()]
		return sorted_userData

print(user_csv())