package gameClient;


import java.util.List;

import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.EdgeData;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import gui.Graph_GUI;
import utils.Point3D;
import utils.Range;
import utils.StdDraw;

public class MyGameGUI implements Runnable {
	
	 Range rx, ry;
	 static double X = 0;
	 static double Y = 0;
	 final static double epsilon = 0.0000001;
	 final static double epsilon2 = 0.00019;
	 static boolean KMLbool = false;
	 static String KML_file_name;
	 private static int numgame;
	 game_service game;
	graph gr;
	DGraph dgraph;
	Graph_GUI gui;
	ArrayList<Robot> rob;
	ArrayList<Fruit> fru;
	
    public MyGameGUI(int g){

       game = Game_Server.getServer(g);
       numgame=g;
       String graph= game.getGraph();
       dgraph= new DGraph();
       dgraph.init(graph);
       gui = new Graph_GUI(dgraph);
       rx = FindXmaxmin(dgraph);
       ry = FindYmaxmin(dgraph);
       
       Thread t=new Thread(this);
       t.start();

    }
	public MyGameGUI() {
		//Selecting a random scenario number between 0-23
			int scenarioNumber=(int)(Math.random()*23);
			numgame=scenarioNumber;
			System.out.println("The scenario number that choosen is "+scenarioNumber);
			game = Game_Server.getServer(scenarioNumber);

			//Preparing graph to be like the graph on scenario Number in server
			dgraph = new DGraph();
			dgraph.init(game.getGraph());
			
			//scale x and y
			rx = FindXmaxmin(dgraph);
			ry = FindYmaxmin(dgraph);
			
			//thread for reload game //play for run function
			 Thread t=new Thread(this);
	         t.start();	
	}
    
    @Override
	public void run() 
	{
		drawGraph(dgraph);
		int numR=getnumR(game);
		addRobot1(numR,game); 
		initTheGameForFruit(game);
		initTheGameForRobot(game); 
		int ans=JOptionPane.showConfirmDialog(null, "you want KML file??", "message", JOptionPane.YES_OPTION);
		if(ans == 0) 
		{
			
			String input = JOptionPane.showInputDialog("enter your name file");
			if(input != null && input != "")
				startKML(input);

		}
		int press=JOptionPane.showConfirmDialog(null, "The scenario number is choosen, You want to start ?");
     	if(press!=0) System.exit(0); //if the user press NO or CANCEL, stop the game.
     	
		game.startGame();
		
		//show time
		StdDraw.setPenRadius(15);
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.text( rx.get_max()-0.027, ry.get_max()-0.0005,"End game in:"+game.timeToEnd()/1000);
		double oldX = X;
		double oldY = Y;
		
		while(game.isRunning()) {
		
			
			if(X != oldX && Y != oldY) {
			
				node_data dest = searchForDest(X, Y);
				Robot r = CloseRobot(dest);
				game.chooseNextEdge(r.getId(), dest.getKey());
				oldX = X;
				oldY = Y;
			}
			game.move();
			refreshGame();
			try {
			
				Thread.sleep(100);
			} 
			catch (InterruptedException e) {
			
				e.printStackTrace();
			}
		}
		if(KMLbool) {

			try {
				KML_Logger.closeFile(KML_file_name);

			}catch (IOException e) {

				e.printStackTrace();

			}

		}
		//show the score of all robots.
		String message = "";
		int i = 1;
		for(Robot r : rob)
			message += "robot " + (i++) + " score: " + r.value +"\n";
		JOptionPane.showMessageDialog(null, message);
		System.exit(0);

	}
	

	/*
	 * Checks and return how many robots we need in the game.
	 */
	public int getnumR(game_service game) {
		int rn=0;
		String g=game.toString();
		JSONObject l;
		try {
		l=new JSONObject(g);
		JSONObject t = l.getJSONObject("GameServer");
		rn=t.getInt("robots");
		}
		catch (JSONException e) {e.printStackTrace();}
	return rn;
	}
	
	/*
	 * Adds robots to the graph (random).
	 */
	public void addRobot1(int numR,game_service game) {
		for (int i = 0; i < numR; i++) {
			game.addRobot(i);
		}
	}
	
