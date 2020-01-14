package gameClient;

import utils.Point3D;

public class Robot {

	int src; 
	Point3D p; 
	int id; 
	int dest; 
	double value;
	double speed;
	
	public Robot(int src,Point3D p,int id,int dest,double value,double speed) {
		this.src=src; 
		this.p=p; 
		this.id=id; 
		this.dest=dest; 
		this.value=value;
		this.speed=speed;
	}
	public Robot() {
		
	}
}
