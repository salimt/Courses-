Readme for GeoNames Postal Code files :

allCountries.zip: all countries, for the UK only the outwards codes, the UK total codes are in GB_full.csv.zip 
GB_full.csv.zip the full codes for the UK, ca 1.7 mio rows
<iso countrycode>: country specific subset also included in allCountries.zip
This work is licensed under a Creative Commons Attribution 3.0 License.
This means you can use the dump as long as you give credit to geonames (a link on your website to www.geonames.org is ok)
see http://creativecommons.org/licenses/by/3.0/
UK (GB_full.csv.zip): Contains Royal Mail data Royal Mail copyright and database right 2017.
The Data is provided "as is" without warranty or any representation of accuracy, timeliness or completeness.

This readme describes the GeoNames Postal Code dataset.
The main GeoNames gazetteer data extract is here: http://download.geonames.org/export/dump/


For many countries lat/lng are determined with an algorithm that searches the place names in the main geonames database 
using administrative divisions and numerical vicinity of the postal codes as factors in the disambiguation of place names. 
For postal codes and place name for which no corresponding toponym in the main geonames database could be found an average 
lat/lng of 'neighbouring' postal codes is calculated.
Please let us know if you find any errors in the data set. Thanks

For Canada we have only the first letters of the full postal codes (for copyright reasons)

For Ireland we have only the first letters of the full postal codes (for copyright reasons)

For Malta we have only the first letters of the full postal codes (for copyright reasons)

The Argentina data file contains 4-digit postal codes which were replaced with a new system in 1999.

For Brazil only major postal codes are available (only the codes ending with -000 and the major code per municipality).

The data format is tab-delimited text in utf8 encoding, with the following fields :

country code      : iso country code, 2 characters
postal code       : varchar(20)
place name        : varchar(180)
admin name1       : 1. order subdivision (state) varchar(100)
admin code1       : 1. order subdivision (state) varchar(20)
admin name2       : 2. order subdivision (county/province) varchar(100)
admin code2       : 2. order subdivision (county/province) varchar(20)
admin name3       : 3. order subdivision (community) varchar(100)
admin code3       : 3. order subdivision (community) varchar(20)
latitude          : estimated latitude (wgs84)
longitude         : estimated longitude (wgs84)
accuracy          : accuracy of lat/lng from 1=estimated to 6=centroid
