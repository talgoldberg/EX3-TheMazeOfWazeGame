package dataStructure;

import java.io.Serializable;

public class EdgeData implements edge_data,Serializable  {

	private static final long serialVersionUID = 1L;
	private int src; 
	private int dst; 
	private double w;
	private String info;
	private int tag;
	
	
	
	public EdgeData() {
		 this.src=0; 
		 this.dst=0; 
		 this.w=0; 
		 this.info=""; 
		 this.tag=0;
	}
	
	
	
	
	public EdgeData(int s, int d,double weight) {
		 this.src=s; 
		 this.dst=d;
		 this.w=weight;
	
	}
	
	@Override
	public int getSrc() {
		
		return this.src;
	}

	@Override
	public int getDest() {
		
		return this.dst;
	}

	@Override
	public double getWeight() {
		return this.w;
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
	public String toString() {
		 
		return src + "-----" + w + "----->" +dst;
	}
}
