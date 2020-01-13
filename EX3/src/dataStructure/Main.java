package dataStructure;

import java.awt.font.NumericShaper.Range;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import algorithms.Graph_Algo;

import utils.Point3D;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		DGraph x=new DGraph();

		Point3D p=new Point3D(1,2,3);

		NodeData n=new NodeData(p,2, 0);

		x.addNode(n);

		p=new Point3D(3,2,1);

		NodeData m=new NodeData(p,4, 0);

		x.addNode(m);

		p=new Point3D(3,2,1);

		NodeData v=new NodeData(p,5,0);
		
		x.addNode(v);

		x.connect(2, 5, 13);

		//x.connect(5, 2, 4);

		x.connect(2, 4, 2);

		//x.connect(2, 3, 1);
		x.connect(4, 5, 2);
//		x.connect(3, 1, 1);
		//System.out.println(x.toString());
		Graph_Algo g1 = new Graph_Algo();
	
		
		g1.init(x);
		//graph copy=g1.copy();
		//System.out.println(copy.getV().toString());
		//System.out.println(copy.getE(n.getKey()));
		g1.save("output.txt");
		System.out.println(g1.toString());
		System.out.println(	g1.isConnected());
		List<Integer> list=new ArrayList<>();
		for(node_data node :x.getV())
			list.add(node.getKey());
		
		System.out.println(g1.shortestPath(2, 5));
		System.out.println(g1.shortestPathDist(4, 2));
		System.out.println(g1.TSP(list).toString());
	
		Point3D p1=new Point3D (1,1);
		Point3D p2=new Point3D (3,2);
		Point3D p3=new Point3D (4,5);
		Point3D p4=new Point3D (6,2);
		Point3D p5=new Point3D (7,-1);
		Point3D p6=new Point3D (5,-3);
		Point3D p7=new Point3D (2,-2);
		Point3D p8=new Point3D (-1,-4);
		Point3D p9=new Point3D (-4,-2);
		Point3D p10=new Point3D (-5,-1);
		Point3D p11=new Point3D (-6,2);
		Point3D p12=new Point3D (-5,4);
		Point3D p13=new Point3D (-3,6);
		Point3D p14=new Point3D (-1,3);
		
		NodeData n1=new NodeData(p1,1,3);
		NodeData n2=new NodeData(p2,2,3);
		NodeData n3=new NodeData(p3,3,3);
		NodeData n4=new NodeData(p4,4,3);
		NodeData n5=new NodeData(p5,5,3);
		NodeData n6=new NodeData(p6,6,3);
		NodeData n7=new NodeData(p7,7,3);
		NodeData n8=new NodeData(p8,8,3);
		NodeData n9=new NodeData(p9,9,3);
		NodeData n10=new NodeData(p10,10,3);
		NodeData n11=new NodeData(p11,11,3);
		NodeData n12=new NodeData(p12,12,3);
		NodeData n13=new NodeData(p13,13,3);
		NodeData n14=new NodeData(p14,14,3);
		
		graph g=new DGraph();
		
		g.addNode(n1);
		g.addNode(n2);
		g.addNode(n3);
		g.addNode(n4);
		g.addNode(n5);
		g.addNode(n6);
		g.addNode(n7);
		g.addNode(n8);
		g.addNode(n9);
		g.addNode(n10);
		g.addNode(n11);
		g.addNode(n12);
		g.addNode(n13);
		g.addNode(n14);
		
		g.connect(1, 2, 3);
		g.connect(2, 3, 4);
		g.connect(3, 4, 5);
		g.connect(4, 5, 6);
		g.connect(4, 7, 32);
		g.connect(5, 6, 13);
		g.connect(6, 7, 63);
		g.connect(7, 8, 44);
		g.connect(8, 9, 34.6);
		g.connect(9, 10, 31.2);
		g.connect(9, 14, 10.3);
		g.connect(10, 11, 12.5);
		g.connect(11, 12, 18);
		g.connect(12, 8, 23.6);
		g.connect(12, 13, 39);
		g.connect(13, 14, 55.6);
		g.connect(14, 1, 43.2);
		g.connect(14, 7, 98.6);
		Graph_Algo y=new Graph_Algo();
		
		y.init(g);
		System.out.println(y.shortestPath(2,7));
		System.out.println(y.isConnected());

		
//		Graph_gui gu=new Graph_gui();
//		Range rx=new Range(-20,20);
//		Range ry=new Range(-22,6);
//		gu.drawGraph(750,750,rx,ry,g);
	
	
	
	}

}
