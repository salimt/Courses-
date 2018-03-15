# -*- coding: utf-8 -*-
"""
@author: salimt
"""

class Node(object):
    def __init__(self, name):
        """Assumes name is a string"""
        self.name = name
    def getName(self):
        return self.name
    def __str__(self):
        return self.name

class Edge(object):
    def __init__(self, src, dest):
        """Assumes src and dest are nodes"""
        self.src = src
        self.dest = dest
    def getSource(self):
        return self.src
    def getDestination(self):
        return self.dest
    def __str__(self):
        return self.src.getName() + '->' + self.dest.getName()
               
class Digraph(object):
    """edges is a dict mapping each node to a list of
    its children"""
    def __init__(self):
        self.edges = {}
    def addNode(self, node):
        if node in self.edges:
            raise ValueError('Duplicate node')
        else:
            self.edges[node] = []
    def addEdge(self, edge):
        src = edge.getSource()
        dest = edge.getDestination()
        if not (src in self.edges and dest in self.edges):
            raise ValueError('Node not in graph')
        self.edges[src].append(dest)
    def childrenOf(self, node):
        return self.edges[node]
    def hasNode(self, node):
        return node in self.edges
    def getNode(self, name):
        for n in self.edges:
            if n.getName() == name:
                return n
        raise NameError(name)
    def __str__(self):
        result = ''
        for src in self.edges:
            for dest in self.edges[src]:
                result = result + src.getName() + '->'\
                         + dest.getName() + '\n'
        return result[:-1] #omit final newline

class Graph(Digraph):
    def addEdge(self, edge):
        Digraph.addEdge(self, edge)
        rev = Edge(edge.getDestination(), edge.getSource())
        Digraph.addEdge(self, rev)

# For example, an acceptable permutation (edge) 
# is between "ABC" and "ACB" but not between "ABC" and "CAB".

nodes = []
nodes.append(Node("ABC")) # nodes[0]
nodes.append(Node("ACB")) # nodes[1]
nodes.append(Node("BAC")) # nodes[2]
nodes.append(Node("BCA")) # nodes[3]
nodes.append(Node("CAB")) # nodes[4]
nodes.append(Node("CBA")) # nodes[5]

g = Graph()
for n in nodes:
    g.addNode(n)

n = len(nodes)
for node in range(n-1):
    currentNode = Node.getName(nodes[node])
    for item in range(node+1, n):
        nextNode = Node.getName(nodes[item])
#        print(currentNode, nextNode)
        if currentNode == nextNode: raise ValueError ('Same Value Exists!')
        if currentNode[0] == nextNode[0] and currentNode[1] == nextNode[2] \
                                         and currentNode[2] == nextNode[1]:
            g.addEdge(Edge(g.getNode(currentNode), g.getNode(nextNode)))
        if currentNode[0] == nextNode[1] and currentNode[1] == nextNode[0] \
                                         and currentNode[2] == nextNode[2]:
            g.addEdge(Edge(g.getNode(currentNode), g.getNode(nextNode)))                          
print(g)
            
        