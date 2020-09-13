# VendingMachine
A simple application to process vending machine transactions

A very rudimentary project I completed for Tech Elevator. 

This program reads in a text file and generates a LinkedHashMap based on its contents to set the vending machine inventory.

The CLI allows a user to add money, select items, and receive "change" from the machine.

The inventory reflects the total changes made while running. Everytime it is restarted, the inventory is reset to reflect the text file. 

Every transaction is recorded in an audit log using a PrintWriter.
