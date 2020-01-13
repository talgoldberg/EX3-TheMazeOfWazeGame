package dataStructure;

import java.io.Serializable;

import utils.Point3D;

public class NodeData implements node_data,Serializable {

	private static final long serialVersionUID = 1L;
	private int key; 
	private Point3D p; 
	private double w; 
	private String info; 
	private int tag; 
	
	
	public NodeData() {
		this.key=0; 
		this.w=0; 
		this.info=""; 
		this.tag=0;
	}
	
	
	
	public NodeData(int k, Point3D p1, double weight, String i, int t) {
		this.key=k;
		this.p=p1; 
		this.w=weight; 
		this.info=i; 
		this.tag=t;
	}
	public NodeData( Point3D p1, int k, double weight) {
		this.key=k;
		this.p=p1; 
		this.w=weight; 
	}
	public NodeData( Point3D p1, int k) {
		this.key=k;
		this.p=p1; 
	}
	@Override
	public int getKey() {
		return this.key;
	}

	@Override
	public Point3D getLocation() {
	return this.p;
	}

	@Override
	public void setLocation(Point3D p) {
		this.p=p;
	}

	@Override
	public double getWeight() {
		return this.w;
	}

	@Override
	public void setWeight(double w) {
		this.w=w;
		
	}

	@Override
	public String getInfo() {
		return this.info;
	}

	@Override
	public void setInfo(String s) {
		this.info=s;
		
	}

	@Override
	public int getTag() {
		return this.tag;
	}

	@Override
	public void setTag(int t) {
		this.tag=t;
		
	}
	public String toString()
	{
		return ""+key;
	}
}
