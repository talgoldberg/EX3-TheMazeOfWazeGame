package gameClient;

import dataStructure.edge_data;
import utils.Point3D;

public class Fruit {

	double value; 
	Point3D p; 
	String s;
	int type;
	edge_data e;
	boolean isDest=false;
	public Fruit(double value,Point3D p,int type,edge_data e) {
		
		this.value=value; 
		this.p=p; 
		this.type=type;
		this.e=e;
		if(this.type==1)
			s="./apple.png";
		if(this.type==-1)
			s="./banana.png";
	}
	public Fruit() {
		
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public Point3D getP() {
		return p;
	}
	public void setP(Point3D p) {
		this.p = p;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public edge_data getE() {
		return e;
	}
	public void setE(edge_data e) {
		this.e = e;
	}
	public boolean isDest() {
		return isDest;
	}
	public void setDest(boolean isDest) {
		this.isDest = isDest;
	}
}
