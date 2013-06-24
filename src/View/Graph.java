package View;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/**
 * 
 * @author Bernhard Hermes
 * Gibt Information über den Verlauf der Größe eines Artikels wieder.
 */
public class Graph {
	private JPanel panel = new JPanel();
	private JLabel label = new JLabel("Artikel:");
	private JButton button = new JButton("Go");
	private JComboBox<String> article = new JComboBox<String>(getArticles());
	private DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	private JFreeChart chart;
	private ChartPanel chartPanel;

	public Graph() {
		init();
	}

	private void search() {
		dataset.clear();
		createDataset(dataset);
		chartPanel.repaint();
	}

	private void init() {

		// Maincontainer
		panel.setLayout(new MigLayout("", "[]", "[]"));
		panel.setOpaque(false);
		

		createDataset(dataset);
		chart = createChart(dataset);
		chartPanel = new ChartPanel(chart);

		// chartPanel.setPreferredSize(new Dimension(500, 270));

		article.setPreferredSize(new Dimension(150, article.getHeight()));
		button.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				search();				
			}
		});
		
		panel.add(chartPanel,"span");
		panel.add(label,"span 3");
		panel.add(article);
		panel.add(button);
	}
	
	private String[] getArticles()
	{
		String sql = "Select Artikel from revision group by Artikel";

		String content = SQLPanel.con.getSelectStatement(sql);
		
		ArrayList<String> rtn = new ArrayList<String>();
		
		for (String s: content.split("\n" ))
		{
			rtn.add(s.trim());
		}
		
		rtn.remove(0);
		rtn.remove(0);
		
		return rtn.toArray(new String[rtn.size()]);
	}

	public JPanel getGraph() {
		return panel;
	}

	private void createDataset(DefaultCategoryDataset dataset) {

		// row keys...
		final String series1 = (String) article.getSelectedItem();

		String type1 = "Artikel";

		dataset.addValue(1.0, series1, type1);

		String sql = "Select RevisionID,Groesse from revision where Artikel='"
				+ article.getSelectedItem() + "'";

		String content = SQLPanel.con.getSelectStatement(sql);

		ArrayList<Integer> größe = new ArrayList<Integer>();
		ArrayList<String> revid = new ArrayList<String>();

		String[] line = content.split("\n");

		for (int i = 2; i < line.length; i++) {
			System.out.println(line[i]);
			String[] split = line[i].split("  ");
			revid.add(split[0]);
			größe.add(Integer.parseInt(split[1]));
		}

		for (int i = größe.size() - 1; i >= 0; --i) {
			dataset.addValue(größe.get(i), series1, revid.get(i));
		}

	}

	private JFreeChart createChart(final CategoryDataset dataset) {

		// create the chart...
		final JFreeChart chart = ChartFactory.createLineChart(
				"Revisions Größe", // chart title
				"Revision", // domain axis label
				"Byte", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips
				false // urls
				);

		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
		// final StandardLegend legend = (StandardLegend) chart.getLegend();
		// legend.setDisplaySeriesShapes(true);
		// legend.setShapeScaleX(1.5);
		// legend.setShapeScaleY(1.5);
		// legend.setDisplaySeriesLines(true);

		chart.setBackgroundPaint(Color.white);

		final CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setRangeGridlinePaint(Color.white);

		// customise the range axis...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setAutoRangeIncludesZero(true);

		// ****************************************************************************
		// * JFREECHART DEVELOPER GUIDE *
		// * The JFreeChart Developer Guide, written by David Gilbert, is
		// available *
		// * to purchase from Object Refinery Limited: *
		// * *
		// * http://www.object-refinery.com/jfreechart/guide.html *
		// * *
		// * Sales are used to provide funding for the JFreeChart project -
		// please *
		// * support us so that we can continue developing free software. *
		// ****************************************************************************

		// customise the renderer...
//		final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot
//				.getRenderer();
		// renderer.setDrawShapes(true);

		// OPTIONAL CUSTOMISATION COMPLETED.

		return chart;
	}

}
