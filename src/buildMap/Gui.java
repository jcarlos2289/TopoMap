package buildMap;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
//import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Gui extends JFrame implements ActionListener {
	private static final long serialVersionUID = -7457350242559078527L;
	private CanvasMap cm;
	int width = 1000, height = 900;
	JButton process, clusterbt, clusterCoefBt;
	JCheckBox originalButton, graphButton, backButton, showNodes,clusters;
	JTextField th1, th2, th3;
	JComboBox<String> nodes;
	double threshold1, threshold2;
	int cutNode;
	boolean original = true, showMap = true, background = true,
			mapGenerated = false, nodesMode = false, selectNodeChanged = false, showCluster = false;
	int selectedNode = -1;
	
	BuildMap bm;
	Kmeans km;
	String name ;
	
	public Gui() {
		threshold1 = 0.022;
		threshold2 = 0.049;
		cutNode = 15;
		bm = new BuildMap(threshold1, threshold2, cutNode);
		
		//bm.readTags("/home/jcarlos2289/Documentos/tagsNewCollege/NewCollegePlaces_AlexNet/NewCollege_",0.000000001,8127,"output.data",205,1,2000000);
		//name = "NewCollege_PlacesAlexNet";
		
		bm.readTags("/home/jcarlos2289/Documentos/tagsNewCollege/NewCollege_HybridAlexNet/NewCollege_",0.000000001,8127,"output.data",1183,1,2000000);
		name = "NewCollege_HybridAlexNet";

		// bm.readTags("/Users/miguel/Dropbox/Investigacion/Desarrollo/MapaTopologico/tagsNewCollege/NewCollegeTags/PanoStitchOutput_LisaNewCollegeNov3_");
		//bm.readTags("/home/jcarlos2289/workspacejava/tagsNewCollege/NewCollegePlaces/NewCollege_",0.000000001,8127);
		//bm.readTags("/home/jcarlos2289/Documentos/tagsNewCollege/NewCollegePlaces/NewCollege_",0.000000001);

		//bm.readTags("/Users/miguel/Dropbox/Investigacion/Desarrollo/MapaTopologico/tagsNewCollege/NewCollegeTags/PanoStitchOutput_LisaNewCollegeNov3_");
		//bm.readTags("/home/jcarlos2289/workspace/tagsNewCollege/NewCollegePlaces/NewCollege_",0.000000001);
		
		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Dumbo/dum_cloudy1/dum_cloudy1_Places/IDOL_DUMBO_Cl1_",-0.000000001,917,"IDOL_DUMBO_Cl1.txt",205);
		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie/min_cloudy1/min_cloudy1_Places/IDOL_MINNIE_Cl1_",-0.000000001,915, "IDOL_MINNIE_Cl1.txt",205);
		//name = "MinnieCl1_PlacesAlexNet";
		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Dumbo/dum_cloudy1/dum_cloudy1_ImageNet/IDOL_DUMBO_Cl1_",-0.000000001,917,"IDOL_DUMBO_Cl1.txt",1000);
		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie/min_cloudy1/min_cloudy1_ImageNet/IDOL_MINNIE_Cl1_",-1.00,915, "IDOL_MINNIE_Cl1.txt",1000);
//name = "MinnieCl1_ImageNetCaffe";
	    //bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Dumbo/dum_cloudy1/dum_cloudy1_Hybrid/IDOL_DUMBO_Cl1_",-0.000000001,917,"IDOL_DUMBO_Cl1.txt",1183);
		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie/min_cloudy1/min_cloudy1_Hybrid/IDOL_MINNIE_Cl1_",-0.000000001,915, "IDOL_MINNIE_Cl1.txt",1183);
//name = "MinnieCl1_HybridAlexNet";
		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie/min_cloudy1/min_cloudy1_ImageNetGoogleNet/IDOL_MINNIE_Cl1_",-0.000000001,915, "IDOL_MINNIE_Cl1.txt",1000);
	//	bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Dumbo/dum_cloudy1/dum_cloudy1_ImageNetGoogleNet/IDOL_DUMBO_Cl1_",-0.000000001,917,"IDOL_DUMBO_Cl1.txt",1000);
//name = "DumboCl1_ImageNetGoogLeNet";
		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie/min_cloudy1/min_cloudy1_ImageNetAlex/IDOL_MINNIE_Cl1_",-0.000000001,915, "IDOL_MINNIE_Cl1.txt",1000);
		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Dumbo/dum_cloudy1/dum_cloudy1_ImageNetAlex/IDOL_DUMBO_Cl1_",-0.000000001,917,"IDOL_DUMBO_Cl1.txt",1000);
		//name = "MinnieCl1_ImageNetAlexNet";
		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie/min_cloudy1/min_cloudy1_ImageNetRCNN/IDOL_MINNIE_Cl1_",-0.000000001,915, "IDOL_MINNIE_Cl1.txt",1000);
		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Dumbo/dum_cloudy1/dum_cloudy1_ImageNetRCNN/IDOL_DUMBO_Cl1_",-0.000000001,917,"IDOL_DUMBO_Cl1.txt",1000);
        // name = "DumboCl1_ImageNetRCNN";
		
//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie/min_cloudy1/min_cloudy1_ImageNetVGG/IDOL_MINNIE_Cl1_",-0.000000001,915, "IDOL_MINNIE_Cl1.txt",1000);
	    //bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Dumbo/dum_cloudy1/dum_cloudy1_ImageNetVGG/IDOL_DUMBO_Cl1_",-0.000000001,917,"IDOL_DUMBO_Cl1.txt",1000);
         //name = "DumboCl1_ImageNetVGG";



		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie/min_cloudy1/min_cloudy1_ImageNetMerge/IDOL_MINNIE_Cl1_",-0.000000001,915, "IDOL_MINNIE_Cl1.txt",1000);
	    //bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Dumbo/dum_cloudy1/dum_cloudy1_ImageNetMerge/IDOL_DUMBO_Cl1_",-0.000000001,917,"IDOL_DUMBO_Cl1.txt",1000);
			
		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie/min_cloudy1/min_cloudy1_ImageNetSUM/IDOL_MINNIE_Cl1_",-0.000000001,915, "IDOL_MINNIE_Cl1.txt",1000);
	    //bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Dumbo/dum_cloudy1/dum_cloudy1_ImageNetSUM/IDOL_DUMBO_Cl1_",-0.000000001,917,"IDOL_DUMBO_Cl1.txt",1000);
		
		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie/min_cloudy1/min_cloudy1_ImageNetFusion/IDOL_MINNIE_Cl1_",-0.000000001,1830, "IDOL_MINNIE_Cl1_FUSION.txt",1000);
		
		
//------------------------------------------------Fusion		
//CLOUDY
		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_cloudy/min_cloudy_PlacesAlexNet/IDOL_MINNIE_Cl_",-0.000000001,3752, "/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_cloudy/IDOL_MINNIE_Cl.txt",205);
		//name = "MinnieCloudy_PlacesAlexNet";
		
		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_cloudy/min_cloudy_ImageNetAlexNet/IDOL_MINNIE_Cl_",-0.000000001,3752, "/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_cloudy/IDOL_MINNIE_Cl.txt",205);
		//name = "MinnieCloudy_ImageNetAlexNet";
		
		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_cloudy/min_cloudy_ImageNetCaffeNet/IDOL_MINNIE_Cl_",-0.000000001,3752, "/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_cloudy/IDOL_MINNIE_Cl.txt",205);
		//name = "MinnieCloudy_ImageNetCaffeNet";
		
		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_cloudy/min_cloudy_ImageNetGoogLeNet/IDOL_MINNIE_Cl_",-0.000000001,3752, "/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_cloudy/IDOL_MINNIE_Cl.txt",205);
		//name = "MinnieCloudy_ImageNetGoogLeNet";
		
		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_cloudy/min_cloudy_ImageNetVGG/IDOL_MINNIE_Cl_",-0.000000001,3752, "/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_cloudy/IDOL_MINNIE_Cl.txt",205);
		//name = "MinnieCloudy_ImageNetVGG";
		
		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_cloudy/min_cloudy_HybridAlexNet/IDOL_MINNIE_Cl_",-0.000000001,3752, "/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_cloudy/IDOL_MINNIE_Cl.txt",1183,5, 200000000);
		//name = "MinnieCloudy_HybridAlexNet";

//Sunny
		
		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_sunny/min_sunny_PlacesAlexNet/IDOL_MINNIE_Su_",-0.000000001,3606, "/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_sunny/IDOL_MINNIE_Su.txt",205);
		//name = "MinnieSunny_PlacesAlexNet";

		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_sunny/min_sunny_ImageNetAlexNet/IDOL_MINNIE_Su_",-0.000000001,3606, "/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_sunny/IDOL_MINNIE_Su.txt",205);
		//name = "MinnieSunny_ImageNetAlexNet";

		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_sunny/min_sunny_ImageNetCaffeNet/IDOL_MINNIE_Su_",-0.000000001,3606, "/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_sunny/IDOL_MINNIE_Su.txt",205);
		//name = "MinnieSunny_ImageNetCaffeNet";

		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_sunny/min_sunny_ImageNetGoogLeNet/IDOL_MINNIE_Su_",-0.000000001,3606, "/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_sunny/IDOL_MINNIE_Su.txt",205);
		//name = "MinnieSunny_ImageNetGoogLeNet";

		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_sunny/min_sunny_ImageNetVGG/IDOL_MINNIE_Su_",-0.000000001,3606, "/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_sunny/IDOL_MINNIE_Su.txt",205);
		//name = "MinnieSunny_ImageNetVGG";

		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_sunny/min_sunny_HybridAlexNet/IDOL_MINNIE_Su_",-0.000000001,3606, "/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_sunny/IDOL_MINNIE_Su.txt",1183);
		//name = "MinnieSunny_HybridAlexNet";



//Night
		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_night/min_night_PlacesAlexNet/IDOL_MINNIE_Ni_",-0.000000001,4005, "/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_night/IDOL_MINNIE_Ni.txt",205,5, 200000000);
		//name = "Minnienight_PlacesAlexNet";

		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_night/min_night_ImageNetAlexNet/IDOL_MINNIE_Ni_",-0.000000001,4005, "/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_night/IDOL_MINNIE_Ni.txt",205);
		//name = "MinnieNight_ImageNetAlexNet";

		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_night/min_night_ImageNetCaffeNet/IDOL_MINNIE_Ni_",-0.000000001,4005, "/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_night/IDOL_MINNIE_Ni.txt",205);
		//name = "MinnieNight_ImageNetCaffeNet";

		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_night/min_night_ImageNetGoogLeNet/IDOL_MINNIE_Ni_",-0.000000001,4005, "/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_night/IDOL_MINNIE_Ni.txt",205);
		//name = "MinnieNight_ImageNetGoogLeNet";

		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_night/min_night_ImageNetVGG/IDOL_MINNIE_Ni_",-0.000000001,4005, "/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_night/IDOL_MINNIE_Ni.txt",205);
		//name = "MinnieNight_ImageNetVGG";

		//bm.readTags("/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_night/min_night_HybridAlexNet/IDOL_MINNIE_Ni_",-0.000000001,4005, "/home/jcarlos2289/Descargas/KTH_IDOL/KTH_Minnie_Fusion/min_night/IDOL_MINNIE_Ni.txt",1183);
		//name = "MinnieNight_HybridAlexNet";
		
		getContentPane().setLayout(new BorderLayout());
		setSize(width, height);
		setTitle(name);
		cm = new CanvasMap(this);
		//setTitle("Topological Mapping");
		getContentPane().add(cm, BorderLayout.CENTER);
		getContentPane().add(getToolBar(), BorderLayout.NORTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		
	}

	public JPanel getToolBar() {
		JPanel jp = new JPanel();
		jp.setSize(width, 100);
		jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
		jp.setAlignmentX(LEFT_ALIGNMENT);
		process = new JButton("Gen Map");
		process.addActionListener(this);
		jp.add(process);

		clusterbt = new JButton("Gen Clus");
		clusterbt.addActionListener(this);
		jp.add(clusterbt);
		
		clusterCoefBt = new JButton("ClusCoef");
		clusterCoefBt.addActionListener(this);
		jp.add(clusterCoefBt);

		originalButton = new JCheckBox("Original Map");
		originalButton.setSelected(true);
		originalButton.addActionListener(this);
		jp.add(originalButton);
		graphButton = new JCheckBox("Graph");
		graphButton.setSelected(true);
		graphButton.setEnabled(mapGenerated);
		graphButton.addActionListener(this);
		jp.add(graphButton);
		backButton = new JCheckBox("BackImage");
		backButton.setSelected(true);
		backButton.addActionListener(this);
		jp.add(backButton);
		
		clusters = new JCheckBox("ShowCluster");
		clusters.setSelected(false);
		clusters.setEnabled(false);
		clusters.addActionListener(this);
		jp.add(clusters);
		
		JLabel lab1 = new JLabel("Threshold1");
		jp.add(lab1);
		th1 = new JTextField(String.valueOf(threshold1));
		th1.addActionListener(this);
		jp.add(th1);
		JLabel lab2 = new JLabel("Threshold2");
		jp.add(lab2);
		th2 = new JTextField(String.valueOf(threshold2));
		th2.addActionListener(this);
		jp.add(th2);
		JLabel lab3 = new JLabel("CutNode");
		jp.add(lab3);
		th3 = new JTextField(String.valueOf(cutNode));
		th3.addActionListener(this);
		jp.add(th3);
		showNodes = new JCheckBox("ShowNodes");
		showNodes.setSelected(false);
		showNodes.setEnabled(false);
		showNodes.addActionListener(this);
		jp.add(showNodes);
		String[] aux = {"Select Node"};
		nodes = new JComboBox<String>(aux);
		nodes.addActionListener(this);
		nodes.setEnabled(nodesMode);
		jp.add(nodes);
		return jp;
	}

	public void genComboNodes() {
		int size = bm.map.nodes.size();
		String[] aux = new String[size + 1];
		aux[0] = "Select Node";
		for (int i = 1; i < size + 1; i++) {
			aux[i] = String.valueOf(i - 1) + " "
					+ bm.map.getNode(i - 1).getSize();
		}
		nodes.setModel(new DefaultComboBoxModel<String>(aux));
		nodes.setSelectedIndex(0);
		//Imprimir lista de nodos
		/*String lst = "";
		for (int i = 0; i < aux.length; i++) {
			lst += aux[i] + "\n";
		}*/

		// FileMethods.saveFile(lst, "NodeList", false);
	}

	public static void main(String[] args) {
		Gui g = new Gui();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		g.setVisible(true);
		g.toFront();
	
		String DATARESUME ;
		
		/*if (true)
			DATARESUME="Th1;Th2;CN;Nodes;Edges;Metric\n";
		else
			DATARESUME="";*/
	
		float incremento = (float) 0.002;
		float th1 = (float) 0.002;
		float th2 = (float) 0.01;
		int vuelta=1;
		
		
		//DecimalFormatSymbols simbolos;
        // Each tag contains a name and probability assigned to it by the recognition engine.
        //System.out.println(String.format("  %s (%.4f)", tag.getName(), tag.getProbability()));
        DecimalFormatSymbols simbol = new DecimalFormatSymbols();
        simbol.setDecimalSeparator('.');
        DecimalFormat formateador = new DecimalFormat("####.######", simbol);
        
		for (int i = 0; i <15; i++) {
			for (int j = 0; j <2; j++) {
				g.bm.setThreshold1(th1);
				g.bm.setThreshold2(th2);
				g.bm.buildMap();
				System.out.printf("i= %d\tj= %d\t Ciclo= %d\t th1= %.4f\t th2= %.4f\n", i,j,vuelta, th1, th2);
				th1+=incremento;
				
						//String.format("%,6f",g.bm.threshold1)	
				float metric = g.bm.map.getMapMetric(g.cm.MaxDistance());
				DATARESUME=formateador.format(g.bm.threshold1)+ ";"
				
						+ formateador.format(g.bm.threshold2)+ ";"
						+g.bm.cutNode+ ";"+g.bm.map.nodes.size()+ ";"
						+g.bm.map.edges.size()+ ";"	
						+g.bm.map.coefA+ ";"	
						+g.bm.map.coefB+ ";"	
						+g.bm.map.coefC+ ";"	
						+g.bm.map.coefD+ ";"	
						+g.bm.map.coefE+ ";"	
						+metric+"\n";
				FileMethods.saveFile(DATARESUME, g.name+"_MetricsData", true);
				//System.out.printf("i= %d\tj= %d\t Ciclo= %d\n", i,j,vuelta);
				++vuelta;
				}
			th2+=0.001;
			th1=(float) 0.002;
		}
		g.setVisible(false);
		g.dispose();
		
		
	/*	
		System.out.println("Width-X= "+g.cm.getMaxWidth());
		System.out.println("Heigth-Y= "+g.cm.getMaxHeight());
		System.out.println("DMAX= " +g.cm.MaxDistance());
		
		g.dispose();
		*/
		
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == process) {
			graphButton.setEnabled(false);
			showNodes.setEnabled(false);
			bm.buildMap();
			graphButton.setEnabled(true);
			showNodes.setEnabled(true);
			genComboNodes();
			mapGenerated = true;
			cm.repaint();
			cm.showNodeDetails();
			cm.showMapInfo();
			String DATARESUME ="Th1;Th2;CN;Nodes;Edges;Metric\n";
			float metric = bm.map.getMapMetric(cm.MaxDistance());
			DATARESUME+=bm.threshold1+ ";"+ bm.threshold2+ ";"+bm.cutNode+ ";"+bm.map.nodes.size()+ ";"+bm.map.edges.size()+ ";"+metric+"\n";
			FileMethods.saveFile(DATARESUME, name+"_MetricsData", true);
			return;
		}
		if (e.getSource() == clusterbt) {
		    // Kmeans
			int k =Integer.parseInt( JOptionPane.showInputDialog("How many clusters?","4"));
			km= new Kmeans(k, bm.dimension, bm.imgTags);
			km.findMeans();
			//mapGenerated = false;
			showCluster=true;
			clusters.setEnabled(true);
			clusters.setSelected(true);
			cm.repaint();
			return;
		}
		
				
		if (e.getSource() == clusterCoefBt) {
			Kmeans km2;
			km2 = new Kmeans(1,bm.dimension, bm.imgTags);
			ArrayList<Float> coef = new ArrayList<Float>();
			int k=1; 
			
			if(k==1)
				FileMethods.saveFile("K;s2\n", "K_Variances_"+name, false);
			
			
			do {
				km2.setK(k);
				Float coefValue =km2.findMeansCoef();
				coef.add(coefValue); 
				FileMethods.saveFile(String.valueOf(k)+";"+String.valueOf(coefValue)+"\n", "K_Variances_"+name, true);
				++k;
				if ((k%100)==0) System.out.println("K="+k);
			}
			while(k<=800);
			
			
			/*			
			try {
				DrawLineChart.viewChart(coef,name);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				*/
			
			
			return;
		}
		
		
		
		if (e.getSource() == originalButton) {
			//showCluster = false;
			original = originalButton.isSelected();
			cm.repaint();
			return;
		}
		if (e.getSource() == graphButton) {
			showMap = graphButton.isSelected();
			cm.repaint();
			return;
		}
		if (e.getSource() == backButton) {
			background = backButton.isSelected();
			cm.repaint();
			return;
		}
		
		if (e.getSource() == clusters) {
			showCluster = clusters.isSelected();
			cm.repaint();
			return;
		}
		
		if (e.getSource() == th1) {
			threshold1 = Double.parseDouble(th1.getText());
			bm.setThreshold1(threshold1);
			cm.repaint();
			return;
		}
		if (e.getSource() == th2) {
			threshold2 = Double.parseDouble(th2.getText());
			bm.setThreshold2(threshold2);
			cm.repaint();
			return;
		}
		if (e.getSource() == th3) {
			System.out.println(th3.getText());
			bm.setCutNode(Integer.parseInt(th3.getText()));
			if (bm.map != null)
				bm.map.setWeights(Integer.parseInt(th3.getText()));
			cm.repaint();
			return;
		}
		if (e.getSource() == showNodes) {
			nodesMode = showNodes.isSelected();
			selectedNode = -1;
			nodes.setEnabled(true);
			cm.repaint();
			return;
		}

		if (e.getSource() == nodes) {
			if (((String) nodes.getSelectedItem()).equals("Select Node")) {
				selectedNode = -1;
			} else {
				selectedNode = Integer.valueOf(
						((String) nodes.getSelectedItem()).split(" ")[0]);
				selectNodeChanged = true;
				cm.repaint();
			}
			return;
		}
	}

}
