#!/usr/bin/python
# -*- coding: utf-8 -*-

from __future__ import print_function

import json
import time
import os.path
from subprocess import Popen, PIPE
from collections import namedtuple


# Python 2/3 compatibility
# Python 2:
try:
    from urlparse import urlparse
    from urllib import urlencode
    from urllib2 import urlopen, Request, HTTPError
except:
    pass

# Python 3:
try:
    from urllib.parse import urlparse, urlencode
    from urllib.request import urlopen, Request
    from urllib.error import HTTPError
except:
    pass

import sys
# Python 2:
if sys.version_info < (3, 0):
    def input(str):
        return raw_input(str)


# Start of core script
process_id = os.getpid()

version = '1.0.0'
submitt_url = \
    'https://www.coursera.org/api/onDemandProgrammingScriptSubmissions.v1'

minizinc_cmd = 'mzn-gecode'

Metadata = namedtuple('Metadata', ['assignment_key', 'name', 'problem_data', 'model_data'])

Problem = namedtuple('Problem', ['sid', 'model_file', 'input_file', 'runtime', 'name'])

Model = namedtuple('Model', ['sid', 'model_file', 'name'])

mzn_solution = '----------'


def load_metadata(metadata_file_name='_coursera'):
    '''
    Parses an assignment metadata file

    Args:
        metadata_file_name (str): location of the metadata file

    Returns:
        metadata as a named tuple structure
    '''

    if not os.path.exists(metadata_file_name):
        print('metadata file "%s" not found' % metadata_file_name)
        quit()

    try:
        with open(metadata_file_name, 'r') as metadata_file:
            key = metadata_file.readline().strip()
            name = metadata_file.readline().strip()
            problem_count = int(metadata_file.readline().strip())
            problem_data = []
            for i in range(0, problem_count):
                line = metadata_file.readline().strip()
                line_parts = line.split(',')
                line_parts = [x.strip() for x in line_parts]
                assert(len(line_parts) == 5)
                line_parts[3] = int(line_parts[3])
                problem_data.append(Problem(*line_parts))
            model_count = int(metadata_file.readline().strip())
            model_data = []
            for i in range(0, model_count):
                line = metadata_file.readline().strip()
                line_parts = line.split(',')
                line_parts = [x.strip() for x in line_parts]
                assert(len(line_parts) == 3)
                model_data.append(Model(*line_parts))
            metadata_file.close()
    except Exception as e:
        print('problem parsing assignment metadata file')
        print('exception message:')
        print(e)
        quit()
    return Metadata(key, name, problem_data, model_data)


def part_prompt(problems, models):
    '''
    Prompts the user for which parts of the assignment they would like to 
    submit.

    Args:
        problems:  a list of assignment problems
        models:  a list of assignment models

    Returns:
        the selected subsets of problems and models
    '''

    count = 1
    print('Hello! These are the assignment parts that you can submit:')
    for i, problem in enumerate(problems):
        print(str(count) + ') ' + problem.name)
        count += 1
    for i, model in enumerate(models):
        print(str(count) + ') ' + model.name)
        count += 1
    print('0) All')

    part_text = input('Please enter which part(s) you want to submit (0-%d): ' % count)
    selected_problems = []
    selected_models = []

    for item in part_text.split(','):
        try:
            i = int(item)
        except:
            print('Skipping input "' + item + '".  It is not an integer.')
            continue

        if i >= count or i < 0:
            print('Skipping input "' + item + '".  It is out of the valid range (0-%d).' % count)
            continue

        if i == 0:
            selected_problems.extend(problems)
            selected_models.extend(models)
            continue

        if i <= len(problems):
            selected_problems.append(problems[i-1])
        else:
            selected_models.append(models[i-1-len(problems)])
            

    if len(selected_problems) <= 0 and len(selected_models) <= 0:
        print('No valid assignment parts identified.  Please try again. \n')
        return part_prompt(problems, models)
    else:
        return selected_problems, selected_models


def get_source(m):
    '''collects the source code.'''
    print("\nFor part "+m.name)
    print("Submitting: "+m.model_file+"\n")
    with open(m.model_file,'r') as f:
        src = f.read()
        f.close()
    return src


