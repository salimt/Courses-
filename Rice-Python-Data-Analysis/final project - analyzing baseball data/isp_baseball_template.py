# -*- coding: utf-8 -*-
"""
@author: salimt


Project for Week 4 of "Python Data Analysis".
Processing CSV files with baseball stastics.

Be sure to read the project description page for further information
about the expected behavior of the program.
"""

import csv

##
## Provided code from Week 3 Project
##

def read_csv_as_list_dict(filename, separator, quote):
    """
    Inputs:
      filename  - name of CSV file
      separator - character that separates fields
      quote     - character used to optionally quote fields
    Output:
      Returns a list of dictionaries where each item in the list
      corresponds to a row in the CSV file.  The dictionaries in the
      list map the field names to the field values for that row.
    """
    table = []
    with open(filename, newline='') as csvfile:
        csvreader = csv.DictReader(csvfile, delimiter=separator, quotechar=quote)
        for row in csvreader:
            table.append(row)
    return table


def read_csv_as_nested_dict(filename, keyfield, separator, quote):
    """
    Inputs:
      filename  - name of CSV file
      keyfield  - field to use as key for rows
      separator - character that separates fields
      quote     - character used to optionally quote fields
    Output:
      Returns a dictionary of dictionaries where the outer dictionary
      maps the value in the key_field to the corresponding row in the
      CSV file.  The inner dictionaries map the field names to the
      field values for that row.
    """
    table = {}
    with open(filename, newline='') as csvfile:
        csvreader = csv.DictReader(csvfile, delimiter=separator, quotechar=quote)
        for row in csvreader:
            rowid = row[keyfield]
            table[rowid] = row
    return table

##
## Provided formulas for common batting statistics
##

# Typical cutoff used for official statistics
MINIMUM_AB = 500

def batting_average(info, batting_stats):
    """
    Inputs:
      batting_stats - dictionary of batting statistics (values are strings)
    Output:
      Returns the batting average as a float
    """
    hits = float(batting_stats[info["hits"]])
    at_bats = float(batting_stats[info["atbats"]])
    if at_bats >= MINIMUM_AB:
        return hits / at_bats
    else:
        return 0

def onbase_percentage(info, batting_stats):
    """
    Inputs:
      batting_stats - dictionary of batting statistics (values are strings)
    Output:
      Returns the on-base percentage as a float
    """
    hits = float(batting_stats[info["hits"]])
    at_bats = float(batting_stats[info["atbats"]])
    walks = float(batting_stats[info["walks"]])
    if at_bats >= MINIMUM_AB:
        return (hits + walks) / (at_bats + walks)
    else:
        return 0

def slugging_percentage(info, batting_stats):
    """
    Inputs:
      batting_stats - dictionary of batting statistics (values are strings)
    Output:
      Returns the slugging percentage as a float
    """
    hits = float(batting_stats[info["hits"]])
    doubles = float(batting_stats[info["doubles"]])
    triples = float(batting_stats[info["triples"]])
    home_runs = float(batting_stats[info["homeruns"]])
    singles = hits - doubles - triples - home_runs
    at_bats = float(batting_stats[info["atbats"]])
    if at_bats >= MINIMUM_AB:
        return (singles + 2 * doubles + 3 * triples + 4 * home_runs) / at_bats
    else:
        return 0


##
## Part 1: Functions to compute top batting statistics by year
##

def filter_by_year(statistics, year, yearid):
    """
    Inputs:
      statistics - List of batting statistics dictionaries
      year       - Year to filter by
      yearid     - Year ID field in statistics
    Outputs:
      Returns a list of batting statistics dictionaries that
      are from the input year.
    """
    statistics_for_yearid = []
    for value in statistics:
        if yearid in value:
            if int(value.get(yearid)) == year:
                statistics_for_yearid.append(value)
    return statistics_for_yearid
    
#print(filter_by_year([{'year3': '3', 'year2': '2', 'year1': '1'}],1, 'year1')) #same
#print(filter_by_year([{'year1': '1', 'year3': '3', 'year2': '2'}],1, 'year2')) #[]
#print(filter_by_year([{'year1': '1', 'year3': '3', 'year2': '2'},
#{'year1': '2', 'year3': '4', 'year2': '3'},
#{'year1': '3', 'year3': '5', 'year2': '4'}],
#3, 'year1'))

def top_player_ids(info, statistics, formula, numplayers):
    """
    Inputs:
      info       - Baseball data information dictionary
      statistics - List of batting statistics dictionaries
      formula    - function that takes an info dictionary and a
                   batting statistics dictionary as input and
                   computes a compound statistic
      numplayers - Number of top players to return
    Outputs:
      Returns a list of tuples, player ID and compound statistic
      computed by formula, of the top numplayers players sorted in
      decreasing order of the computed statistic.
    """

    top_players = []
    top_players_list = []
    
    for values in statistics:
        key = "".join(values[info["playerid"]])
        value = formula(info,values)
        top_players.append((key, value))

    for _ in range(numplayers):
        max_val, max_player = 0, ""
        for player in top_players:
            if player[1] > max_val and player not in top_players_list:
                max_val = player[1]
                max_player = player[0]
        top_players_list.append((max_player, max_val))
    return top_players_list


