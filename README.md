# p2p-chat-RMI-java
Simple GUI chat program using java RMI

This is a simple java project to create Chat program with GUI

- Run serverDriver first to create the server on registry
- Then run nodeDriver to create a node(client) to connect to the server

The server has implement some basic features:
-GUI
-RMI communicate
-Unicast, broadcast messagings
- Vector time stamp
- Voting leader which implements Chang-Robert algorithm (distributed algorithm)

It's has some issue:
-when new node connects to server, the GUI cannot update correctly onlines
- It's better if server and client has a processing queue and designed with observer pattern
