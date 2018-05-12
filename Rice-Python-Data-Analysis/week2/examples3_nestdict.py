"""
Tabular data as nested dictionaries.
"""

# Top 10 software products with the most vulnerabilities in 2017
# (through August).  From www.cvedetails.com.
vulnerabilities2017 = {
    'Android': {'vendor': 'Google',
                'type': 'Operating System',
                'number': 564},
    'Linux Kernel': {'vendor': 'Linux',
                     'type': 'Operating System',
                     'number': 367},
    'Imagemagick': {'vendor': 'Imagemagick',
                    'type': 'Application',
                    'number': 307},
    'IPhone OS': {'vendor': 'Apple',
                  'type': 'Operating System',
                  'number': 290},
    'Mac OS X': {'vendor': 'Apple',
                 'type': 'Operating System',
                 'number': 210},
    'Windows 10': {'vendor': 'Microsoft',
                   'type': 'Operating System',
                   'number': 195},
    'Windows Server 2008': {'vendor': 'Microsoft',
                            'type': 'Operating System',
                            'number': 187},
    'Windows Server 2016': {'vendor': 'Microsoft',
                            'type': 'Operating System',
                            'number': 183},
    'Windows Server 2012': {'vendor': 'Microsoft',
                            'type': 'Operating System',
                            'number': 176},
    'Windows 7': {'vendor': 'Microsoft',
                  'type': 'Operating System',
                  'number': 174}
}

# Display vulnerabilities table
print("Product               Vendor        Type               Vulnerabilities")
print("----------------------------------------------------------------------")

for product, values in vulnerabilities2017.items():
    row = "{:21} {:13} {:18} {:8}".format(product, values['vendor'], values['type'], values['number'])
    print(row)

print("")

# Finding/selecting items

# How many vulnerabilites does Windows 7 have?
print(vulnerabilities2017['Windows 7']['number'])

# What product had the most vulnerabilities?
maxproduct = None
maxnumber = -1

for product, values in vulnerabilities2017.items():
    if values['number'] > maxnumber:
        maxproduct = product
        maxnumber = values['number']

print(maxproduct, maxnumber)
