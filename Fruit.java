package gameClient;

import utils.Point3D;

public class Fruit {

	double value; 
	Point3D p; 
	String s;
	public Fruit(double value,Point3D p,int type) {
		
		this.value=value; 
		this.p=p; 
		
		if(type==1) {
			this.s="banana";
		}
		if(type==-1) {
			this.s="apple";
		}
	}
	public Fruit() {
		
	}
}
