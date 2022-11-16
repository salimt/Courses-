
# https://www.pg4e.com/code/pokeapi.py
# https://www.pg4e.com/code/myutils.py

# If needed:
# https://www.pg4e.com/code/hidden-dist.py
# copy hidden-dist.py to hidden.py
# edit hidden.py and put in your credentials

# python3 pokeapi.py
# Pulls data from the pokeapi.py4e.com API and puts it into our pokeapi table

import psycopg2
import hidden
import myutils
import requests
import json


# Load the secrets
secrets = hidden.secrets()

conn = psycopg2.connect(host=secrets['host'],
        port=secrets['port'],
        database=secrets['database'],
        user=secrets['user'],
        password=secrets['pass'],
        connect_timeout=3)

cur = conn.cursor()

print('If you want to restart the spider, run')
print('DROP TABLE IF EXISTS pokeapi CASCADE;')
print(' ')

sql = '''
CREATE TABLE IF NOT EXISTS pokeapi (id serial, body JSONB);
'''
print(sql)
cur.execute(sql)


for obj in range(1,101):

    url = f'https://pokeapi.co/api/v2/pokemon/{obj}/'
    print('=== Url is', url)
    response = requests.get(url)
    text = response.text
    js = json.loads(text)
    #print('=== Text is', text)
    #print("fqq" , js)
    #sql = 'UPDATE pokeapi SET body=%s;'
    sql = "INSERT INTO pokeapi (body) VALUES (%s)";
    cur.execute(sql, (text,))



    
print('Closing database connection...')
conn.commit()
cur.close()