def compute(metadata, model_file_override=None):
    '''
    Determines which assignment parts the student would like to submit.
    Then computes his/her answers to those assignment parts

    Args:
        metadata:  the assignment metadata
        model_file_override:  an optional model file to override the metadata 
            default

    Returns:
        a dictionary of results in the format Coursera expects
    '''

    if model_file_override is not None:
        print('Overriding model file with: '+model_file_override)

    selected_problems, selected_models = \
        part_prompt(metadata.problem_data, metadata.model_data)
    results = {}

    #submission needs empty dict for every assignment part
    results.update({prob_data.sid : {} for prob_data in metadata.problem_data})
    results.update({model_data.sid : {} for model_data in metadata.model_data})

    for model in selected_models:
        if model_file_override != None:
            model.model_file = model_file_override
        #else:
        #    model_file = model.model_file
        
        if not os.path.isfile(model.model_file):
            print('Unable to locate assignment file "%s" in the current working directory.' % model_file)
            continue
        
        submission = get_source(model)
        results[model.sid] = {'output':submission}

    for problem in selected_problems:
        if model_file_override != None:
            model_file = model_file_override
        else:
            model_file = problem.model_file
        
        if not os.path.isfile(model_file):
            print('Unable to locate assignment file "%s" in the current working directory.' % model_file)
            continue

        submission = output(problem, model_file)
        if submission != None:
            results[problem.sid] = {'output':submission}

    print('\n== Computations Complete ...')

    return results


def run_minizinc(process_id, model_file, data_file, solve_time_limit=10000, 
    mzn_time_limit=5000, all_solutions=False):
    '''
    Executes MiniZinc and Gecode on a model and data file.
    this submission process id is used to prevent collisions of temporary 
    files unique on the file system.

    Args:
        process_id: the id of this submission process
        model_file: a .mzn model file
        data_file:  a .dzn data file
        solve_time_limit: the amount of time before the MiniZinc solve process 
            should be terminated
        mzn_time_limit: the amount of time the solver is given
        all_solutions: indicates all solutions should be printed

    Returns:
        stdout and stderr of the MiniZinc process
    '''

    cmd = [minizinc_cmd, model_file, data_file]
    if all_solutions:
        cmd.append('--all-solutions')
    if isinstance(solve_time_limit, int):
        cmd.append('--fzn-flags')
        cmd.append('-time '+str(solve_time_limit))

    print('running gecode minizinc as a subprocess with the command,')
    print(' '.join(cmd))

    process = Popen(cmd, stdout=PIPE, stderr=PIPE, shell = (os.name == 'nt'))

    stdout = ''
    stderr = ''

    while process.poll() != 0:
        while True:
            line = process.stdout.readline().decode('utf8')
            if not line:
                break
            stdout += line
            sys.stdout.write(line)
        sys.stdout.flush()
        while True:
            line = process.stderr.readline().decode('utf8')
            if not line:
                break
            stderr += line
            sys.stderr.write(stderr)
        sys.stderr.flush()

    return stdout, stderr


def last_solution(solution_stream):
    '''Extracts the last solution from a stream of output solutions'''
    solutions = solution_stream.split(mzn_solution)
    if len(solutions) < 2: #this means there was no solution
        return solution_stream
    return mzn_solution.join(solutions[-2:])


def output(problem, model_file=None):
    '''
    Attempts to execute MiniZinc locally on a given assignment problem.

    Args:
        problem: the assignment problem of interest
        model_file: a .mzn model file to override the default in problem

    Returns:
        the submission string in a format that the grader expects
    '''

    if model_file == None:
        model_file = problem.model_file
    
    solution = ''

    start = time.clock()
    try:
        stdout, stderr = run_minizinc(
            process_id,
            model_file,
            problem.input_file,
            solve_time_limit=problem.runtime*1000,
            mzn_time_limit=3600000,
            all_solutions=True)
    except Exception as e:
        print('''running minizinc as a subprocess on input %s with 
            model %s raised an exception\ntry testing outside of this 
            submission script''' % (problem.input_file, model_file))
        print('exception message:')
        print(e)
        print('')
        return 'Local Exception =('+'\n\n'+str(e)
    end = time.clock()
    
    solution = last_solution(stdout)

    print('For part: '+problem.name)
    print('Submitting: ')
    print(solution)

    submission_string = \
        solution.strip()+'\n'+ \
        str(end-start)+'\n'+ \
        'command line submission'

    return submission_string


def login_dialog(assignment_key, results, credentials_file_location = '_credentials'):
    '''
    Requests Coursera login credentials from the student and submits the 
    student's solutions for grading

    Args:
        assignment_key: Coursera's assignment key
        results: a dictionary of results in Cousera's format
        credentials_file_location: a file location where login credentials can 
            be found
    '''

    success = False
    tries = 0

    while not success:

        # stops infinate loop when credentials file is incorrect 
        if tries <= 0:
            login, token = login_prompt(credentials_file_location)
        else:
            login, token = login_prompt('')

        code, responce = submit_solution(assignment_key, login, token, results)

        print('\n== Coursera Responce ...')
        #print(code)
        print(responce)
        
        if code != 401:
            success = True
        else:
            print('\ntry logging in again')
        tries += 1

def login_prompt(credentials_file_location):
    '''
    Attempts to load credentials from a file, if that fails asks the user.
    Returns:
        the user's login and token
    '''
    
    if os.path.isfile(credentials_file_location):
        try:
            with open(credentials_file_location, 'r') as metadata_file:
                login = metadata_file.readline().strip()
                token = metadata_file.readline().strip()
                metadata_file.close()
        except:
            login, token = basic_prompt()
    else:
        login, token = basic_prompt()
    return login, token


