package in.bbat.presenter.views.tester;

import in.BBAT.data.model.Entities.CpuUsageEntity;
import in.BBAT.data.model.Entities.MemoryEntity;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
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

public class MemoryCPUUsageView extends ViewPart {
	
	public static final String ID ="in.BBAT.presenter.tester.MemoryCpuUsageView";
	public MemoryCPUUsageView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
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
		Composite swtAwtComponent = new Composite(parent, SWT.EMBEDDED);
		java.awt.Frame chartFrame = SWT_AWT.new_Frame(swtAwtComponent);
		chartFrame.add(chartPanel);

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

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
