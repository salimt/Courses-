# -*- coding: utf-8 -*-
"""
@author: salimt
"""

class Node:
    def __init__(self,data):
        self.data = data
        self.next = None 
        
class Solution: 
    def insert(self,head,data):
            p = Node(data)           
            if head==None:
                head=p
            elif head.next==None:
                head.next=p
            else:
                start=head
                while(start.next!=None):
                    start=start.next
                start.next=p
            return head  
        
    def display(self,head):
        current = head
        while current:
            print(current.data,end=' ')
            current = current.next
            
    def removeDuplicates(self, head):
        current = head
        while current.next:
            if current.data == current.next.data:
                current.next = current.next.next
            else:
                current = current.next
        return head
        
#        myList = []
#        while head:
#            if head.data not in myList:
#                myList.append(head.data)  
#            head = head.next
#        print(*myList, sep=' ')
        
      
mylist= Solution()
T= [1,2,2,3,3,4]
head=None
for i in T:
    data=int(i)
    head=mylist.insert(head,data) 
head=mylist.removeDuplicates(head)
mylist.display(head)