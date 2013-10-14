package in.bbat.presenter.internal;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;
import in.BBAT.abstrakt.presenter.run.model.TestDeviceRunModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunModel;
import in.BBAT.abstrakt.presenter.run.model.TestStatus;
import in.BBAT.presenter.labelProviders.DeviceTestRunLableProvider;
import in.BBAT.testRunner.runner.TestRunner;
import in.BBAT.testRunner.runner.UiAutoTestCaseJar;
import in.bbat.abstrakt.gui.BBATImageManager;
import in.bbat.presenter.internal.TestRunExecutor.DeviceRunListener;
import in.bbat.presenter.views.tester.AutomatorLogView;
import in.bbat.presenter.views.tester.TestLogView;
import in.bbat.presenter.views.tester.TestRunnerView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class DeviceTestRun {

	private AndroidDevice device;
	private CTabFolder testRunFolder ;
	private TableViewer viewer;
	private CTabItem testRunItem;
	private List<TestCaseModel> testRunCases = new ArrayList<TestCaseModel>();
	private TestDeviceRunModel testDeviceRun;
	private TestRunModel testRun;
	private ArrayList<TestRunInstanceModel> testRunInstances;
	private TestStatus status = TestStatus.NOTEXECUTED;
	private boolean stopped = false;
	private List<IDeviceRunExecutionlistener> listener = new ArrayList<IDeviceRunExecutionlistener>();

	public DeviceTestRun(AndroidDevice device,CTabFolder mainTabFolder) {
		this.setDevice(device);
		this.setTabFolder(mainTabFolder);
	}
	public DeviceTestRun(AndroidDevice device) {
		this(device, TestRunnerView.testRunFolder);
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
		testRunItem = new CTabItem(TestRunnerView.testRunFolder, SWT.None);
		testRunItem.setText(device.getName());
		Composite comp = new Composite(testRunFolder, SWT.None);
		viewer = new TableViewer(comp, SWT.MULTI | SWT.H_SCROLL| SWT.V_SCROLL);
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.getTable().setLinesVisible(true);
		viewer.getTable().setHeaderVisible(true);
		viewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				try {
					TestLogView view  = (TestLogView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestLogView.ID);
					if(view!=null)
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(view);
					Object sel = ((IStructuredSelection)event.getSelection()).getFirstElement();
					((TestRunInstanceModel)sel).setShowLogs(true);

					IViewPart autoLogView =  PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(AutomatorLogView.ID);
					if(autoLogView!= null){
						((AutomatorLogView)autoLogView).setInput((TestRunInstanceModel)sel);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}	
				try {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(TestLogView.ID);
				} catch (PartInitException e) {
					e.printStackTrace();
				}
			}
		});

		createColumns(comp, viewer);
		viewer.setLabelProvider(new DeviceTestRunLableProvider());
		try {
			viewer.setInput(getRunInstances());
		} catch (Exception e) {
			e.printStackTrace();
		}

		testRunItem.setControl(comp);
		testRunFolder.setSelection(testRunItem);
	}

	private List<TestRunInstanceModel> getRunInstances() {
		if(testRunInstances!=null)
			return testRunInstances;
		testRunInstances = new ArrayList<TestRunInstanceModel>();
		createTestDeviceRun();
		for(TestCaseModel caseObj:getTestRunCases()){
			TestRunInstanceModel runInstModel = new TestRunInstanceModel(testDeviceRun,caseObj,TestStatus.NOTEXECUTED.getStatus());
			runInstModel.save();
			testRunInstances.add(runInstModel);
		}
		return testRunInstances;
	}
	private void createTestDeviceRun() {
		this.testDeviceRun = new TestDeviceRunModel(testRun,device);
		testDeviceRun.save();
	}

	public void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Project","TestSuite","TestCase","Status" };
		int[] bounds = { 25,25,25,25 };

		TableColumnLayout layout = new TableColumnLayout();
		parent.setLayout(layout);

		TableViewerColumn col =createTableViewerColumn(viewer,BBATImageManager.getInstance().getImage(BBATImageManager.PROJECT_GIF_16), titles[0],bounds[0]);
		layout.setColumnData(col.getColumn(), new ColumnWeightData(bounds[0]));

		TableViewerColumn col1 = createTableViewerColumn(viewer, BBATImageManager.getInstance().getImage(BBATImageManager.TESTSUITE_GIF_16), titles[1],bounds[1]);
		layout.setColumnData(col1.getColumn(), new ColumnWeightData(bounds[1]));

		TableViewerColumn col2 = createTableViewerColumn(viewer, BBATImageManager.getInstance().getImage(BBATImageManager.TESTCASE_GIF_16), titles[2],bounds[2]);
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

	public List<TestCaseModel> getTestRunCases() {
		return testRunCases;
	}

	public void setTestRunCases(List<TestCaseModel> testRunCases) {
		this.testRunCases = testRunCases;
	}

	public List<String> getTestScriptPaths() {
		List<String> testScriptPaths = new ArrayList<String>();
		for (TestCaseModel testRunCase : testRunCases) {
			if(!testScriptPaths.contains(testRunCase.getTestScriptPath()))
				testScriptPaths.add(testRunCase.getTestScriptPath());
		}
		return testScriptPaths;
	}

	public void execute(final UiAutoTestCaseJar jar) {
		for(IDeviceRunExecutionlistener l :listener){
			l.deviceRunExecutionStarted(DeviceTestRun.this);
		}
		setStopped(false);
		updateStatus(TestStatus.EXECUTING);
		testDeviceRun.setStartTime(System.currentTimeMillis());
		testDeviceRun.update();
		Job testRunJob = new Job("Execute") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				
				TestRunner runner = new TestRunner(jar,getDevice().getiDevice());
				for (TestRunInstanceModel testRunCase : getRunInstances()) {
					if(isStopped()){
						return Status.OK_STATUS;
					}
					testRunCase.setStartTime(System.currentTimeMillis());
					runner.execute(testRunCase.getTestCaseModel().getName(), new TestCaseExecutionListener(testRunCase, DeviceTestRun.this), new DeviceLogListener(testRunCase),new UIAutomatorOutputListener(testRunCase));
					testRunCase.setEndTime(System.currentTimeMillis());
					testRunCase.update();
				}
				testDeviceRun.setEndTime(System.currentTimeMillis());
				testDeviceRun.update();
				testRun.setEndTime(new Timestamp(System.currentTimeMillis()));
				testRun.update();
				updateStatus(TestStatus.EXECUTED);

				return Status.OK_STATUS;
			}

		};
		testRunJob.schedule();
		testRunJob.addJobChangeListener(new IJobChangeListener() {

			@Override
			public void sleeping(IJobChangeEvent event) {

			}

			@Override
			public void scheduled(IJobChangeEvent event) {

			}

			@Override
			public void running(IJobChangeEvent event) {

			}

			@Override
			public void done(IJobChangeEvent event) {
				for(IDeviceRunExecutionlistener l :listener){
					l.deviceRunExecutionCompleted(DeviceTestRun.this);
				}
			}

			@Override
			public void awake(IJobChangeEvent event) {

			}

			@Override
			public void aboutToRun(IJobChangeEvent event) {

			}
		});
	}


	private void updateStatus(TestStatus status) {
		setStatus(status);
		testDeviceRun.setStatus(getStatus());
		testDeviceRun.update();
		refrestTestRunnerView();		
	}
	private void refrestTestRunnerView() {
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				IViewPart autoLogView =  PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestRunnerView.ID);
				if(autoLogView!= null){
					try {
						((TestRunnerView)autoLogView).refresh();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});
	}

	public void clear()
	{
		if(testRunItem!=null)
			testRunItem.dispose();
		testRunInstances= null;
	}

	public void refresh() {
		viewer.refresh();		
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DeviceTestRun){
			((DeviceTestRun) obj).getDevice().equals(getDevice());
			return true;
		}
		return false ;
	}

	@Override
	public int hashCode() {
		return getDevice().hashCode();
	}

	public void focus() {
		if(testRunItem != null)
			testRunFolder.setSelection(testRunItem);
	}

	public void setTestRun(TestRunModel testRun) {
		this.testRun = testRun;
	}

	public TestStatus getStatus() {
		return status;
	}

	public void setStatus(TestStatus status) {
		this.status = status;
	}

	public void stop(){
		setStopped(true);
	}
	public boolean isStopped() {
		return stopped;
	}
	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}
	public void addListener(IDeviceRunExecutionlistener listener) {
		this.listener.add(listener);		
	}
}
