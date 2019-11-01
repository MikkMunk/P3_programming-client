# P3_programming-client

This is the client side of MED3 PCSS miniproject of group 301.

The server side can be found here: https://github.com/latedude2/P3_programming-server

Other information regarding the game and the server can be found in the Wiki of the repository, including:
  - Rules of the original "Codenames" game
  - How to play the game
  - Class diagram for the client side
  
Other info can be found in the wiki of the server, including:
  - Sequence and use case diagrams, and the class diagram for the server
  - Principles and souce used for word generation

The rules and instructions on how to play the game can also be found on the server side's wiki.



Instructions how to download and run the game:

- It is useful to read the general game rules in the wiki to familiarize with the "Codenames" game concept.
- One person has to download and host the server, whereas 4 people are required to use the clients. One of the players can both host the server, and the client, if necessary.
- The players involved (both server and clients) must be connected to the same network as the server.
- The clients must acquire the the IP address of the host computer and insert it on the Client side (when creating the Socket in line 29 of the "Main" class).
- The server must be run first, and then clients join one by one. They are assigned teams accordingly (as seen in "How to play the game" in the Wiki).

Note; the game has to be run in IntelliJ for the UI to function properly.
