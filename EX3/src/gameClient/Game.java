package gameClient;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import Server.game_service;
import dataStructure.graph;
import utils.Point3D;

public class Game {

	graph gr;
	game_service game;
	
	public Game(graph gr,game_service game) {
		this.gr=gr; 
		this.game=game;
	}
	public Game() {
		
	}
	
	public void initRobot(game_service game) {
	
		ArrayList<String> r = (ArrayList<String>) game.getRobots();
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
				int value=n.getInt("value");
				int src=n.getInt("src");
				int dest=n.getInt("dest");
				int speed=n.getInt("speed");
				Robot ro=new Robot(src,p1,id,dest,value,speed);
				
			}
		
		
		}catch{};
	}

}
