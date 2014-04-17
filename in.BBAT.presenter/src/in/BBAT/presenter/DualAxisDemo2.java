/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2004, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * ------------------
 * DualAxisDemo2.java
 * ------------------
 * (C) Copyright 2002-2004, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: DualAxisDemo2.java,v 1.17 2004/05/21 10:09:10 mungady Exp $
 *
 * Changes
 * -------
 * 19-Nov-2002 : Version 1 (DG);
 * 27-Apr-2004 : Updated for changes to the XYPlot class (DG);
 *
 */

package in.BBAT.presenter;

import in.BBAT.data.model.Entities.CpuUsageEntity;
import in.BBAT.data.model.Entities.MemoryEntity;
import in.BBAT.presenter.views.tester.ScreenShotView;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * An example of a time series chart.  For the most part, default settings are used, except that
 * the renderer is modified to show filled shapes (as well as lines) at each data point.
 *
 */
public class DualAxisDemo2 extends ApplicationFrame {

	/**
	 * A demonstration application showing how to create a time series chart with dual axes.
	 *
	 * @param title  the frame title.
	 */
	public DualAxisDemo2(final String title) {

		super(title);

		// create a title...
		final String chartTitle = "Memory & CPU usage";
		final XYDataset dataset = createDataset1();

		final JFreeChart chart = ChartFactory.createTimeSeriesChart(
				chartTitle, 
				"Time", 
				"Memory usage (kB)",
				dataset, 
				true, 
				true, 
				false
				);

		//      final StandardLegend legend = (StandardLegend) chart.getLegend();
		//    legend.setDisplaySeriesShapes(true);

		final XYPlot plot = chart.getXYPlot();
		final NumberAxis axis2 = new NumberAxis("CPU usage (%)");
		axis2.setAutoRangeIncludesZero(false);
		plot.setRangeAxis(1, axis2);
		plot.setDataset(1, createDataset2());
		plot.mapDatasetToRangeAxis(1, 1);
		final XYItemRenderer renderer = plot.getRenderer();
		renderer.setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());
		if (renderer instanceof StandardXYItemRenderer) {
			final StandardXYItemRenderer rr = (StandardXYItemRenderer) renderer;
			//            rr.setPlotShapes(true);
			rr.setShapesFilled(true);
		}

		final StandardXYItemRenderer renderer2 = new StandardXYItemRenderer();
		renderer2.setSeriesPaint(0, Color.black);
		//        renderer2.setPlotShapes(true);
		renderer.setToolTipGenerator(StandardXYToolTipGenerator.getTimeSeriesInstance());
		plot.setRenderer(1, renderer2);

		final DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("hh:mm:ss"));

		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(chartPanel);

	}

	/**
	 * Creates a sample dataset.
	 *
	 * @return The dataset.
	 */
	private XYDataset createDataset2() {

		final TimeSeries s1 = new TimeSeries("CPU", Millisecond.class);
		for(CpuUsageEntity ent : ScreenShotView.testCase.getCpuUsageValues()){
			s1.addOrUpdate(new Millisecond(new Date(ent.getTime())), ent.getPercent());
		}

		final TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(s1);

		return dataset;

	}

	/**
	 * Creates a sample dataset.
	 *
	 * @return The dataset.
	 */
	private XYDataset createDataset1() {


		final TimeSeries s2 = new TimeSeries("Memory", Millisecond.class);
		for(MemoryEntity ent : ScreenShotView.testCase.getMemoryUsageValues()){
			s2.addOrUpdate(new Millisecond(new Date(ent.getTime())), ent.getPercent());
		}



		final TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(s2);

		return dataset;

	}

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

	/**
	 * Starting point for the demonstration application.
	 *
	 * @param args  ignored.
	 */
	public static void main(final String[] args) {

		final DualAxisDemo2 demo = new DualAxisDemo2("Memory & CPU Usage");
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);

	}

}
