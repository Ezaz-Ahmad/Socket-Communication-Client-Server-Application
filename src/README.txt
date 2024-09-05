1. How to Compile/Execute the Server:

Compilation:
---------------
* Open your terminal/command line.
* Navigate to the directory where Server.java is located.x
* Compile the server using the following command:
   javac Server.java

Execution:
-----------
After compilation, run the server by specifying the port number:
  java Server 4500

The server will start listening for client requests on the specified port.


2. How to Compile/Execute the Client:

Compilation:
------------
* Open your terminal/command line.
* Navigate to the directory where Client.java is located.
* Compile the client using the following command:
javac Client.java

Execution:
----------
After compilation, need to run the client by specifying the server’s hostname (e.g localhost) and the same port number used for the server (e.g 4500):
   java Client localhost 4500


---------------------------------------------------------------------------------------------------------------------------------
**NOTE**: In order to run the program, the capitals.csv file must be placed in the same directory as Server.java and Client.java
---------------------------------------------------------------------------------------------------------------------------------

3. The Protocol Used by the Client and Server to Communicate:

The client and server communicate using a text-based protocol over TCP sockets.

* Capital City Lookup:
Client sends: Capital:<country_name>
Server responds with the capital of the specified country, or an error if the country is not found.

* Population Estimate:
Client sends: Population:<country_name>
Server responds with a simulated population estimate based on the country.

* Add a New Country and Capital:
Client sends: Add:<country_name>,<capital_city>

Server responds with a success message if the country-capital pair is added, or an error if the country already exists.
