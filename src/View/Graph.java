package View;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.Color;
import java.util.ArrayList;

public class Graph {
	private JPanel panel = new JPanel();
	
	
	public Graph()
	{
		init();
	}
	private void init() {
		
		// Maincontainer
				panel.setLayout(new MigLayout("", "[]", "[]"));
				panel.setOpaque(false);
				
		
		final CategoryDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));
		
        panel.add(chartPanel);
	}
	public JPanel getGraph() {
		return panel;
	}
	
	 private CategoryDataset createDataset() {
	        
	        // row keys...
	        final String series1 = "First";

	      

	        // create the dataset...
	        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	        
	        String type1 = "first";

	        dataset.addValue(1.0, series1, type1);
	        
	        String sql = "Select RevisionID,Groesse from revision where Artikel='Berlin'";
	        
	        
	      
	        String content = SQLPanel.con.getSelectStatement(sql);
	        
	        ArrayList<Integer> größe = new ArrayList<Integer>();
	        ArrayList<String> revid = new ArrayList<String>();
	        
	        return dataset;
	                
	    }
	 
	 private JFreeChart createChart(final CategoryDataset dataset) {
	        
	        // create the chart...
	        final JFreeChart chart = ChartFactory.createLineChart(
	            "Line Chart Demo 1",       // chart title
	            "Type",                    // domain axis label
	            "Value",                   // range axis label
	            dataset,                   // data
	            PlotOrientation.VERTICAL,  // orientation
	            true,                      // include legend
	            true,                      // tooltips
	            false                      // urls
	        );

	        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
//	        final StandardLegend legend = (StandardLegend) chart.getLegend();
	  //      legend.setDisplaySeriesShapes(true);
	    //    legend.setShapeScaleX(1.5);
	      //  legend.setShapeScaleY(1.5);
	        //legend.setDisplaySeriesLines(true);

	        chart.setBackgroundPaint(Color.white);

	        final CategoryPlot plot = (CategoryPlot) chart.getPlot();
	        plot.setBackgroundPaint(Color.lightGray);
	        plot.setRangeGridlinePaint(Color.white);

	        // customise the range axis...
	        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	        rangeAxis.setAutoRangeIncludesZero(true);

	        // ****************************************************************************
	        // * JFREECHART DEVELOPER GUIDE                                               *
	        // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
	        // * to purchase from Object Refinery Limited:                                *
	        // *                                                                          *
	        // * http://www.object-refinery.com/jfreechart/guide.html                     *
	        // *                                                                          *
	        // * Sales are used to provide funding for the JFreeChart project - please    * 
	        // * support us so that we can continue developing free software.             *
	        // ****************************************************************************
	        
	        // customise the renderer...
	        final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
//	        renderer.setDrawShapes(true);

	       
	        // OPTIONAL CUSTOMISATION COMPLETED.
	        
	        return chart;
	    }

}
