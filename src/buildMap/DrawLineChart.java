package buildMap;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class DrawLineChart {

	private static XYDataset fillDataset(ArrayList<Float> data) {
		// se declaran las series y se llenan los datos
		XYSeries sIngresos = new XYSeries("σ²");
		int i = 1;
		for (Iterator<Float> iterator = data.iterator(); iterator.hasNext(); ++i) {
			Float dataValue = iterator.next();
			sIngresos.add(i, dataValue);

		}
		XYSeriesCollection xyseriescollection = new XYSeriesCollection();
		xyseriescollection.addSeries(sIngresos);

		return xyseriescollection;
	}
	
	public static void viewChart(ArrayList<Float> data, String name) throws IOException{

		// se declara el grafico XY Lineal
		XYDataset xydataset = fillDataset(data);
		JFreeChart linealChart = ChartFactory.createXYLineChart("K-vs-σ² "+name, "K", "σ²", xydataset, PlotOrientation.VERTICAL,
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
		
        //String title = name+".png" ;
        
        File fileChart = new File( "resultados/"+name+".png" );
         ChartUtilities.saveChartAsPNG(fileChart , linealChart , 1000 , 750 );
		
	}
	
	
	

}// -->fin clase