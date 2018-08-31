"""
Project for Week 2 of "Python Data Visualization".
Read World Bank GDP data and create some basic XY plots.

Be sure to read the project description page for further information
about the expected behavior of the program.

@author: salimt
"""

import csv
import pygal


def read_csv_as_nested_dict(filename, keyfield, separator, quote):
    """
    Inputs:
      filename  - Name of CSV file
      keyfield  - Field to use as key for rows
      separator - Character that separates fields
      quote     - Character used to optionally quote fields

    Output:
      Returns a dictionary of dictionaries where the outer dictionary
      maps the value in the key_field to the corresponding row in the
      CSV file.  The inner dictionaries map the field names to the
      field values for that row.
    """
    

    with open(filename, 'r') as csvfile:
        fields = {}
        reader = csv.DictReader(csvfile, delimiter=separator, quotechar=quote)
        for line in reader:
            temp = dict(line)
            fields[temp.get(keyfield)] = temp
            
    return fields

### TEST
#result = {'1': {'Field4': '4', 'Field3': '3', 'Field1': '1', 'Field2': '2'}, 
#          '5': {'Field4': '8', 'Field3': '7', 'Field1': '5', 'Field2': '6'}, 
#          '9': {'Field4': '12', 'Field3': '11', 'Field1': '9', 'Field2': '10'}}
#
#if read_csv_as_nested_dict('table1.csv', 'Field1', ',', '"') == result:
#    print("True")
#else:
#    print("False")
    
 
def build_plot_values(gdpinfo, gdpdata):
    """
    Inputs:
      gdpinfo - GDP data information dictionary
      gdpdata - A single country's GDP stored in a dictionary whose
                keys are strings indicating a year and whose values
                are strings indicating the country's corresponding GDP
                for that year.

    Output: 
      Returns a list of tuples of the form (year, GDP) for the years
      between "min_year" and "max_year", inclusive, from gdpinfo that
      exist in gdpdata.  The year will be an integer and the GDP will
      be a float.
    """
    
    result = []
    for year in range(gdpinfo.get("min_year"), gdpinfo.get("max_year")+1):
        if str(year) in gdpdata.keys():
            try:
                result.append((year, float(gdpdata.get(str(year)))))
            except ValueError:
                continue

    return result

### TEST
#gdpinfo = {'country_name': 'Country Name', 'min_year': 2001, 'gdpfile': '',
#           'max_year': 2015, 'quote': '', 'country_code': 'Code', 'separator': ''}
#gdpdata =  {'2003': '2', '2013': '', '2005': '4', '2014': '13', '2010': '',
#            '2015': '14', '2009': '8', '2008': '7', '2002': '1', '2001': '',
#            '2011': '10', '2006': '5', '2012': '11', '2007': '', '2004': ''}
#
#if build_plot_values(gdpinfo, gdpdata) == [(2002, 1.0), (2003, 2.0), (2005, 4.0), 
#                    (2006, 5.0), (2008, 7.0), (2009, 8.0), (2011, 10.0), (2012, 11.0),
#                    (2014, 13.0), (2015, 14.0)]:
#    print("True")
#else:
#    print("False")


def build_plot_dict(gdpinfo, country_list):
    """
    Inputs:
      gdpinfo      - GDP data information dictionary
      country_list - List of strings that are country names

    Output:
      Returns a dictionary whose keys are the country names in
      country_list and whose values are lists of XY plot values 
      computed from the CSV file described by gdpinfo.

      Countries from country_list that do not appear in the
      CSV file should still be in the output dictionary, but
      with an empty XY plot value list.
    """
    
    final = {}
    
    for country in country_list:
        datas = []
        
        country_info = read_csv_as_nested_dict(gdpinfo.get("gdpfile"), gdpinfo.get("country_name"), 
                                      gdpinfo.get("separator"), gdpinfo.get("quote"))
        for year in range(gdpinfo.get("min_year"), gdpinfo.get("max_year")+1):
            try:
                print(year, country_info.get(country).get(str(year)))
                datas.append((int(year), float(country_info.get(country).get(str(year)))))
            except ValueError:
                continue
            except AttributeError:
                continue
        final[country] = datas
        
    return final


### TEST
#answer = {'A 5 ': [(20010, 8000000000.0), (20011, 7000000000.0), (20012, 6000000000.0),
#                   (20013, 5000000000.0), (20014, 4000000000.0), (20015, 3000000000.0),
#                   (20016, 2000000000.0), (20017, 1000000000.0)]}
#gdpinfo = {'country_code': 'CC', 'gdpfile': 'gdptable3.csv', 'quote': "'",
#          'separator': ';', 'country_name': 'ID', 'min_year': 20010, 'max_year': 20017}
#
#if build_plot_dict(gdpinfo, ['A 5 ']) == answer:
#    print("True")
#else:
#    print("False")


def render_xy_plot(gdpinfo, country_list, plot_file):
    """
    Inputs:
      gdpinfo      - GDP data information dictionary
      country_list - List of strings that are country names
      plot_file    - String that is the output plot file name

    Output:
      Returns None.

    Action:
      Creates an SVG image of an XY plot for the GDP data
      specified by gdpinfo for the countries in country_list.
      The image will be stored in a file named by plot_file.
    """
    
    gdp_chart = pygal.XY()
    gdp_chart.title = 'Plot of GDP for select countries spanning ' + str(gdpinfo.get("min_year")) \
                                        + ' to ' + str(gdpinfo.get("max_year"))
    gdp_chart.y_title = 'GDP in current US dollars'

    for country in country_list:
        gdp_chart.add(country, build_plot_dict(gdpinfo, country_list).get(country))

    
#    gdp_chart.render_in_browser()
    gdp_chart.render_to_file(plot_file)


def test_render_xy_plot():
    """
    Code to exercise render_xy_plot and generate plots from
    actual GDP data.
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

    render_xy_plot(gdpinfo, [], "isp_gdp_xy_none.svg")
    render_xy_plot(gdpinfo, ["China"], "isp_gdp_xy_china.svg")
    render_xy_plot(gdpinfo, ["United Kingdom", "United States"],
                   "isp_gdp_xy_uk+usa.svg")


# Make sure the following call to test_render_xy_plot is commented out
# when submitting to OwlTest/CourseraTest.

#test_render_xy_plot()
