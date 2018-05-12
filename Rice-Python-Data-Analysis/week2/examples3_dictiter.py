"""
Iterating over dictionaries.
"""

# Mapping from various cities to their country
capitals = {'USA': 'Washington, D.C.',
            'China': 'Beijing',
            'France': 'Paris',
            'England': 'London',
            'Italy': 'Rome',
            'Russia': 'Moscow',
            'Australia': 'Canberra',
            'Peru': 'Lima',
            'Japan': 'Tokyo'}

print("Direct Iteration")
print("================")

for country in capitals:
    print("{}, {}".format(capitals[country], country))

print("")

print("Iteration over Keys")
print("===================")

for country in capitals.keys():
    print("{}, {}".format(capitals[country], country))

print("")

print("Iteration over Values")
print("=====================")

for city in capitals.values():
    print("Capital city: {}".format(city))

print("")

print("Iteration over Items")
print("===================")

for country, city in capitals.items():
    print("{}, {}".format(city, country))

print("")

print("Checking Membership")
print("===================")

print('England' in capitals)
print('Lima' in capitals)

print('Moscow' in capitals.keys())
print('Italy' in capitals.keys())

print('Houston' in capitals.values())
print('Beijing' in capitals.values())

