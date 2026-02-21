Peter Bergen
pbergen2@u.rochester.edu

Synopsis of how my program works:
One of the most important parts of the entire program is Graph.java, which is what actually implement's Dijkstra's algorithm. It stores streets and intersections from the input file and adds nodes and edges from the parsed input. In order to implement Dijkstra's I needed to use a min heap for the priority queue, which is custom built in MinHeap.java. Node.java and Edge.java 'assist' Graph.java by storing intersection ID, Latitude/longitude (which is very important and something I had a lot of issues with), road ID, starting/ending nodes, amongst other functions. MyHashMap is the other custom Data structure I built, which maps intersection IDs to Node objects and has constant runtime. MapPanel.java is responsible for the graphics, using Java Swing to paint the map. It also scales the map automatically when the user resizes the window. StreetMap.java is the singular most important source code file. It handles command line argument parsing, file reading (before data is sent to other files it passes through here), launching the display window, printing the directions & mileage portion of the output, amongst other things as well. Having MyHashMap be constant runtime is probably the most important thing for handling large datasets like nys.txt. 

The largest obstacle I came across during this project was an issue with my StreetMap.java file which resulted in the graph on the display being mushed to one side. Nys.txt's graph was distinguishable, albeit small, but smaller datasets were completely undistinguishable. The issue ended up being with how the latitude/longitude were programmed. As you can see in my final submission the lat/long stuff is in Graph.java, which is the result of me completely redoing this portion and placing it in Graph.java, pretty much breaking up StreetMap.java into more isolated files to prevent clutter. Originally my StreetMap.java was over 300 lines. 

List of files in submission:
StreetMap.java
Graph.java
Node.java
Edge.java 
MyHashMap.java
MinHeap.java
MapPanel.java
(Proj3)README.txt (this file)

To compile javac *.java

To run, do java StreetMap ur.txt --show --directions HOYT MOREY
Obviously this is an example, different input files can be substituted as well as different street names. You can also do just --show or just --directions without the other if you choose, as they are implemented separately. 
