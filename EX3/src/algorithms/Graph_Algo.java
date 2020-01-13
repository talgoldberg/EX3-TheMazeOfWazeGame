package algorithms;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Stack;

import dataStructure.DGraph;
import dataStructure.NodeData;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;
/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author 
 *
 */
public class Graph_Algo implements graph_algorithms,Serializable{

	private static final long serialVersionUID = 1L;

	private graph d=new DGraph();


	public Graph_Algo()
	{
		
	}
	@Override
	public void init(graph g) 
	{

		d=g;	
	}
	@Override
	public void init(String file_name) {

		try{
			FileInputStream myFile = new FileInputStream(file_name);
			ObjectInputStream ois = new ObjectInputStream(myFile);
			d= (DGraph) ois.readObject();
			ois.close();
		}catch(Exception error) {
			error.printStackTrace();
		}

	}

	@Override
	public void save(String file_name) {

		try{
			FileOutputStream myFile = new FileOutputStream(file_name);
			ObjectOutputStream oos = new ObjectOutputStream(myFile);
			oos.writeObject(d);
			oos.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public boolean isConnected() {

		if((d.nodeSize()>1 && d.edgeSize()==0) || (d.nodeSize()>d.edgeSize()) )
			return false;

		if(d.nodeSize()==1&&d.edgeSize()==0)
			return true; 
		
		putTag0(d);
		
		boolean ans=false;
		for(node_data n: d.getV()) {
			if(d.getNode(n.getKey())!=null)	{
			n.setTag(1);
			chaek(d.getE(n.getKey()));
			}
			
			
		}
		if(chaekTag1(d)==ans)
			return ans;
		else
			ans=true;
		
		return ans;

	}
	private void chaek(Collection<edge_data> edge) {
		
		
		for (edge_data e:edge) {
			
			node_data help=d.getNode(e.getDest());
			
				help.setTag(1);
			
			if(help.getTag()==1)
				return;
		
			chaek(d.getE(help.getKey()));
			
			
		
				
			
			}
	
	}
	private void putTag0(graph g) {
		
		for(node_data n : g.getV()) {
			int k=n.getKey(); 
			if(g.getNode(k)!=null) {
				g.getNode(k).setTag(0);
				}
			}
	}
	private boolean chaekTag1(graph g) {
		for(node_data n : g.getV()) {
			if(g.getNode(n.getKey()).getTag()!=1)
				return false;
		}
		return true;
		}
	
	@Override
	public double shortestPathDist(int src, int dest)//dijkstra 

	{


		putTag0(d);

		Tags1(src,d);

		if(d.getNode(dest).getTag()!=1) {

			System.out.println("There is not a path");

			return -1;

		}

		SetMaxWeight(d);

		d.getNode(src).setWeight(0);

		Collection<node_data> notVisited=new LinkedList<>(d.getV());

		node_data minWeight;

		while (!notVisited.isEmpty()) {

			

			minWeight = findMinNode(notVisited);

		
			if(d.getE(minWeight.getKey())!=null) {
			for(edge_data e : d.getE(minWeight.getKey())) {

				node_data neighbor = d.getNode(e.getDest());

				if(neighbor.getInfo() != "done") {

					double distance = minWeight.getWeight() + e.getWeight();

					if(distance < neighbor.getWeight()) {

						neighbor.setWeight(distance);

						neighbor.setTag(minWeight.getKey());

					}
				}
				}

			}

			

			minWeight.setInfo("done");

			notVisited.remove(minWeight);

		}



		double ans = d.getNode(dest).getWeight();

		if(ans == Double.MAX_VALUE) 

			return -1;

		else

			return ans;

	}
	private static void Tags1(int key,graph g) {

		Stack<Integer> Stack=new Stack<Integer>();

		Stack.push(key);

		while(!Stack.isEmpty()) {

			node_data n=g.getNode(Stack.peek());
			if(n!=null) {
			n.setTag(1);

			Stack.pop();
			if(g.getE(n.getKey())!=null) {
			for(edge_data itr:g.getE(n.getKey())) {
				
				boolean isExsits=g.getNode(itr.getDest())!=null;

				boolean NotPassYet=g.getNode(itr.getDest()).getTag()==0;

				if(isExsits&&NotPassYet) {

					Stack.push(itr.getDest());

					g.getNode(itr.getDest()).setTag(1);
				
				}
			}
				}

			}

		}

	}
	private static node_data findMinNode(Collection<node_data> nodes) {

		Iterator<node_data> itr = nodes.iterator();

		node_data minWeight = itr.next();

		for(node_data n : nodes) {

			if(n.getWeight() < minWeight.getWeight())

				minWeight = n;

		}

		return minWeight;

	}
	private static void SetMaxWeight(graph g) {

		for(node_data n : g.getV()) {

			n.setWeight(Double.MAX_VALUE);

			n.setInfo("");

			n.setTag(0);

		}

	}

	@Override
	public List<node_data> shortestPath(int src, int dest) 

	{
		List<node_data> ans=new ArrayList<>();

		if(shortestPathDist(src,dest)==Double.MAX_VALUE)

		{

			ans.add(d.getNode(src));
			return ans;

		}



		node_data runner=d.getNode(dest);

		while(runner.getKey()!=src)

		{

			ans.add(new NodeData(runner.getLocation(),runner.getKey(),runner.getWeight()));

			runner=d.getNode(runner.getTag());

		}

		ans.add(d.getNode(src));
		Collections.reverse(ans);
		return ans;

	}

	@Override
	public List<node_data> TSP(List<Integer> targets) 
	{

		List<node_data> TSP = new LinkedList<node_data>();

		Iterator<Integer> itr=targets.iterator();

		int src=itr.next();

		TSP.add(0,d.getNode(src));

		while(itr.hasNext()) {

			int dest=itr.next();
			List<node_data> nodePath = new LinkedList<node_data>(shortestPath(src,dest));

			nodePath.remove(0);

			TSP.addAll(nodePath);
			src=dest;

		}

		return TSP;

	}

	@Override
	public graph copy() 
	{
		HashMap<Integer,node_data> nodeCol=new HashMap<Integer,node_data>();
		HashMap<Integer,edge_data> edgeCol1=new HashMap<Integer,edge_data>();
		HashMap<Integer,HashMap<Integer,edge_data>> edgeCol=new HashMap<Integer,HashMap<Integer,edge_data>>();
		for(node_data n: d.getV()) {
			int k=n.getKey(); 
			nodeCol.put(k, n);

			if(d.getNode(k)!=null &&d.getE(k)!=null) 
			{
				for (edge_data e: d.getE(k)) {
					if(edgeCol.containsKey(k)) {
						edgeCol.get(k).put(e.getDest(),e);
					}

					edgeCol1.put(k, e);
					edgeCol.put(k, edgeCol1);
				}
			}

		}
		d=new DGraph(nodeCol, edgeCol);
		return d;
		
		
	}
	private class Vertex_Comperator implements Comparator<node_data> 

	{

		public Vertex_Comperator()

		{



		}



		@Override

		public int compare(node_data v2,node_data v1)

		{

			if(v1.getWeight()-v2.getWeight()>0)

				return -1;

			else return 1;

		}

	}
	@Override
	public String toString() {
		return "Graph_Algo [d=" + d + "]";
	}
	

}
