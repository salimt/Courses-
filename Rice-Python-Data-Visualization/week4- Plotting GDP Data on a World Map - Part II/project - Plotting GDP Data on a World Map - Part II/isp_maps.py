"""
Project for Week 4 of "Python Data Visualization".
Unify data via common country codes.

Be sure to read the project description page for further information
about the expected behavior of the program.

@author: salimt
"""

import csv
import math
import copy
import pygal


def read_csv_file(file_name, separator, quote):
    """
    Given a CSV file, read the data into a nested list
    Input: String corresponding to comma-separated  CSV file
    Output: Nested list consisting of the fields in the CSV file
    """
       
    with open(file_name, newline='') as csv_file:
        csv_table = []
        csv_reader = csv.reader(csv_file, delimiter=separator, quotechar=quote)
        for row in csv_reader:
            csv_table.append(row)
    return csv_table

def build_country_code_converter(codeinfo):
    """
    Inputs:
      codeinfo      - A country code information dictionary

    Output:
      A dictionary whose keys are plot country codes and values
      are world bank country codes, where the code fields in the
      code file are specified in codeinfo.
    """
    joined_codes = {}
    
    reader = read_csv_file(codeinfo.get("codefile"), codeinfo.get("separator"),
                                                     codeinfo.get("quote"))

    plot_code = reader[0].index(codeinfo.get("plot_codes"))
    data_code = reader[0].index(codeinfo.get("data_codes"))
    
    for data in reader[1:]:
        joined_codes[data[plot_code]] = data[data_code]
    
    return joined_codes

##TEST
#codeinfo = {'separator': ',', 'plot_codes': 'Code1', 'data_codes': 'Code2', 
#            'quote': "'", 'codefile': 'code1.csv'}
#if build_country_code_converter(codeinfo) == {'Gh': 'Ij', 'MN': 'OP', 'Ab': 'Cd', 'ST': 'UV'}:
#    print("True")
#else:
#    print("False")

def reconcile_countries_by_code(codeinfo, plot_countries, gdp_countries):
    """
    Inputs:
      codeinfo       - A country code information dictionary
      plot_countries - Dictionary whose keys are plot library country codes
                       and values are the corresponding country name
      gdp_countries  - Dictionary whose keys are country codes used in GDP data

    Output:
      A tuple containing a dictionary and a set.  The dictionary maps
      country codes from plot_countries to country codes from
      gdp_countries.  The set contains the country codes from
      plot_countries that did not have a country with a corresponding
      code in gdp_countries.

      Note that all codes should be compared in a case-insensitive
      way.  However, the returned dictionary and set should include
      the codes with the exact same case as they have in
      plot_countries and gdp_countries.
    """
    
    found_country_codes = {}
    no_code_countries = set()
   
    for country in plot_countries:
        for code in gdp_countries.values():
            if plot_countries.get(country) == code.get("Country Name"):
                found_country_codes[country] = code.get("Country Code")
         
    for code in plot_countries:
        if not code in found_country_codes:
            no_code_countries.add(code)
    
    tupled_values = () + (found_country_codes, no_code_countries,)
    return tupled_values


##TEST
#codeinfo = {'plot_codes': 'ISO3166-1-Alpha-2', 'quote': '"', 'separator': ',',
#            'codefile': 'code4.csv', 'data_codes': 'ISO3166-1-Alpha-3'}
#plot_countries =  {'pr': 'Puerto Rico', 'us': 'United States', 'no': 'Norway'}
#gdp_countries = {'USA': {'Country Name': 'United States', 'Country Code': 'USA'}, 
#                 'NOR': {'Country Name': 'Norway', 'Country Code': 'NOR'}}
#
#if reconcile_countries_by_code(codeinfo,plot_countries,gdp_countries) == \
#                                        ({'us': 'USA', 'no': 'NOR'}, {'pr'}):
#    print("True")
#else:
#    print("False")



