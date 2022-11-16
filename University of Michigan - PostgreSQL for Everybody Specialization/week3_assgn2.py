# To check the results, use psql and look at the
# pythonfun table

import psycopg2
import hidden

# Load the secrets
secrets = hidden.secrets()

conn = psycopg2.connect(host=secrets['host'],
        port=secrets['port'],
        database=secrets['database'],
        user=secrets['user'],
        password=secrets['pass'],
        connect_timeout=3)

cur = conn.cursor()

value = 632340
for i in range(300) :
    print(i+1, value)
    sql = 'INSERT INTO pythonseq (iter, val) VALUES (%s, %s);'
    cur.execute(sql, (i+1, value, ))
    value = int((value * 22) / 7) % 1000000




conn.commit()
cur.close()
