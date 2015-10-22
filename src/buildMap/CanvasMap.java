package buildMap;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;

public class CanvasMap extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1366050590724868148L;
	Gui gui;
	ImageIcon img2;
	double xmean=3995.859622691936, ymean=-7172.739459442184;
	double zoomFactor=2.8;
	double xdesp, ydesp;
	int radius=10;
	JDialog tags=null, nodeInfo=null, graf=null;
	
	public CanvasMap (Gui ig) {
		img2 = new ImageIcon(Toolkit.getDefaultToolkit().getImage("newcollege.jpg"));
		gui=ig;
		xdesp=(gui.width/2.0)-65;
		ydesp=(gui.height/2.0)-74;
	    addMouseListener(this);
	}
	
	private double distance (MouseEvent evt, Node n) {
		double dist=0.0;
		dist = Math.pow(evt.getX()-(int)(zoomFactor*(n.representative.xcoord-xmean)+xdesp+radius), 2.0);
		dist += Math.pow(evt.getY()-(int)(-zoomFactor*(n.representative.ycoord-ymean)+ydesp+radius), 2.0);
		return Math.sqrt(dist);
	}

	public void paint(Graphics g) {
		int x,y, xAnt=0, yAnt=0;
		boolean firstTime=true;
		Graphics2D g2d = (Graphics2D) g;
		
		paintComponent(g);
		g.setColor(new Color(255,255,255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		if (gui.background)
			img2.paintIcon(this, g, 0, 0);
		g.setColor(new Color(255,0,0));
		g2d.setStroke(new BasicStroke(2.0f));
		if (gui.original) {
			for (int i=0; i<gui.bm.imgTags.size(); i++) {
				if (gui.bm.imgTags.get(i).xcoord!=-1) {
					x=(int)(zoomFactor*(gui.bm.imgTags.get(i).xcoord-xmean)+xdesp);
					y=(int)(-zoomFactor*(gui.bm.imgTags.get(i).ycoord-ymean)+ydesp);
					if (firstTime) {
						xAnt=x;
						yAnt=y;
						firstTime=false;
					}
					if (Math.sqrt(Math.pow(x-xAnt, 2.0)+Math.pow(y-yAnt,2.0)) < 30.0) 
						g2d.drawLine(xAnt, yAnt, x, y);
					xAnt=x;
					yAnt=y;
				}				
			}
		}
		g.setColor(new Color(0,0,255));
		if (gui.mapGenerated && gui.showMap) {
			if (gui.nodesMode && gui.selectedNode!=-1) {
				Node selectn = gui.bm.map.nodes.get(gui.selectedNode);
				for (ImageTags img:selectn.images) {
					x=(int)(zoomFactor*(img.xcoord-xmean)+xdesp);
					y=(int)(-zoomFactor*(img.ycoord-ymean)+ydesp);
			        g.drawOval(x, y, radius, radius);
				}
				g.setColor(new Color(0,255,255));
				x=(int)(zoomFactor*(selectn.representative.xcoord-xmean)+xdesp);
				y=(int)(-zoomFactor*(selectn.representative.ycoord-ymean)+ydesp);
		        g.drawOval(x, y, radius, radius);
				g.setColor(new Color(255,0,255));
				for (Map.Edge e:gui.bm.map.edges) {
					if (e.a==selectn || e.b==selectn) {
						xAnt=(int)(zoomFactor*(e.a.representative.xcoord-xmean)+xdesp);
						yAnt=(int)(-zoomFactor*(e.a.representative.ycoord-ymean)+ydesp);
						x=(int)(zoomFactor*(e.b.representative.xcoord-xmean)+xdesp);
						y=(int)(-zoomFactor*(e.b.representative.ycoord-ymean)+ydesp);
						g2d.drawLine(xAnt+radius/2, yAnt+radius/2, x+radius/2, y+radius/2);
					}
				}
				if (gui.selectNodeChanged) {
					if (tags!=null) {
						 tags.dispose();
	                     nodeInfo.dispose();
	                     graf.dispose();
	                     tags=null;
	                     nodeInfo=null;
	                     graf=null;
					}
					showInfo(selectn);
					gui.selectNodeChanged=false;
				}
			}
			else {
				for (Node n:gui.bm.map.nodes) {
					x=(int)(zoomFactor*(n.representative.xcoord-xmean)+xdesp);
					y=(int)(-zoomFactor*(n.representative.ycoord-ymean)+ydesp);
			        g.drawOval(x, y, radius, radius);
				}
				for (Map.Edge e:gui.bm.map.edges) {
					xAnt=(int)(zoomFactor*(e.a.representative.xcoord-xmean)+xdesp);
					yAnt=(int)(-zoomFactor*(e.a.representative.ycoord-ymean)+ydesp);
					x=(int)(zoomFactor*(e.b.representative.xcoord-xmean)+xdesp);
					y=(int)(-zoomFactor*(e.b.representative.ycoord-ymean)+ydesp);
					g2d.drawLine(xAnt+radius/2, yAnt+radius/2, x+radius/2, y+radius/2);
				}
			}
		}
		//else{
			if (gui.showCluster){
				
				int colors[][] = new int[gui.km.k][3];
				
				for (int i = 0; i <colors.length ; i++) {
					for (int j = 0; j < colors[i].length; j++) {
						colors [i][j] = (int)(Math.random()*255);
						//colors [i][1] = (int)(Math.random()*255);
						//colors [i][2] = (int)(Math.random()*255);
					}
					}
				
				
				
						
				for (int j = 0; j <gui.km.obtained.size(); j++) {
					
					Point point = gui.km.obtained.get(j);
					g.setColor(new Color(colors[gui.km.near.get(j)][0],colors[gui.km.near.get(j)][1],colors[gui.km.near.get(j)][2]));
					x=(int)(zoomFactor*(point.xcoord-xmean)+xdesp);
					y=(int)(-zoomFactor*(point.ycoord-ymean)+ydesp);
			        g.drawOval(x, y, 2, 2);
					
					
				}
				/*for (Iterator<Point> iterator = gui.km.obtained.iterator(); iterator.hasNext();) {
					Point point = iterator.next();
					x=(int)(zoomFactor*(point.xcoord-xmean)+xdesp);
					y=(int)(-zoomFactor*(point.ycoord-ymean)+ydesp);
			        g.drawOval(x, y, radius, radius);
				}*/
				
				
				
				
				int c = 0;
				for (Point point : gui.km.means) {
					
					g.setColor(new Color(colors[c][0],colors[c][1],colors[c][2]));
					x=(int)(zoomFactor*(point.xcoord-xmean)+xdesp);
					y=(int)(-zoomFactor*(point.ycoord-ymean)+ydesp);
			        g.drawOval(x, y, radius, radius+3);
			        g.fillOval(x, y, radius, radius+3);
			        ++c;
			        			        
					//System.out.println("X = " + point.xcoord +" y = " + point.ycoord);
					//System.out.println("X = " + x +" y = " + y);
			     
				}
				
				
				g.setColor(new Color(0,0,255));
				//g.draw3DRect(80, 100, 30 ,15, true);
				g.drawRect(98, 85, 50 ,20);
				g.fillRect(98, 85, 50 ,20);
				g.setColor(new Color(255,255,255));
				g.drawString("k = " + gui.km.k, 100, 100);
				//g.drawString(str, xAnt, yAnt);
				
				
				
				
				
				
			}
			
		//}
	}
	
	public void showInfo (Node sel) {
		JLabel textArea;
		JScrollPane scroll;
		
		gui.selectedNode=gui.bm.map.nodes.indexOf(sel);
		gui.showNodes.setSelected(true);
		gui.nodesMode=true;
		gui.nodes.setEnabled(true);
		gui.nodes.setSelectedIndex(gui.bm.map.nodes.indexOf(sel)+1);
		
		tags=new JDialog(gui);
		tags.setTitle("Tags");
		tags.setSize(450, 400);
		textArea= new JLabel(sel.getTextTags());
		tags.add(textArea);
		tags.setLocation(1050,0);
		tags.setVisible(true);
		nodeInfo=new JDialog(gui);
		nodeInfo.setTitle("Images");
		nodeInfo.setSize(450, 300);
		textArea= new JLabel(sel.getNodeInfo(gui.bm.map.edges, gui.bm.map.nodes));
		scroll=new JScrollPane(textArea);
		nodeInfo.add(scroll);
		nodeInfo.setLocation(1050,400);
		nodeInfo.setVisible(true);
		//FileMethods.saveFile(sel.getNodesContent(), "Node_"+String.valueOf(gui.bm.map.nodes.indexOf(sel)), false);
		createChart(sel);
		
	}
	
	
	public void  createChart(Node sel){
		
		   final CategoryDataset dataset = sel.getDataset();
		      
	        final JFreeChart chart = ChartFactory.createBarChart(
	                "Tags Probability Mean",         // chart title
	                "",               // domain axis label
	                "",                  // range axis label
	                dataset,                  // data
	                PlotOrientation.HORIZONTAL, // orientation
	                false,                     // include legend
	                true,                     // tooltips?
	                false                     // URLs?
	            );

	            // set the background color for the chart...
	            chart.setBackgroundPaint(Color.white);

	            // get a reference to the plot for further customisation...
	            final CategoryPlot plot = chart.getCategoryPlot();
	            plot.setBackgroundPaint(Color.lightGray);
	            plot.setDomainGridlinePaint(Color.white);
	            plot.setRangeGridlinePaint(Color.white);

	            // set the range axis to display integers only...
	            final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
	                  
	        final ChartPanel chartPanel = new ChartPanel(chart);
	        chartPanel.setPreferredSize(new Dimension(500, 270));
	        
	        graf = new JDialog(gui);
            graf.setContentPane(chartPanel);
            graf.setLocation(1050,650);
            graf.setSize(700, 400);
            graf.setTitle("Tag Histogram");
            graf.setVisible(true);
		
		
	}
	
	
	
	
	public void mousePressed(MouseEvent evt) {
		Node sel=null;
		
		if (gui.mapGenerated && gui.showMap) {
			if (tags!=null) {
				tags.dispose();
				nodeInfo.dispose();
				graf.dispose();
				gui.nodes.setSelectedIndex(0);
				tags=null;
				nodeInfo=null;
				graf=null;
			}
			for (Node n:gui.bm.map.nodes) {
				if (distance(evt, n) < radius) {
					sel=n;
					System.out.print(gui.bm.map.nodes.indexOf(n)+" ");
				}
			}
			if (sel!=null)  {
				System.out.println(" ");
				showInfo(sel);
			}
		}
	}
	
	
	

	public void mouseClicked (MouseEvent evt) {
	}
	public void mouseReleased(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
}