def basic_prompt():
    '''
    Prompt the user for login credentials. 
    Returns:
        the user's login and token
    '''
    login = input('User Name (e-mail address): ')
    token = input('Submission Token (from the assignment page): ')
    return login, token


def submit_solution(assignment_key, email_address, token, results):
    '''
    Sends the student's submission to Coursera for grading via the submission 
    API.

    Args:
        assignment_key: Coursera's assignment key
        email_address: the student's email
        token: the student's assignment token
        results: a dictionary of results in Cousera's format

    Returns:
        the https response code and a feedback message
    '''

    print('\n== Connecting to Coursera ...')
    print('Submitting %d of %d parts' % 
        (sum(['output' in v for k,v in results.items()]), len(results)))

    # build json datastructure
    parts = {}
    submission = {
        'assignmentKey': assignment_key,  
        'submitterEmail': email_address,  
        'secret': token,
        'parts': results
    }
    #print(submission)

    # send submission
    req = Request(submitt_url)
    req.add_header('Cache-Control', 'no-cache')
    req.add_header('Content-type', 'application/json')

    try:
        res = urlopen(req, json.dumps(submission).encode('utf8'))
    except HTTPError as e:
        responce = json.loads(e.read().decode('utf8'))

        if 'details' in responce and responce['details'] != None and \
            'learnerMessage' in responce['details']:
            return e.code, responce['details']['learnerMessage']
        else:
            return e.code, 'Unexpected response code, please contact the ' \
                               'course staff.\nDetails: ' + responce['message']

    code = res.code
    responce = json.loads(res.read().decode('utf8'))

    if code >= 200 and code <= 299:
        return code, 'Your submission has been accepted and will be ' \
                     'graded shortly.'

    return code, 'Unexpected response code, please contact the '\
                 'course staff.\nDetails: ' + responce


def main(args):
    '''
    1) Reads a metadata file to customize the submission process to 
    a particular assignment.  
    2) The compute the student's answers to the assignment parts.
    3) Submits the student's answers for grading.

    Provides the an option for saving the submissions locally.  This is very 
    helpful when testing the assignment graders.

    Args:
        args: CLI arguments from an argparse parser
    '''

    if args.metadata is None:
        metadata = load_metadata()
    else:
        print('Overriding metadata file with: '+args.metadata)
        metadata = load_metadata(args.metadata)

    print('==\n== '+metadata.name+' Submission \n==')
    
    # compute dialog
    results = compute(metadata, args.override)

    if sum(['output' in v for k,v in results.items()]) <= 0:
        return

    # store submissions if requested
    if args.record_submission == True:
        print('Recording submission as files')
        for sid, submission in results.items():
            if 'output' in submission:
                directory = '_'+sid
                if not os.path.exists(directory):
                    os.makedirs(directory)

                submission_file_name = directory+'/submission.sub'
                print('  writting submission file: '+submission_file_name)
                with open(submission_file_name,'w') as submission_file:
                    submission_file.write(submission['output'])
                    submission_file.close()

    # submit dialog
    if args.credentials is None:
        login_dialog(metadata.assignment_key, results)
    else:
        print('Overriding credentials file with: '+args.credentials)
        login_dialog(metadata.assignment_key, results, args.credentials)



import argparse
def build_parser():
    '''
    Builds an argument parser for the CLI

    Returns:
        parser: an argparse parser
    '''

    parser = argparse.ArgumentParser(
        description='''The command line interface for Modeling Discrete 
            Optimization assignment submission on the Coursera Platform.''',
        epilog='''Please file bugs on github at: 
        https://github.com/ccoffrin/coursera-mdo-submission/issues. If you 
        would like to contribute to this tool's development, check it out at: 
        https://github.com/ccoffrin/coursera-mdo-submission'''
    )

    parser.add_argument('-v', '--version', action='version', 
        version='%(prog)s '+version)

    parser.add_argument('-o', '--override', 
        help='overrides the model source file specified in the \'_coursera\' file')

    parser.add_argument('-m', '--metadata', 
        help='overrides the \'_coursera\' metadata file')

    parser.add_argument('-c', '--credentials', 
        help='overrides the \'_credentials\' credentials file')

    parser.add_argument('-rs', '--record_submission', 
        help='records the submission(s) as files', action='store_true')
    return parser


if __name__ == '__main__':
    import sys

    try:
        cmd = [minizinc_cmd, '--help']
        process = Popen(cmd, stdout=PIPE, shell = (os.name == 'nt') )
        stdout, stderr = process.communicate()    
    except OSError as e:
        print('unable to find minizinc command: '+minizinc_cmd)
        print('details: ', e)
        quit()

    parser = build_parser()
    main(parser.parse_args())

