package in.bbat.presenter.internal;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.abstrakt.presenter.run.manager.DeviceLogListener;
import in.BBAT.abstrakt.presenter.run.model.TestRunCase;
import in.BBAT.presenter.labelProviders.TestRunnerLableProvider;
import in.BBAT.testRunner.runner.TestRunner;
import in.BBAT.testRunner.runner.UiAutoTestCaseJar;
import in.bbat.presenter.TestCaseExecutionListener;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;

public class DeviceTestRun {

	private AndroidDevice device;
	private CTabFolder testRunFolder;
	private TableViewer viewer;
	private CTabItem testRunItem;
	private List<TestRunCase> testRunCases = new ArrayList<TestRunCase>();

	public DeviceTestRun(AndroidDevice device,CTabFolder mainTabFolder) {
		this.setDevice(device);
		this.setTabFolder(mainTabFolder);
	}

	public AndroidDevice getDevice() {
		return device;
	}

	public void setDevice(AndroidDevice device) {
		this.device = device;
	}

	public CTabFolder getTabFolder() {
		return testRunFolder;
	}

	public void setTabFolder(CTabFolder tabFolder) {
		this.testRunFolder = tabFolder;
	}

	public void createTab(){
		clear();
		testRunItem = new CTabItem(testRunFolder, SWT.None);
		testRunItem.setText(device.getName());
		Composite comp = new Composite(testRunFolder, SWT.None);


		viewer = new TableViewer(comp, SWT.MULTI | SWT.H_SCROLL| SWT.V_SCROLL);
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.getTable().setLinesVisible(true);
		viewer.getTable().setHeaderVisible(true);
		createColumns(comp, viewer);
		viewer.setLabelProvider(new TestRunnerLableProvider());
		try {
			viewer.setInput(getTestRunCases());
		} catch (Exception e) {
			e.printStackTrace();
		}

		testRunItem.setControl(comp);
		testRunFolder.setSelection(0);
	}

	public void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Project","TestSuite","TestCase","Status" };
		int[] bounds = { 25,25,25,25 };

		TableColumnLayout layout = new TableColumnLayout();
		parent.setLayout(layout);

		TableViewerColumn col =createTableViewerColumn(viewer,null, titles[0],bounds[0]);
		layout.setColumnData(col.getColumn(), new ColumnWeightData(bounds[0]));

		TableViewerColumn col1 = createTableViewerColumn(viewer, null, titles[1],bounds[1]);
		layout.setColumnData(col1.getColumn(), new ColumnWeightData(bounds[1]));

		TableViewerColumn col2 = createTableViewerColumn(viewer, null, titles[2],bounds[2]);
		layout.setColumnData(col2.getColumn(), new ColumnWeightData(bounds[2]));

		TableViewerColumn col3 = createTableViewerColumn(viewer, null, titles[3],bounds[3]);
		layout.setColumnData(col3.getColumn(), new ColumnWeightData(bounds[3]));

	}

	private TableViewerColumn createTableViewerColumn(TableViewer viewer,
			Image imag, String titles, int bounds) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(titles);
		column.setToolTipText(titles);
		column.setImage(imag);
		column.setWidth(bounds);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	public List<TestRunCase> getTestRunCases() {
		return testRunCases;
	}

	public void setTestRunCases(List<TestRunCase> testRunCases) {
		this.testRunCases = testRunCases;
	}

	public List<String> getTestScriptPaths() {
		List<String> testScriptPaths = new ArrayList<String>();
		for (TestRunCase testRunCase : testRunCases) {
			if(!testScriptPaths.contains(testRunCase.getTestcase().getTestScriptPath()))
				testScriptPaths.add(testRunCase.getTestcase().getTestScriptPath());
		}
		return testScriptPaths;
	}

	public void excute() {

		Job testRunJob = new Job("Execute") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {

				TestRunner runner = new TestRunner(new UiAutoTestCaseJar(getTestScriptPaths()),getDevice().getiDevice());
				for (TestRunCase testRunCase : getTestRunCases()) {
					runner.execute(testRunCase.getTestcase().getName(), new TestCaseExecutionListener(testRunCase, DeviceTestRun.this), new DeviceLogListener(testRunCase));
				}
				return Status.OK_STATUS;
			}
		};
		testRunJob.schedule();
	}

	public void clear()
	{
		if(testRunItem!=null)
			testRunItem.dispose();
	}

	public void refresh() {
		viewer.refresh();		
	}
}
