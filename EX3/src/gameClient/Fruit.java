package gameClient;

import dataStructure.edge_data;
import utils.Point3D;

public class Fruit {

	double value; 
	Point3D p; 
	String s;
	int type;
	
	public Fruit(double value,Point3D p,int type) {
		
		this.value=value; 
		this.p=p; 
		this.type=type;
		
		if(this.type==1)
			s="./apple.png";
		if(this.type==-1)
			s="./banana.png";
	}
	public Fruit() {
		
	}
}