def build_map_dict_by_code(gdpinfo, codeinfo, plot_countries, year):
    """
    Inputs:
      gdpinfo        - A GDP information dictionary
      codeinfo       - A country code information dictionary
      plot_countries - Dictionary mapping plot library country codes to country names
      year           - String year for which to create GDP mapping

    Output:
      A tuple containing a dictionary and two sets.  The dictionary
      maps country codes from plot_countries to the log (base 10) of
      the GDP value for that country in the specified year.  The first
      set contains the country codes from plot_countries that were not
      found in the GDP data file.  The second set contains the country
      codes from plot_countries that were found in the GDP data file, but
      have no GDP data for the specified year.
    """
    
    country_codes = {}
    copy_plot_countries = copy.deepcopy(plot_countries)
    no_value_countries = set()
    
    code_reader = read_csv_file(codeinfo.get("codefile"), codeinfo.get("separator"),
                                                          codeinfo.get("quote"))
    
    plot_code = code_reader[0].index(codeinfo.get("plot_codes"))
    data_code = code_reader[0].index(codeinfo.get("data_codes"))

    with open(gdpinfo.get("gdpfile"), 'r') as csvfile:
        gdp_reader = csv.DictReader(csvfile, delimiter=gdpinfo.get("separator"), 
                                                       quotechar=gdpinfo.get("quote"))
        
        for data in gdp_reader:
            for code in code_reader[1:]:
                if data[gdpinfo.get("country_code")].lower() == code[data_code].lower():
                    plt_code = [plot_c for plot_c in copy_plot_countries \
                                                  if code[plot_code].lower() == plot_c.lower()]
                    if not len(plt_code) == 0:
                        try:
                            country_codes[''.join(plt_code)] = math.log10(float(data.get(year)))
                        except ValueError:
                            no_value_countries.add(''.join(plt_code))
                        del copy_plot_countries[''.join(plt_code)]
                        
    return () + (country_codes, set(copy_plot_countries.keys()), no_value_countries, )

##TEST
#gdpinfo = {'country_code': 'CC', 'gdpfile': 'gdptable3.csv', 'quote': "'",
#           'separator': ';', 'country_name': 'ID', 'min_year': 20010, 'max_year': 20017}
#codeinfo = {'separator': ',', 'plot_codes': 'Code4', 'data_codes': 'Code3', 'quote': "'", 
#            'codefile': 'code1.csv'}
#plot_countries =  {'C3': 'c3', 'C2': 'c2', 'C1': 'c1'}
#if build_map_dict_by_code(gdpinfo, codeinfo, plot_countries, '20016') == \
#      ({'C3': 10.780708577050003, 'C2': 9.301029995663981, 'C1': 9.301029995663981}, set(), set()):
#    print("True")
#else:
#    print("False")

def render_world_map(gdpinfo, codeinfo, plot_countries, year, map_file):
    """
    Inputs:
      gdpinfo        - A GDP information dictionary
      codeinfo       - A country code information dictionary
      plot_countries - Dictionary mapping plot library country codes to country names
      year           - String year of data
      map_file       - String that is the output map file name

    Output:
      Returns None.

    Action:
      Creates a world map plot of the GDP data in gdp_mapping and outputs
      it to a file named by svg_filename.
    """    
    worldmap_chart = pygal.maps.world.World()
    worldmap_chart.title = 'GDP by country for ' + year + ' (log scale), unified by common' + \
                           ' country Code'
                           
    gdp_datas = build_map_dict_by_code(gdpinfo, codeinfo, plot_countries, year)
    
    worldmap_chart.add('GDP For ' + year, gdp_datas[0])
    worldmap_chart.add('Missing from World Bank Data', gdp_datas[1])
    worldmap_chart.add('No GDP Data', gdp_datas[2])
     
    worldmap_chart.render_in_browser()          #renders the world map on default browser
#    worldmap_chart.render_to_file(map_file)    #saves the file with the given name


def test_render_world_map():
    """
    Test the project code for several years
    """
    gdpinfo = {
        "gdpfile": "isp_gdp.csv",
        "separator": ",",
        "quote": '"',
        "min_year": 1960,
        "max_year": 2015,
        "country_name": "Country Name",
        "country_code": "Country Code"
    }

    codeinfo = {
        "codefile": "isp_country_codes.csv",
        "separator": ",",
        "quote": '"',
        "plot_codes": "ISO3166-1-Alpha-2",
        "data_codes": "ISO3166-1-Alpha-3"
    }

    # Get pygal country code map
    pygal_countries = pygal.maps.world.COUNTRIES

    # 1960
    render_world_map(gdpinfo, codeinfo, pygal_countries, "1960", "isp_gdp_world_code_1960.svg")

    # 1980
    render_world_map(gdpinfo, codeinfo, pygal_countries, "1980", "isp_gdp_world_code_1980.svg")

    # 2000
    render_world_map(gdpinfo, codeinfo, pygal_countries, "2000", "isp_gdp_world_code_2000.svg")

    # 2010
    render_world_map(gdpinfo, codeinfo, pygal_countries, "2010", "isp_gdp_world_code_2010.svg")


# Make sure the following call to test_render_world_map is commented

#test_render_world_map()
