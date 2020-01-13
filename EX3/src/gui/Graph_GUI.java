package gui;

import java.awt.BasicStroke;

import java.awt.Color;

import java.awt.Graphics;

import java.awt.Graphics2D;

import java.awt.Menu;

import java.awt.MenuBar;

import java.awt.MenuItem;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;

import java.awt.image.BufferedImage;

import java.io.File;

import java.io.IOException;

import java.util.ArrayList;

import java.util.Collection;

import java.util.List;



import javax.imageio.ImageIO;

import javax.swing.ImageIcon;

import javax.swing.JFileChooser;

import javax.swing.JFrame;

import javax.swing.JOptionPane;

import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.filechooser.FileSystemView;





import algorithms.*;

import dataStructure.*;

import utils.*;



import java.awt.Color;

import java.awt.Font;



import algorithms.Graph_Algo;

import dataStructure.*;



import utils.Point3D;

import utils.Range;

import utils.StdDraw;

public class Graph_GUI extends JFrame implements ActionListener

{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DGraph Dgraph;


	public Graph_GUI (DGraph d)
	{
		this.Dgraph=d;
		initGUI();
		
	}

	private void initGUI() 
	{
		this.setSize(700, 700);                           //create window
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      //create exit on window


		/////////////create menu on window////////////////
		MenuBar menuBar = new MenuBar();                       
		Menu menu = new Menu("Menu");
		menuBar.add(menu);
		this.setMenuBar(menuBar);

		/////////////add Buttons to the menu////////////////////////
		MenuItem save = new MenuItem("save");
		save.addActionListener(this);

		MenuItem TSP = new MenuItem("TSP");
		TSP.addActionListener(this);

		MenuItem SP = new MenuItem("shortest Path");
		SP.addActionListener(this);

		MenuItem isConnected = new MenuItem("isConnected");
		isConnected.addActionListener(this);

		MenuItem fromFile = new MenuItem("from file");
		fromFile.addActionListener(this);

		MenuItem SPD = new MenuItem("shortest Path Dist");
		SPD.addActionListener(this);

		menu.add(save);
		menu.add(TSP);
		menu.add(SP);
		menu.add(isConnected);
		menu.add(fromFile);
		menu.add(SPD);

	}