	/*
	 * Initial builds and draws the robots.
	 */
	public void initTheGameForRobot(game_service game) {
		
		rob=new ArrayList<>();
		List<String> r=game.getRobots();
		String RJ=r.toString();
		try {
			JSONArray j =new JSONArray(RJ);
			int i=0; 
			int c=0;
			while(i<j.length()) {
				String help="";
				JSONObject n=(JSONObject) j.get(i);
				JSONObject jr = n.getJSONObject("Robot");
				String s=jr.getString("pos");
				String p[]=new String[3];
				for (int k = 0; k < s.length(); k++) {
					if(s.charAt(k)!=',') {
						help=help+s.charAt(k);
					}
					if(c<3&&s.charAt(k)==',') {
						p[c]=help; 
						c++;
						help="";
					}
				}
				p[c]=help; 
				c=0;
				double x=Double.parseDouble(p[0]);
				double y=Double.parseDouble(p[1]);
				double z=Double.parseDouble(p[2]);
				Point3D p1=new Point3D(x,y,z);
				int id=jr.getInt("id");
				double value=jr.getDouble("value");
				int src=jr.getInt("src");
				int dest=jr.getInt("dest");
				double speed=jr.getDouble("speed");
				Robot ro=new Robot(src,p1,id,dest,value,speed);
				rob.add(ro);
				i++;
			
				if(KMLbool) {

					try {
						KML_Logger.write(KML_file_name, ro.p.x(), ro.p.y(), "Robot", game.timeToEnd());

					} catch (FileNotFoundException e) {
						e.printStackTrace();

					}

				}
			
			}
		
		
		}catch(Exception error) {
			error.printStackTrace();

			System.out.println("catch");
		
	}
		int n=1;
		for (Robot r1: rob) {
			
			StdDraw.setPenRadius(0.030);
		StdDraw.setPenColor(Color.black);
		StdDraw.point(r1.p.x(), r1.p.y());

		StdDraw.text(rx.get_max() - 0.001 - 0.0075*n, ry.get_max()-0.0005, 
				"robot "+ (n++) + " score: " + r1.value);

	}
	
	}
	/*
	 * Initial builds and draws the fruits.
	 */
	public void initTheGameForFruit(game_service game) {
		
		fru=new ArrayList<Fruit>();
		List<String> f= game.getFruits();
		String FJ=f.toString(); 
		
		try {
			JSONArray j =new JSONArray(FJ);
			int i=0; 
			int c=0;
			while(i<j.length()) {
				String help="";
				JSONObject n=(JSONObject) j.get(i);
				JSONObject jf = n.getJSONObject("Fruit");
				String s=jf.getString("pos");
				String p[]=new String[3];
				for (int k = 0; k < s.length(); k++) {
					if(s.charAt(k)!=',') {
						help=help+s.charAt(k);
					}
					if(c<3&&s.charAt(k)==',') {
						p[c]=help; 
						c++;
						help="";
					}
				}
				p[c]=help; 
				c=0;
				double x=Double.parseDouble(p[0]);
				double y=Double.parseDouble(p[1]);
				double z=Double.parseDouble(p[2]);
				Point3D p1=new Point3D(x,y,z);
				double value=jf.getDouble("value");
				int type=jf.getInt("type");
				Fruit fr=new Fruit(value,p1,type,findEdgeFruit(p1,type));
				fru.add(fr);
				i++;
		
				if(KMLbool) {
					
					String objType = "";
						if(fr.type == 1)
							objType="Apple";
						else 
							objType="Banana";
				
					try {
					KML_Logger.write(KML_file_name, fr.p.x(), fr.p.y(), objType, game.timeToEnd());
				
					} catch (FileNotFoundException e) {
						 
						e.printStackTrace();
							
					}
				
				}
			
			}
	
		}catch(Exception error) {
		error.printStackTrace();

		System.out.println("catch");
	
		}
		
		for (Fruit f1: fru) {
			String fruit_icon = ""; 
			if(f1.type==1)
				fruit_icon="./apple.png";
			if(f1.type==-1)
				fruit_icon="./banana.png";
			
		
			StdDraw.picture(f1.p.x(), f1.p.y(), fruit_icon);
		}
	}
	
	/*
	 * find where put the fruit on the graph.
	 */
	public edge_data findEdgeFruit(Point3D pf,int type) {
		edge_data e=new EdgeData();
		
		Collection<node_data> nc = dgraph.getV();
		for (node_data n: nc ) {
			Point3D p1=n.getLocation(); 
			Collection<edge_data> ec = dgraph.getE(n.getKey());
			for (edge_data e1: ec) {
				Point3D p2 = dgraph.getNode(e1.getDest()).getLocation();
				if(pf.distance3D(p1)+pf.distance3D(p2)-p1.distance3D(p2)<epsilon)
					return e1;
			}
			
		}
	return e;
	}

