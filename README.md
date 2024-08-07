This repository contains a Java-based project demonstrating network communication using sockets, featuring a server and client application.

Technologies Used:

Java
TCP Sockets
Concepts Covered
Network communication
Client-server architecture
Basic error handling
Server Program

The server program provides the following functionalities:

Capital City Information: Given a country name, the server responds with the capital city.
Population Estimate: Given a country name, the server responds with an estimated population (simulated).
Add Country-Capital Pair: Allows temporary addition of new country-capital pairs.
The server port is configurable via command-line parameters.

Client Program
The client program provides the following functionalities:

Request Capital City: Asks the user for a country and retrieves the capital city from the server.
Request Population Estimate: Asks the user for a country and retrieves the population estimate from the server.
Add Country-Capital Pair: Allows the user to add a new country and capital city pair to the server.
The client connects to the server using a configurable hostname and TCP port via command-line parameters.

Features
Basic CLI for user interaction
Error handling and validation for both server and client inputs