	public void paint(Graphics g)
	{
		super.paint(g);

		for(node_data node :Dgraph.getV()) // draw Data Nodes
		{
			g.setColor(Color.BLUE);         //blue color for Circle
			g.fillOval((int)node.getLocation().x(),(int)node.getLocation().y(), 8, 8); //draw the Circle
			g.setFont(new Font("Arial", Font.BOLD, 14));
			g.drawString(String.valueOf(node.getKey()),(int)node.getLocation().x(),(int)node.getLocation().y());  //draw the key value
		}

		////////////////

		//////////////// draw the Edges nodes//////////////////

		for(node_data node1 :Dgraph.getV())
		{
			if(Dgraph.getE(node1.getKey())!=null)
			{
				for(edge_data edge1: Dgraph.getE(node1.getKey())) 
				{ 
					if(Dgraph.getNode(edge1.getDest())!=null) {
						g.setColor(Color.RED);     // red color for lines
						g.drawLine((int) Dgraph.getNode(edge1.getDest()).getLocation().x()+5, (int) Dgraph.getNode(edge1.getDest()).getLocation().y()+5,
								(int) Dgraph.getNode(edge1.getSrc()).getLocation().x()+5, (int) Dgraph.getNode(edge1.getSrc()).getLocation().y()+5);
						String Weight = String.valueOf(edge1.getWeight());
						//////////////////////////draw the weight of edge
						g.setColor(Color.red);
						g.drawString(Weight, (int)((Dgraph.getNode(edge1.getSrc()).getLocation().x() + Dgraph.getNode(edge1.getDest()).getLocation().x()) / 2),
								(int) (Dgraph.getNode(edge1.getSrc()).getLocation().y() + Dgraph.getNode(edge1.getDest()).getLocation().y()) / 2);


						/////////draw Circle for direction 
						double X = ((Dgraph.getNode(edge1.getDest()).getLocation().x()+2) * 8 + (Dgraph.getNode(edge1.getSrc()).getLocation().x())+2 )/ 9;
						double  Y = ((Dgraph.getNode(edge1.getDest()).getLocation().y()+2 )* 8 + (Dgraph.getNode(edge1.getSrc()).getLocation().y())+2) / 9;
						g.setColor(Color.YELLOW);  // yellow color for the circle of direction
						g.fillOval((int)X,(int)Y, 7, 7);

					}

				}}

		}


	}

 
	@Override
	public void actionPerformed(ActionEvent e) {
     String str = e.getActionCommand();
		
      Graph_Algo a=new Graph_Algo();
      a.init(Dgraph);
      JFrame newWindow = new JFrame();
      
		if(str.equals("isConnected"))
		{
			JOptionPane.showMessageDialog(newWindow,a.isConnected());
			repaint();
		}
		if(str.equals("save"))
		{
			a.save("itsWorksGood.txt");
			repaint();
		}
		if(str.equals("TSP"))
		{
			ArrayList<Integer>arr=new ArrayList<Integer>();
			String NumOfNodes = JOptionPane.showInputDialog(newWindow,"how much Nodes do you want to cheak?");
			
			for (int i = 0; i <Integer.parseInt(NumOfNodes); i++)
			{
				String NumForList = JOptionPane.showInputDialog(newWindow,"choose a node for cheak");
				arr.add(Integer.parseInt(NumForList));
			}
			
			JOptionPane.showMessageDialog(newWindow, a.TSP(arr));  
			repaint();
			
		}
		
		if(str.equals("from file"))
		{
			String FileName = JOptionPane.showInputDialog(newWindow,"what is the name of the file?");
            a.init(FileName);
		}
		if(str.equals("shortest Path"))
		{
			 
			 String Source = JOptionPane.showInputDialog(newWindow,"Source Node");
			 String Dest = JOptionPane.showInputDialog(newWindow,"Destination Node");
			JOptionPane.showMessageDialog(newWindow, a.shortestPath(Integer.parseInt(Source),Integer.parseInt(Dest)).toString());  
			repaint();
		}
		
		
		if(str.equals("shortest Path Dist"))
		{
			String Source = JOptionPane.showInputDialog(newWindow,"Source Node");
			String Dest = JOptionPane.showInputDialog(newWindow,"Destination Node");
			JOptionPane.showMessageDialog(newWindow, a.shortestPathDist(Integer.parseInt(Source), Integer.parseInt(Dest)));  
			repaint();
		}
		

	}




public static void main(String[] args) {
	
	NodeData test1=new NodeData(1,null,1.0,null,1);
	NodeData test2=new NodeData(2,null,22.0,null,2);
	NodeData test3=new NodeData(3,null,3.0,null,3);
	NodeData test4=new NodeData(4,null,7.0,null,1);
	NodeData test5=new NodeData(5,null,5.0,null,2);
	NodeData test6=new NodeData(6,null,8.0,null,3);
	NodeData test7=new NodeData(7,null,1.0,null,1);
	NodeData test8=new NodeData(8,null,12.0,null,2);
	NodeData test9=new NodeData(9,null,10.0,null,3);
	
	Point3D p1= new Point3D(500, 500);
	Point3D p2= new Point3D(600, 400);
	Point3D p3= new Point3D(520,670);
	Point3D p4= new Point3D(400, 100);
	Point3D p5= new Point3D(570, 170);
	Point3D p6= new Point3D(600, 200);
	Point3D p7= new Point3D(150,230);
	Point3D p8= new Point3D(300, 410);
	Point3D p9= new Point3D(400, 180);
	test1.setLocation(p1);
	test2.setLocation(p2);
	test3.setLocation(p3);
	test4.setLocation(p4);
	test5.setLocation(p5);
	test6.setLocation(p6);
	test7.setLocation(p7);
	test8.setLocation(p8);
	test9.setLocation(p9);
	//System.out.println(test1);
	DGraph a1 =new DGraph();
	DGraph a2 =new DGraph();
	a1.addNode(test1);
	a1.addNode(test2);
	a1.addNode(test3);
	a1.addNode(test4);
	a1.addNode(test5);
	a1.addNode(test6);
	a1.addNode(test7);
	a1.addNode(test8);
	a1.addNode(test9);
    a1.connect(2, 5, 444);
    a1.connect(2, 4, 14);
    a1.connect(5, 9, 4);
    a1.connect(4, 5, 30);
    a1.connect(7, 2, 8);
    a1.connect(3, 7, 11);
    a1.connect(6, 1, 9);
    a1.connect(5, 8, 3);
    a1.connect(8, 6, 12);
   

	Graph_GUI gg = new Graph_GUI(a1);
//	Graph_GUI g2 = new Graph_GUI(a2);
	gg.setVisible(true);
}
}