	/**
	 * find the values of x (min and max).
	 * */
	public Range FindXmaxmin(graph g) {
		
		double xmin=Double.MAX_VALUE; 
		double xmax=Double.MIN_VALUE; 
		Collection<node_data> nc = g.getV();
		for(node_data n: nc) {
			
			double px=n.getLocation().x();
			
			if(px>xmax) {
				xmax=px;
			}
		
			if(px<xmin) {
				xmin=px;
			}
		}
	
		return new Range(xmin-0.001,xmax+0.001);
	}
	/**
	 * find the values of y (min and max).
	 * */
	public Range FindYmaxmin(graph g) {
		
		double ymin=Double.MAX_VALUE; 
		double ymax=Double.MIN_VALUE; 
		Collection<node_data> nc = g.getV();
		for(node_data n: nc) {
			
			double py=n.getLocation().y();
			
			if(py>ymax) {
				ymax=py;
			}
		
			if(py<ymin) {
				ymin=py;
			}
		}
		
		return new Range(ymin-0.001,ymax+0.001);
	}
	/*
	 * draw the graph
	 */

	public void drawGraph(graph G)
	{
		StdDraw.setXscale(rx.get_min(),rx.get_max());
		StdDraw.setYscale(ry.get_min(),ry.get_max());
		StdDraw.setPenColor(Color.BLACK);
		for(node_data myNode:G.getV())
		{
			double x0=myNode.getLocation().x();
			double y0=myNode.getLocation().y();
			if(G.getE(myNode.getKey())!=null)
			{
				for(edge_data edge:G.getE(myNode.getKey()))
				{
					//size and color pen
					StdDraw.setPenRadius(0.0039); 
					StdDraw.setPenColor(Color.BLACK);
				
					double x1=G.getNode(edge.getDest()).getLocation().x();
					double y1=G.getNode(edge.getDest()).getLocation().y();

					//edges
					StdDraw.line(x0, y0, x1, y1);
					StdDraw.setPenRadius(0.02);

					//direction 
					StdDraw.setPenColor(Color.orange);
					StdDraw.point(x0*0.1+x1*0.9, y0*0.1+y1*0.9);

					//draw nodes
					StdDraw.setPenColor(Color.BLUE);
					StdDraw.point(x1, y1);

					//nodes key
					StdDraw.setPenColor(Color.BLACK);
					StdDraw.text(x0,y0 + epsilon, myNode.getKey()+"");

				
				}
			}
			StdDraw.setPenColor(Color.BLUE);
			StdDraw.point(x0, y0);
		}
	}
	/*
	 * find the closest robot to the node.
	 */
	public Robot CloseRobot(node_data d) {
	
		
		Robot ans=new Robot(); 
		boolean first = true;
		for(Robot r: rob) {
			
			if(first)
			{
				ans = r;
				first = false;
			}
		else 
		{
			double old1 = ans.getP().distance2D(d.getLocation());
			double new1 = r.getP().distance2D(d.getLocation());
			
			if(new1 < old1)
				ans = r;
			}
		}
		return ans;
		
	}
	
	/**
	 * find the closest node to user click
	 * */
	private node_data searchForDest(double mouseX, double mouseY) 
	{
		Point3D p = new Point3D(mouseX, mouseY);
		node_data closest = null;
		boolean first = true;
		for(node_data v: dgraph.getV())
		{
			if(first)
			{
				closest = v;
				first = false;
			}
			else
			{
				double oldDiff = closest.getLocation().distance2D(p);
				double newDiff = v.getLocation().distance2D(p);
				if(newDiff < oldDiff )
					closest = v;
			}
		}
		return closest;
	}

	/**
	 * refresh the frame all the time that the game is running
	 * */
	public void refreshGame()
	{
		StdDraw.enableDoubleBuffering();
		StdDraw.clear();
		StdDraw.setScale();
		StdDraw.picture(0.5, 0.5, "map.png");
		drawGraph(dgraph);
		initTheGameForFruit(game);
		initTheGameForRobot(game);
		StdDraw.setPenColor(Color.BLUE);
		StdDraw.text(rx.get_max()-0.002, ry.get_max()-0.0005,"time to end: "+ game.timeToEnd() / 1000);
		StdDraw.show();
	}
	/*
	 * update the values of user click
	 */
public static void updateXY(double mouseX, double mouseY) {

	X = mouseX;
	Y = mouseY;

}
public static void startKML(String file_name) {

	if(!file_name.endsWith(".kml") && !file_name.endsWith(".KML"))
		file_name += ".kml";
		KML_file_name = KML_Logger.createFile(file_name, numgame);
		KMLbool = true;
}

/**

 * @return true if we already writing to KML file.

 * */

public static boolean KMLbool() {

	return KMLbool;

}
}

