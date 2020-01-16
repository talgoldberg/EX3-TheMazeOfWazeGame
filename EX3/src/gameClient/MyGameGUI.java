package gameClient;


import java.util.List;

import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;

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
	
	game_service game;
	graph gr;
	DGraph dgraph;
	Graph_GUI gui;
	ArrayList<Robot> rob;
	ArrayList<Fruit> fru;
	
    public MyGameGUI(int g){

       game = Game_Server.getServer(g);
       String graph= game.getGraph();
       dgraph= new DGraph();
       dgraph.init(graph);
       gui = new Graph_GUI(dgraph);
       rx = FindXmaxmin(dgraph);
       ry = FindYmaxmin(dgraph);
       
       Thread t=new Thread(this);
       t.start();

    }
    @Override
	public void run() 
	{
		drawGraph(dgraph); //draw the graph
		 //Checks how many robots in the game
		int numR=getnumR(game);
		addRobot1(numR,game); //add all the robots on scenario nodes 
		 initTheGameForFruit(game);
		 initTheGameForRobot(game); //להחליף ב init 
		 
		
	
		
		if(JOptionPane.showConfirmDialog(null, "press YES to start the game", "TheMaze of Waze", JOptionPane.YES_OPTION) != JOptionPane.YES_OPTION)
		System.exit(0);
		game.startGame();
		StdDraw.setFont();
		StdDraw.setPenColor(Color.BLUE);
		StdDraw.text( rx.get_max()-0.002, ry.get_max()-0.0005,"time to end: "+game.timeToEnd()/1000);
		double oldX = X;
		double oldY = Y;
		
		while(game.isRunning())
		{
			
			if(X != oldX && Y != oldY)
			{
				node_data dest = searchForDest(X, Y);
				Robot r = CloseRobot(dest);
				game.chooseNextEdge(r.getId(), dest.getKey());
				oldX = X;
				oldY = Y;
			}
			game.move();
			RefreshFrame();
			try 
			{
				Thread.sleep(100);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		String message = "";
		int i = 1;
		for(Robot r : rob)
			message += "robot " + (i++) + " score: " + r.value +"\n";
		JOptionPane.showMessageDialog(null, message);
		System.exit(0);

	}
	
	public MyGameGUI() {
		
	}
	
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
	
	
	public void addRobot1(int numR,game_service game) {
		for (int i = 0; i < numR; i++) {
			game.addRobot(i);
		}
	}
	
	
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
		//StdDraw.setPenColor(242, 19, 227);
		StdDraw.text(rx.get_max() - 0.001 - 0.0075*n, ry.get_max()-0.0005, 
				"robot "+ (n++) + " score: " + r1.value);
	}
	
	}

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
				Fruit fr=new Fruit(value,p1,type);
				fru.add(fr);
				i++;
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
	
	
	public edge_data findEdgeFruit(Point3D pf,DGraph d) {
		edge_data e=new EdgeData();
		
		Collection<node_data> nc = d.getV();
		for (node_data n: nc ) {
			Point3D p1=n.getLocation(); 
			Collection<edge_data> ec = d.getE(n.getKey());
			for (edge_data e1: ec) {
				Point3D p2 = d.getNode(e1.getDest()).getLocation();
				if(pf.distance3D(p1)+pf.distance3D(p2)==p1.distance3D(p2))
					return e;
			}
			
		}
	return null;
	}

	
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
	/**
	 * iterate over all vertices in given graph to find min and max y values
	 * */

	public void drawGraph(graph G)
	{
		StdDraw.setXscale(rx.get_min(),rx.get_max());
		StdDraw.setYscale(ry.get_min(),ry.get_max());
		StdDraw.setPenColor(Color.BLACK);
		for(node_data vertex:G.getV())
		{
			double x0=vertex.getLocation().x();
			double y0=vertex.getLocation().y();
			if(G.getE(vertex.getKey())!=null)
			{
				for(edge_data edge:G.getE(vertex.getKey()))
				{
					StdDraw.setPenRadius(0.0015);
					StdDraw.setPenColor(Color.orange);
					Font f=new Font("BOLD", Font.ITALIC, 18);
					StdDraw.setFont(f);
					double x1=G.getNode(edge.getDest()).getLocation().x();
					double y1=G.getNode(edge.getDest()).getLocation().y();

					//draw edges
					StdDraw.line(x0, y0, x1, y1);
					StdDraw.setPenRadius(0.02);

					//draw direction points
					StdDraw.setPenColor(Color.GREEN);
					StdDraw.point(x0*0.1+x1*0.9, y0*0.1+y1*0.9);

					//draw dest vertex
					StdDraw.setPenColor(Color.RED);
					StdDraw.point(x1, y1);

					//draw vertices weights
					StdDraw.setPenColor(Color.BLACK);
					StdDraw.text(x0,y0 + epsilon2, vertex.getKey()+"");

					//draw edges weight
					//					StdDraw.setPenColor(Color.BLACK);
					//					StdDraw.text((x0+x1)/2, (y0+y1)/2,edge.getWeight()+"");
				}
			}
			StdDraw.setPenRadius(0.02);
			StdDraw.setPenColor(Color.RED);
			StdDraw.point(x0, y0);
		}
	}

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
	 * iterate over all vertices to find the closest to user`s click
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

	
	public void RefreshFrame()
	{
		StdDraw.enableDoubleBuffering();
		StdDraw.clear();
		StdDraw.setScale();
		StdDraw.picture(0.5, 0.5, "map.png");
		drawGraph(dgraph);
		initTheGameForFruit(game);
		initTheGameForRobot(game);

		StdDraw.setPenColor(Color.BLUE);
		//Font f=new Font("BOLD", Font.ITALIC, 18);
		//StdDraw.setFont(f);
		StdDraw.text(rx.get_max()-0.002, ry.get_max()-0.0005,"time to end: "+ game.timeToEnd() / 1000);
		StdDraw.show();
	}

public static void updateXY(double mouseX, double mouseY) 

{

	X = mouseX;

	Y = mouseY;

}
	
}

