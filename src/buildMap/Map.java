package buildMap;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Map {
	ArrayList<Node> nodes;
	ArrayList<Edge> edges;
	boolean useHisto=true;
	float[] weights;
	
	public Map () {
		nodes=new ArrayList<Node>();
		edges=new ArrayList<Edge>();
	}
	
	public void setWeights (int size) {
		weights= new float[size];
		for (int i=0; i< size; i++) {
			weights[i]=(i/(float) size);
			//System.err.println(weights[i]);
		}
	}
	
	public Node createNode (ImageTags i) {
		Node n=new Node(useHisto, weights);
		n.add(i);
		nodes.add(n);
		return n;
	}
	
	public void printMap () {
		boolean [][]adj;
		adj=new boolean[nodes.size()][nodes.size()];
		
		for (int i=0; i<nodes.size(); i++) {
			nodes.get(i).printCoords();
		}
		for (int i=0; i<edges.size(); i++) 
			adj[nodes.indexOf(edges.get(i).getA())][nodes.indexOf(edges.get(i).getB())]=true;

		FileWriter fichero = null;
        PrintWriter pw = null;
        
        try {
        	fichero = new FileWriter("adj");
        	pw = new PrintWriter(fichero);

    		for (int i=0; i<nodes.size(); i++) {
    			for (int j=0; j<nodes.size(); j++) 
    				if (adj[i][j]) pw.print("1 ");
    				else pw.print("0 ");
    			pw.println("");
    		}
    	} catch (Exception e) {
            e.printStackTrace();
        } finally {
        	try {
        		if (fichero != null)
        			fichero.close();
        	} catch (Exception e2) {
        		e2.printStackTrace();
        	}
        }
	}
	
	public int getMapSize () {
		return nodes.size();
	}
	
	public void addNode (Node n) {
		nodes.add(n);
	}
	
	public Node getNode (int i) {
		if (i>nodes.size()) 
			return null;
		return nodes.get(i);
	}
	
	public void createEdge (Node a, Node b) {
		for (int i=0; i<edges.size(); i++) {
			if ((edges.get(i).a==a && edges.get(i).b==b) ||
				(edges.get(i).a==b && edges.get(i).b==a))
				return;
		}
		Edge e = new Edge(a, b);
		edges.add(e);
	}
	
	class Edge {
		Node a, b;
		
		public Edge (Node ia, Node ib) {
			a=ia;
			b=ib;
		}
		
		public Node getA () {
			return a;
		}
		
		public Node getB () {
			return b;
		}
		
	}

}
