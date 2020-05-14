#!/usr/bin/env python3
import sys
import subprocess

with open(sys.argv[1], 'r', encoding='utf-8') as infile:
	for line in infile:
		new_name = line.replace("jane", "jdoe")
		subprocess.Popen("mv " + line[6:-1] + " " + new_name[6:-1], shell=True, cwd='/home/{{ username here }}')
infile.close()