def lookup_player_names(info, top_ids_and_stats):
    """
    Inputs:
      info              - Baseball data information dictionary
      top_ids_and_stats - list of tuples containing player IDs and
                          computed statistics
    Outputs:
      List of strings of the form "x.xxx --- FirstName LastName",
      where "x.xxx" is a string conversion of the float stat in
      the input and "FirstName LastName" is the name of the player
      corresponding to the player ID in the input.
    """
    with open(info["masterfile"], newline='') as csvfile:
        reader = csv.DictReader(csvfile,
                                delimiter=info["separator"],
                                quotechar=info["quote"])
        
        player_names, player_info = {}, []

        for player in reader:
            player_names[player[info["playerid"]]] = (player[info["firstname"]], 
                                                      player[info["lastname"]])

        for top_id in top_ids_and_stats:
            player_info.append("{:0.3f} --- {} {}".format(top_id[1], 
                                                          player_names[top_id[0]][0],
                                                          player_names[top_id[0]][1]))
        return player_info


#print(lookup_player_names({'masterfile': 'master2.csv', 'battingfile': '', 
#'separator': ',', 'quote': '"', 
#'playerid': 'playerID', 'firstname': 'nameFirst', 'lastname': 'nameLast', 'yearid': 'yearID', 
#'atbats': 'AB', 'hits': 'H', 'doubles': '2B', 'triples': '3B', 'homeruns': 'HR', 'walks': 'BB', 
#'battingfields': ['AB', 'H', '2B', '3B', 'HR', 'BB']},
#[('guerrvl01', 0.88385), ('berkmla01', 0.2832854)]))


def compute_top_stats_year(info, formula, numplayers, year):
    """
    Inputs:
      info        - Baseball data information dictionary
      formula     - function that takes an info dictionary and a
                    batting statistics dictionary as input and
                    computes a compound statistic
      numplayers  - Number of top players to return
      year        - Year to filter by
    Outputs:
      Returns a list of strings for the top numplayers in the given year
      according to the given formula.
    """
    with open(info["battingfile"], newline='') as csvfile:
        reader = csv.DictReader(csvfile,
                                delimiter=info["separator"],
                                quotechar=info["quote"])
        
        stats = filter_by_year(reader, year, info['yearid'])
        
        return lookup_player_names(info, top_player_ids(info, stats, formula, numplayers))



#print(compute_top_stats_year({'masterfile': 'master3.csv', 
#'battingfile': 'batting3.csv', 'separator': ',', 'quote': '"', 
#'playerid': 'playerID', 'firstname': 'nameFirst', 'lastname': 'nameLast', 'yearid': 'yearID', 
#'atbats': 'AB', 'hits': 'H', 'doubles': '2B', 'triples': '3B', 'homeruns': 'HR', 'walks': 'BB', 
#'battingfields': ['AB', 'H', '2B', '3B', 'HR', 'BB']},
#onbase_percentage, 5, 2001))

##
## Part 2: Functions to compute top batting statistics by career
##

def aggregate_by_player_id(statistics, playerid, fields):
    """
    Inputs:
      statistics - List of batting statistics dictionaries
      playerid   - Player ID field name
      fields     - List of fields to aggregate
    Output:
      Returns a nested dictionary whose keys are player IDs and whose values
      are dictionaries of aggregated stats.  Only the fields from the fields
      input will be aggregated in the aggregated stats dictionaries.
    """

    aggregated_players = {}

    for stat in statistics:
        if stat[playerid] in aggregated_players:
            for field in fields:
                if field in aggregated_players[stat[playerid]]:
                    aggregated_players[stat[playerid]][field] += int(stat[field])
        else: 
            temp_dict = {}           
            for field in fields:                
                temp_dict[playerid] = stat[playerid]
                temp_dict[field] = int(stat[field])
            aggregated_players[stat[playerid]] = temp_dict
            
    return aggregated_players



