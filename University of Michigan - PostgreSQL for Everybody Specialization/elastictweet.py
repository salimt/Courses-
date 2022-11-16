# https://www.pg4e.com/code/elastictweet.py

# Example from:
# https://elasticsearch-py.readthedocs.io/en/master/

# pip install 'elasticsearch<7.14.0'

# (If needed)
# https://www.pg4e.com/code/hidden-dist.py
# copy hidden-dist.py to hidden.py
# edit hidden.py and put in your credentials

from datetime import datetime
from elasticsearch import Elasticsearch
from elasticsearch import RequestsHttpConnection

import hidden

secrets = hidden.elastic()

es = Elasticsearch(
    [secrets['host']],
    http_auth=(secrets['user'], secrets['pass']),
    url_prefix = secrets['prefix'],
    scheme=secrets['scheme'],
    port=secrets['port'],
    connection_class=RequestsHttpConnection,
)
indexname = secrets['user']

# Start fresh
# https://elasticsearch-py.readthedocs.io/en/master/api.html#indices
res = es.indices.delete(index=indexname, ignore=[400, 404])
print("Dropped index")
print(res)

res = es.indices.create(index=indexname)
print("Created the index...")
print(res)

doc0 = {
    'author': 'kimchy',
    'type' : 'tweet',
    'text': 'Elasticsearch: cool. bonsai cool.',
    'timestamp': datetime.now(),
}

doc1 = {
    'author': 'kimchy',
    'type' : 'tweet',
    'text': 'even when there is no power to the computer Examples of secondary',
    'timestamp': datetime.now(),
}

doc2 = {
    'author': 'kimchy',
    'type' : 'tweet',
    'text': 'memory are disk drives or flash memory typically found in USB',
    'timestamp': datetime.now(),
}


doc3 = {
    'author': 'kimchy',
    'type' : 'tweet',
    'text': 'keyboard mouse microphone speaker touchpad etc They are all of',
    'timestamp': datetime.now(),
}

doc4 = {
    'author': 'kimchy',
    'type' : 'tweet',
    'text': 'the ways we interact with the computer',
    'timestamp': datetime.now(),
}

count = 0
for i in [doc0, doc1, doc2, doc3, doc4]:

    # Note - you can't change the key type after you start indexing documents
    res = es.index(index=indexname, id='abc'+str(count), body=i, doc_type="None")
    print('Added document...')
    print(res['result'])

    res = es.get(index=indexname, id='abc'+str(count), doc_type="None")
    print('Retrieved document...')
    print(res)
    count += 1

# Tell it to recompute the index - normally it would take up to 30 seconds
# Refresh can be costly - we do it here for demo purposes
# https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-refresh.html
res = es.indices.refresh(index=indexname)
print("Index refreshed")
print(res)

# Read the documents with a search term
# https://www.elastic.co/guide/en/elasticsearch/reference/current/query-filter-context.html
x = {
  "query": {
    "bool": {
      "must": {
        "match": {
          "text": "bonsai"
        }
      },
      "filter": {
        "match": {
          "type": "tweet"
        }
      }
    }
  }
}

res = es.search(index=indexname, body=x)
print('Search results...')
print(res)
print()
print("Got %d Hits:" % len(res['hits']['hits']))
for hit in res['hits']['hits']:
    s = hit['_source']
    print(f"{s['timestamp']} {s['author']}: {s['text']}")


