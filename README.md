# Ex3-TheMazeOfWazeGame-java


This project Dealing with the subject directed graphs and contains the classes NodeData, EdgeData, DGraph, Graph_Algo and Graph_GUI. In addition in this project you can actually play a game that implements by class MyGameGUI and even play an automatic game with the help of the auto class In addition, the graph, robots and fruits can be exported to KML file with help of the class KML_Logger.

The class NodeData represents the points in the graph and The class EdgeData represents the edges in the graph.

The Class DGraph that implements the interface graph and represents a deliberate weighted graph.

The class Graph_Algo that implements the interface Graph_Algorithms and represents a collection of algorithms on directedand and weighted graphs.

The class Graph_GUI that extends JFrame represent graphic interface. in this class you can display a graph, save it, run algorithms on it, and display their results.

The class MyGameGUI implements runnable and start a game by choose a random scenario number and allows you to view the selected graph with the number of robots that existing in the scenario, in each scenario has a different playing time. In the game you need to collect fruits that exist on certain points in the graph. At the end of the game you can see the amount of points that each robot has accumulated.

The class auto implements Runnable let you to choose a scenario number and start a automatic game. This class will place the robots most efficiently in relation to the fruits located on the graph and start the game automatically as efficiently as possible. all this with help of the algorithms in the class Graph_Algo.

The class KML_Logger allows export of the graph, robots and fruits to KML File and displays the Robots movement and eating fruits.

On WIKI you can read more detail about all the functions and algorithms that are within the above classs and also explanations about the data structure,display system and how to use the project,download and run. Of course you can read there all the details about the functions and about the progress of the game also about the manual game and automatic game.

This image is for demonstration purposes. it is example of scenario number 15 and there we have 1 robot.

See you on Wiki!