#print(aggregate_by_player_id([{'player': '1', 'stat1': '3', 'stat2': '4', 'stat3': '5'},
#{'player': '2', 'stat1': '3', 'stat2': '4', 'stat3': '8'},
#{'player': '3', 'stat1': '3', 'stat2': '5', 'stat3': '1'}],
#'stat1', ['stat2', 'stat3']))
#
#print(aggregate_by_player_id([{'player': '1', 'stat2': '4', 'stat1': '3', 'stat3': '5'},
#{'player': '1', 'stat2': '1', 'stat1': '2', 'stat3': '8'},
#{'player': '1', 'stat2': '7', 'stat1': '5', 'stat3': '4'}],
#'player', ['stat1']))
#
#print(aggregate_by_player_id([{'player': '1', 'stat2': '4', 'stat1': '3', 'stat3': '5'},
#{'player': '2', 'stat2': '2', 'stat1': '1', 'stat3': '3'},
#{'player': '3', 'stat2': '1', 'stat1': '4', 'stat3': '6'}],
#'player', ['stat1', 'stat3']))
#
#print(aggregate_by_player_id([{'player': '1', 'stat1': '3', 'stat2': '4', 'stat3': '5'},
#{'player': '1', 'stat1': '2', 'stat2': '1', 'stat3': '8'},
#{'player': '2', 'stat1': '5', 'stat2': '7', 'stat3': '4'}],
#'player', ['stat1']))


def compute_top_stats_career(info, formula, numplayers):
    """
    Inputs:
      info        - Baseball data information dictionary
      formula     - function that takes an info dictionary and a
                    batting statistics dictionary as input and
                    computes a compound statistic
      numplayers  - Number of top players to return
    """
    
    
    with open(info["battingfile"], newline='') as csvfile:
        reader = csv.DictReader(csvfile,
                                delimiter=info["separator"],
                                quotechar=info["quote"])        
      
        stats = aggregate_by_player_id(reader, info["playerid"], info["battingfields"])
        
        stat_list = [stats[stat] for stat in stats]
            
        return lookup_player_names(info, top_player_ids(info, stat_list, formula, numplayers))

#print(compute_top_stats_career({'masterfile': 'master1.csv', 'battingfile': 'batting1.csv',
# 'separator': ',', 'quote': '"', 
#'playerid': 'player', 'firstname': 'firstname', 'lastname': 'lastname', 'yearid': 'year', 
#'atbats': 'atbats', 'hits': 'hits', 'doubles': 'doubles', 'triples': 'triples',
# 'homeruns': 'homers', 'walks': 'walks', 
#'battingfields': ['atbats', 'hits', 'doubles', 'triples', 'homers', 'walks']},
#batting_average, 4))

##
## Provided testing code
##

def test_baseball_statistics():
    """
    Simple testing code.
    """

    #
    # Dictionary containing information needed to access baseball statistics
    # This information is all tied to the format and contents of the CSV files
    #
    baseballdatainfo = {"masterfile": "Master_2016.csv",   # Name of Master CSV file
                        "battingfile": "Batting_2016.csv", # Name of Batting CSV file
                        "separator": ",",                  # Separator character in CSV files
                        "quote": '"',                      # Quote character in CSV files
                        "playerid": "playerID",            # Player ID field name
                        "firstname": "nameFirst",          # First name field name
                        "lastname": "nameLast",            # Last name field name
                        "yearid": "yearID",                # Year field name
                        "atbats": "AB",                    # At bats field name
                        "hits": "H",                       # Hits field name
                        "doubles": "2B",                   # Doubles field name
                        "triples": "3B",                   # Triples field name
                        "homeruns": "HR",                  # Home runs field name
                        "walks": "BB",                     # Walks field name
                        "battingfields": ["AB", "H", "2B", "3B", "HR", "BB"]}

    print("Top 5 batting averages in 1923")
    top_batting_average_1923 = compute_top_stats_year(baseballdatainfo, batting_average, 5, 1923)
    for player in top_batting_average_1923:
        print(player)
    print("")

    print("Top 10 batting averages in 2010")
    top_batting_average_2010 = compute_top_stats_year(baseballdatainfo, batting_average, 10, 2010)
    for player in top_batting_average_2010:
        print(player)
    print("")

    print("Top 10 on-base percentage in 2010")
    top_onbase_2010 = compute_top_stats_year(baseballdatainfo, onbase_percentage, 10, 2010)
    for player in top_onbase_2010:
        print(player)
    print("")

    print("Top 10 slugging percentage in 2010")
    top_slugging_2010 = compute_top_stats_year(baseballdatainfo, slugging_percentage, 10, 2010)
    for player in top_slugging_2010:
        print(player)
    print("")

    # You can also use lambdas for the formula
    #  This one computes onbase plus slugging percentage
    print("Top 10 OPS in 2010")
    top_ops_2010 = compute_top_stats_year(baseballdatainfo,
                                          lambda info, stats: (onbase_percentage(info, stats) +
                                                               slugging_percentage(info, stats)),
                                          10, 2010)
    for player in top_ops_2010:
        print(player)
    print("")

    print("Top 20 career batting averages")
    top_batting_average_career = compute_top_stats_career(baseballdatainfo, batting_average, 20)
    for player in top_batting_average_career:
        print(player)
    print("")


# Make sure the following call to test_baseball_statistics is
# commented out when submitting to OwlTest/CourseraTest.

#test_baseball_statistics()
