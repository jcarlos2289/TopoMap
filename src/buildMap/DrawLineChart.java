package buildMap;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class DrawLineChart {

	private static XYDataset fillDataset(ArrayList<Float> data) {
		// se declaran las series y se llenan los datos
		XYSeries sIngresos = new XYSeries("Variance");
		int i = 1;
		for (Iterator<Float> iterator = data.iterator(); iterator.hasNext(); ++i) {
			Float dataValue = iterator.next();
			sIngresos.add(i, dataValue);

		}
		XYSeriesCollection xyseriescollection = new XYSeriesCollection();
		xyseriescollection.addSeries(sIngresos);

		return xyseriescollection;
	}
	
	public static void viewChart(ArrayList<Float> data){

		// se declara el grafico XY Lineal
		XYDataset xydataset = fillDataset(data);
		JFreeChart linealChart = ChartFactory.createXYLineChart("K-vs-σ²", "K", "σ²", xydataset, PlotOrientation.VERTICAL,
				true, true, false);

		// personalización del grafico
		XYPlot xyplot = (XYPlot) linealChart.getPlot();
		xyplot.setBackgroundPaint(Color.white);
		xyplot.setDomainGridlinePaint(Color.BLACK);
		xyplot.setRangeGridlinePaint(Color.BLACK);

	
		 final ChartPanel chartPanel = new ChartPanel(linealChart);
	     chartPanel.setPreferredSize(new Dimension(500, 270));
	        
	     JFrame graf = new JFrame("Lineal Chart");
         graf.setContentPane(chartPanel);
         graf.setSize(700, 400);
         graf.setVisible(true);
		
		
	}
	
	
	

}// -->fin clase