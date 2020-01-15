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
	public int getSrc() {
		return src;
	}
	public void setSrc(int src) {
		this.src = src;
	}
	public Point3D getP() {
		return p;
	}
	public void setP(Point3D p) {
		this.p = p;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDest() {
		return dest;
	}
	public void setDest(int dest) {
		this.dest = dest;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
}
