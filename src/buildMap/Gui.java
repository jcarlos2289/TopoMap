package buildMap;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

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

	public Gui() {
		threshold1 = 2;
		threshold2 = 4;
		cutNode = 20;
		bm = new BuildMap(threshold1, threshold2, cutNode);
		// bm.readTags("/Users/miguel/Dropbox/Investigacion/Desarrollo/MapaTopologico/tagsNewCollege/NewCollegeTags/PanoStitchOutput_LisaNewCollegeNov3_");
		bm.readTags("/home/jcarlos2289/workspacejava/tagsNewCollege/NewCollegePlaces_AlexNet/NewCollege_",0.000000001,8127,"output.data",205);
		//bm.readTags("/home/jcarlos2289/Documentos/tagsNewCollege/NewCollegePlaces_AlexNet/NewCollege_",0.000000001);

		getContentPane().setLayout(new BorderLayout());
		setSize(width, height);
		cm = new CanvasMap(this);
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
			return;
		}
		if (e.getSource() == clusterbt) {
		    // Kmeans
			int k =Integer.parseInt( JOptionPane.showInputDialog("How many clusters?","4"));
			km= new Kmeans(k, 205, bm.imgTags);
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
			km2 = new Kmeans(1,6, bm.imgTags);
			ArrayList<Float> coef = new ArrayList<Float>();
			int k=1; 
			
			do {
				km2.setK(k);
				coef.add(km2.findMeansCoef()); 
				++k;
				if ((k%100)==0) System.out.println("K="+k);
			}
			while(k<1001);
			
			
			
			FileMethods.saveFile("K\tVariance\n", "K_Variances", false);
			String  dataResults="";
			
			
			dataResults+="K\tVariance\n";
			int h = 1;
			for (Iterator<Float> iterator = coef.iterator(); iterator.hasNext();++h) {
				Float coefValue =  iterator.next();
				FileMethods.saveFile(String.valueOf(h)+"\t"+String.valueOf(coefValue)+"\n", "K_Variances", true);
				dataResults+= String.valueOf(h)+"\t"+String.valueOf(coefValue)+"\n";
				
			}
			System.out.println(dataResults);
			//JTextArea  jtA = new JTextArea();
			//jtA.setText(dataResults);
			//JScrollPane scroll=new JScrollPane(jtA);
			
			//JOptionPane.showMessageDialog(this, scroll);
			
			DrawLineChart.viewChart(coef);
				
			
			
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
