package dataStructure;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;

import org.json.JSONObject;

import utils.Point3D;

public class DGraph implements graph,Serializable{

	private static final long serialVersionUID = 1L;
	private HashMap<Integer,node_data> nodeCol=new HashMap<Integer,node_data>();
	private HashMap<Integer,HashMap<Integer,edge_data>> edgeCol=new HashMap<Integer,HashMap<Integer,edge_data>>();
	int MC=0;
	
	public DGraph() {
		
	}
	
	public DGraph(HashMap<Integer,node_data> n,HashMap<Integer,HashMap<Integer,edge_data>> edgeCol1) {
		
		nodeCol=(HashMap<Integer, node_data>) n;
		edgeCol=(HashMap<Integer,HashMap<Integer,edge_data>>) edgeCol1;
	}
	
	
	@SuppressWarnings("unchecked")
	public DGraph(DGraph g) 
	{
		nodeCol=(HashMap<Integer, node_data>) g.nodeCol.clone();
		edgeCol=(HashMap<Integer, HashMap<Integer, edge_data>>) g.edgeCol.clone();
	}

	@Override
	public node_data getNode(int key) {//return the node data
		
			return nodeCol.get(key);
		
	}

	@Override
	public edge_data getEdge(int src, int dest) {
		
			return edgeCol.get(src).get(dest);
	
	}

	@Override
	public void addNode(node_data n) {
	
		if(!nodeCol.containsKey(n.getKey())) {	
		nodeCol.put(n.getKey(),(node_data) n);
		MC++;
		}
	}

	@Override
	public void connect(int src, int dest, double w) {
		
		EdgeData newedge =new EdgeData(src,dest,w);
		if(edgeCol.containsKey(src)) {
			if(!edgeCol.get(src).containsKey(dest)) {
				edgeCol.get(src).put(dest,newedge);
			}
		}
			
		else {
			
			HashMap<Integer,edge_data> help=new HashMap<Integer,edge_data>();
			help.put(dest, newedge);
			edgeCol.put(src, help);
			MC++;
		
		}
	
	}
	
	@Override
	public Collection<node_data> getV() {
		
		
		return nodeCol.values();
		
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		
		if(edgeCol.get(node_id)!=null)
			return edgeCol.get(node_id).values();
	return null;
	
	}

	@Override
	public node_data removeNode(int key) {
		for ( Map.Entry<Integer,node_data> e: nodeCol.entrySet()) {
				if(edgeCol.get(e)!=null) {
					if(edgeCol.get(e).get(key)!=null) {
						 edgeCol.get(e).remove(key);
						 MC++;
					}
			 	}
		}
		return nodeCol.remove(key);
		
	}
	@Override
	public edge_data removeEdge(int src, int dest) {
		
		if(edgeCol.get(src)!=null) {
		if(edgeCol.get(src).get(dest)!=null) {
			MC++;
			return edgeCol.get(src).remove(dest);
			
		}
		}
		return null;
	}
	@Override
	public int nodeSize() {
	
		return nodeCol.size();
	}

	@Override
	public int edgeSize() {
	
		return edgeCol.size();
	}

	@Override
	public int getMC() {
		
		return MC;
	}

	public void init(String g)  {
		
		try {
			int i=0;
			int i1=0;
			JSONObject json = new JSONObject(g);

			JSONArray edges = json.getJSONArray("Edges");

			JSONArray nodes = json.getJSONArray("Nodes");


			while(i<nodes.length()) {
				int j=0;
				String help="";
				JSONObject n=(JSONObject) nodes.get(i);
				String s=n.getString("pos");
				String p[]=new String[3]; 
					for (int k = 0; k < s.length(); k++) {
						if(s.charAt(k)!=',') {
							help=help+s.charAt(k);
						}
						if(j<3&&s.charAt(k)==',') {
							p[j]=help; 
							j++; 
							help="";
						}
					}
				p[j]=help;
				j=0;
				double x=Double.parseDouble(p[0]);
				double y=Double.parseDouble(p[1]);
				double z=Double.parseDouble(p[2]);
				
				Point3D p1=new Point3D(x,y,z);
				int idKey=n.getInt("id"); 
				node_data node=new NodeData(p1,idKey);
				addNode(node);
				i++;
			}
				while(i1<edges.length()) {
					JSONObject e=(JSONObject)edges.get(i1);
					
					int src=e.getInt("src"); 
					int w=e.getInt("w"); 
					int dest=e.getInt("dest");
					connect(src,dest,w);
					i1++;
				}
		}catch(Exception error) {
			error.printStackTrace();

			System.out.println("catch");
		}
		}
	}


