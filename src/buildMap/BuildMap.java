package buildMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class BuildMap {
	ArrayList<ImageTags> imgTags;
	ImageTags itags;
	double threshold1, threshold2;
	//float threshold3;
	int cutNode;
	Map map;
	
	public void setThreshold1(double threshold1) {
		this.threshold1 = threshold1;
	}

	public void setThreshold2(double threshold2) {
		this.threshold2 = threshold2;
	}

	public void setCutNode(int cutNode) {
		this.cutNode = cutNode;
	}

	public BuildMap (double th1, double th2, int cn) {
		imgTags=new ArrayList<ImageTags>();
		threshold1=th1;
		threshold2=th2;
		cutNode=cn;
		
	}
	
	public void readTags (String base, double threshold) {
		String fileName;
		FileReader fr=null;
		BufferedReader br=null;
		String line;
		
		// First, read the image coordinates files
		double []xcoord, ycoord;
		xcoord= new double[8128];
		ycoord= new double[8128];
		for (int i=0; i<8128; i++) {
			xcoord[i]=ycoord[i]=-1;
		}
		
		try {
			fr = new FileReader (new File ("output.data"));
			br = new BufferedReader(fr);
			// The first line is the name of the file, ignored
			line = br.readLine();
			while ((line=br.readLine())!=null) {
				String[] sp = line.split(" ");
				int i=Integer.parseInt(sp[0]);
				xcoord[i]=Double.parseDouble(sp[1]);
				ycoord[i]=Double.parseDouble(sp[2]);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		} 
		finally {
			try {
				if (fr != null) {   
					fr.close();
				}
			} 
			catch (Exception e2){ 
				e2.printStackTrace();
			}
		}
		
		// Process all the images and read the tags
		for (int i=1; i<8128; i++) {
			fileName=base+i+".txt";
			try {
				itags=new ImageTags(fileName);
				itags.setThreshold((float)threshold);
				if (xcoord[i]!=-1) {
					itags.setCoords(xcoord[i], ycoord[i]);
				}
				else {
					for (int j=i-2, k=i+1; j>0 || k<8127; j--, k++) {
						if (j>0 && xcoord[j]!=-1) {
							itags.setCoords(xcoord[j], ycoord[j]);
							break;
						}
						if (k<8127 && xcoord[k]!=-1) {
							itags.setCoords(xcoord[k], ycoord[k]);
							break;
						}
					}
				}
				fr = new FileReader (new File (fileName));
				br = new BufferedReader(fr);
				// The first line is the name of the file, ignored
				//line = br.readLine(); //------------------------------------------------------
				while ((line=br.readLine())!=null) {
					itags.addTag(line);
				}
				
				imgTags.add(itags);
			}
			catch(Exception e) {
				e.printStackTrace();
			} 
			finally {
				try {
					if (fr != null) {   
						fr.close();
					}
				} 
				catch (Exception e2){ 
					e2.printStackTrace();
				}
			}
		}
	}
	
	public void buildMap () {
		FileMethods.saveFile("Th1= "+threshold1+" Th2= " +threshold2+" CN= "+ cutNode+"------\n", "Distancias", true);	
		map = new Map();
		map.setWeights(cutNode);
		double minDist, dist;
		int cont=0;
		Node auxNode, auxNode2;
		@SuppressWarnings("unused")
		boolean foundNode, foundEdge;
		
		// For the first image, create a node
		Node currentNode = map.createNode(imgTags.get(0));
		for (int i=1; i<8127; i++) {
			cont++;
			if ((cont%1000)==0) System.out.println("Processing img="+i);
			// Find the closest node
			minDist=Double.MAX_VALUE;
			auxNode2=null;
			for (int n=0; n<map.getMapSize(); n++) {
				auxNode=map.getNode(n);
				if (auxNode!=currentNode) {
					dist=auxNode.distance(imgTags.get(i));
					if (dist<minDist) {
						minDist=dist;
						auxNode2=auxNode;
					}
				}
			}
			dist = currentNode.distance(imgTags.get(i));

			

			if (dist<threshold2) {
				currentNode.add(imgTags.get(i));
			}
			else {
				if (minDist<dist && minDist<threshold1) {
					foundEdge=false;
					map.createEdge(currentNode,auxNode2);
					currentNode=auxNode2;
					currentNode.add(imgTags.get(i));
				}
				else {
					// Final decision: create a new node
					auxNode = currentNode;
					currentNode = map.createNode(imgTags.get(i));
					map.createEdge(auxNode, currentNode);
				}
			}
			// If the distance of the current image is below the first threshold, it keeps it in the current node
//			if (currentNode.distance(imgTags.get(i)) < threshold1) {
//				currentNode.add(imgTags.get(i));
//			}
//			else {
				// Check if it could be put in another node
				//First, check it could be inserted in an adjacent node
//				foundNode=false;
//				for (Edge e:map.edges) {
//					if (e.a==currentNode) {
//						if (e.b.distance(imgTags.get(i))<threshold2) {
//							currentNode=e.b;
//							currentNode.add(imgTags.get(i));
//							foundNode=true;
//							break;
//						}
//					}
//					if (e.b==currentNode) {
//						if (e.a.distance(imgTags.get(i))<threshold2) {
//							currentNode=e.a;
//							currentNode.add(imgTags.get(i));
//							foundNode=true;
//							break;
//						}
//					}
//				}
//				if (!foundNode) {
//					minDist=Double.MAX_VALUE;
//					auxNode2=null;
//					for (int n=0; n<map.getMapSize(); n++) {
//						auxNode=map.getNode(n);
//						if (auxNode!=currentNode) {
//							dist=auxNode.distance(imgTags.get(i));
//							if (dist<minDist) {
//								minDist=dist;
//								auxNode2=auxNode;
//							}
//						}
//					}
//					if (minDist<threshold3) {
//						map.createEdge(currentNode, auxNode2);
//						currentNode=auxNode2;
//						currentNode.add(imgTags.get(i));
//					}
//					else {
//						// Final decision: create a new node
//						auxNode = currentNode;
//						currentNode = map.createNode(imgTags.get(i));
//						map.createEdge(auxNode, currentNode);
//					}
//				}
//			}
		}//end for
		
		
		
	}
	
	public void printMap () {
		map.printMap();
	}
}
