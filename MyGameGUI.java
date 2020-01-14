package gameClient;


import java.util.List;
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
import utils.StdDraw;

public class MyGameGUI {

	game_service game;
	graph gr;
	DGraph dgraph;
	Graph_GUI gui;
	ArrayList<Robot> rob=new ArrayList<Robot>();
	ArrayList<Fruit> fru=new ArrayList<Fruit>();
	
    public MyGameGUI(int g){

       game = Game_Server.getServer(g);
       String graph= game.getGraph();
       dgraph= new DGraph();
       dgraph.init(graph);
       gui = new Graph_GUI(dgraph);
       int numR=getnumR(game);
       addRobot(numR,game);
       
    

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
	public void addRobot(int numR,game_service game) {
		for (int i = 0; i < numR; i++) {
			game.addRobot(i);
		}
	}
	
	public void initRobot(game_service game) {
		
		ArrayList<String> r=new ArrayList<String>();
		r=(ArrayList<String>) game.getRobots();
		String RJ=r.toString();
		try {
			JSONArray j =new JSONArray(RJ);
			int i=0; 
			int c=0;
			while(i<j.length()) {
				String help="";
				JSONObject n=(JSONObject) j.get(i);
				String s=n.getString("pos");
				String p[]=new String[3];
				for (int k = 0; k < s.length(); k++) {
					if(s.charAt(k)!=',') {
						help=help+s.charAt(k);
					}
					if(s.charAt(k)==',') {
						p[c]=help; 
						c++;
						help="";
					}
				}
				p[c]=help; 
				double x=Double.parseDouble(p[0]);
				double y=Double.parseDouble(p[1]);
				double z=Double.parseDouble(p[2]);
				Point3D p1=new Point3D(x,y,z);
				int id=n.getInt("id");
				double value=n.getDouble("value");
				int src=n.getInt("src");
				int dest=n.getInt("dest");
				double speed=n.getDouble("speed");
				Robot ro=new Robot(src,p1,id,dest,value,speed);
				
				rob.add(ro);
				i++;
			}
		
		
		}catch(Exception error) {
			error.printStackTrace();

			System.out.println("catch");
		
	}
	for (Robot r1: rob) {
		
	}
	
	}

	public void initFruit(game_service game) {
		ArrayList<String> f=new ArrayList<String>();
		f=(ArrayList<String>) game.getFruits();
		String FJ=f.toString(); 
		
		try {
			JSONArray j =new JSONArray(FJ);
			int i=0; 
			int c=0;
			while(i<j.length()) {
				String help="";
				JSONObject n=(JSONObject) j.get(i);
				String s=n.getString("pos");
				String p[]=new String[3];
				for (int k = 0; k < s.length(); k++) {
					if(s.charAt(k)!=',') {
						help=help+s.charAt(k);
					}
					if(s.charAt(k)==',') {
						p[c]=help; 
						c++;
						help="";
					}
				}
				p[c]=help; 
				double x=Double.parseDouble(p[0]);
				double y=Double.parseDouble(p[1]);
				double z=Double.parseDouble(p[2]);
				Point3D p1=new Point3D(x,y,z);
				double value=n.getDouble("value");
				int type=n.getInt("type");
				Fruit fr=new Fruit(value,p1,type);
				fru.add(fr);
				i++;
		}
	
		}catch(Exception error) {
		error.printStackTrace();

		System.out.println("catch");
	
		}
	}
	
	public edge_data findFruit(Fruit f,graph g) {
		edge_data e=new EdgeData();
		Point3D p=f.p;
		Collection<node_data> nc = g.getV();
		for (node_data n: nc ) {
			Point3D p1=n.getLocation(); 
			Collection<edge_data> ec = g.getE(n.getKey());
			for (edge_data e1: ec) {
				Point3D p2 = g.getNode(e1.getDest()).getLocation();
				if(p.distance3D(p1)+p1.distance3D(p2)==p1.distance3D(p2))
					return e;
			}
			
		}
	return null;
	}
}

