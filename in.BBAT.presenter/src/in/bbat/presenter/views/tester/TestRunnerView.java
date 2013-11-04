package in.bbat.presenter.views.tester;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.abstrakt.presenter.run.model.TestRunCaseModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.BBAT.abstrakt.presenter.run.model.TestStatus;
import in.BBAT.presenter.DND.listeners.TestCaseDropListener;
import in.BBAT.presenter.DND.listeners.TestRunDropListener;
import in.BBAT.presenter.labelProviders.DeviceTestRunLableProvider;
import in.BBAT.presenter.labelProviders.TestRunInfoLabelProvider;
import in.BBAT.presenter.labelProviders.TestRunnerLableProvider;
import in.bbat.abstrakt.gui.BBATImageManager;
import in.bbat.presenter.internal.DeviceTestRun;
import in.bbat.presenter.internal.TestRunExecutionManager;
import in.bbat.presenter.views.BBATViewPart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.android.ddmlib.logcat.LogCatMessage;

public class TestRunnerView extends BBATViewPart {

	public static final String ID = "in.BBAT.presenter.tester.TestRunnerView";
	private TableViewer commonTestCaseViewer;
	private TreeViewer testDeviceViewer;
	public static CTabFolder testRunFolder;
	private CTabItem testRunItem;
	private TableViewer deviceRunInfoViewer;
	private CTabItem deviceRunItem;
	private TableViewer deviceTestCaseViewer;
	private DeviceTestRun deviceTestRun;

	public TestRunnerView() {
	}

	@Override
	public void refresh() throws Exception {
		commonTestCaseViewer.refresh();
		if(testDeviceViewer!=null)
			testDeviceViewer.refresh();
		if(deviceRunInfoViewer!=null)
			deviceRunInfoViewer.refresh();
		if(deviceTestCaseViewer !=null)
			deviceTestCaseViewer.refresh();
	}

	@Override
	public ISelection getSelectedElements() {
		return commonTestCaseViewer.getSelection();
	}

	private SashForm createSash(Composite parent) {
		SashForm sash = new SashForm(parent, SWT.HORIZONTAL);
		sash.setLayoutData(new GridData(GridData.FILL_BOTH));
		return sash;
	}
	@Override
	public void createPartControl(Composite parent) {

		SashForm form = createSash(parent);

		createDeviceViewer(form);
		createDeviceRunViewerTabs(form);

		form.setWeights(new int[]{50,50});
		createDropSupport();
	}

	private void createDeviceRunViewerTabs(Composite innerleft) {

		CTabFolder testRunFolder = new CTabFolder(innerleft, SWT.TOP|SWT.BORDER);
		CTabItem runTabItem = new CTabItem(testRunFolder, SWT.None);
		runTabItem.setText("Execution View");
		Composite comp = new Composite(testRunFolder, SWT.BORDER);

		deviceRunInfoViewer = new TableViewer(comp, SWT.MULTI | SWT.H_SCROLL| SWT.V_SCROLL);
		deviceRunInfoViewer.setContentProvider(new ArrayContentProvider());
		deviceRunInfoViewer.getTable().setLinesVisible(true);
		deviceRunInfoViewer.getTable().setHeaderVisible(true);
		deviceRunInfoViewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				try {
					Object sel = ((IStructuredSelection)event.getSelection()).getFirstElement();
					((TestRunInstanceModel)sel).setShowLogs(true);

					if(((TestRunInstanceModel)sel).getStatus().equalsIgnoreCase(TestStatus.NOTEXECUTED.getStatus()))
					{
						return;
					}

					if(!((TestRunInstanceModel)sel).getStatus().equalsIgnoreCase(TestStatus.EXECUTING.getStatus())){
						IViewPart autoLogView =  PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(AutomatorLogView.ID);
						if(autoLogView!= null){
							((AutomatorLogView)autoLogView).setInput((TestRunInstanceModel)sel);
						}
						TestLogView view  = (TestLogView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestLogView.ID);

						if(view != null){
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(view);
						}
						view =  (TestLogView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(TestLogView.ID);
						view.bufferChanged(((TestRunInstanceModel)sel).getDeviceLogsFromDB(), new ArrayList<LogCatMessage>());
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

		createRunColumns(comp, deviceRunInfoViewer);
		deviceRunInfoViewer.setLabelProvider(new TestRunInfoLabelProvider());
		runTabItem.setImage(BBATImageManager.getInstance().getImage(BBATImageManager.EXECUTING));
		runTabItem.setControl(comp);
		testRunFolder.setSelection(runTabItem);

	}

	public void createRunColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "TestCase","TestSuite","Project","Status" };
		int[] bounds = { 25,25,25,25 };

		TableColumnLayout layout = new TableColumnLayout();
		parent.setLayout(layout);

		TableViewerColumn col =createTableViewerColumn(viewer,BBATImageManager.getInstance().getImage(BBATImageManager.TESTCASE_GIF_16), titles[0],bounds[0]);
		layout.setColumnData(col.getColumn(), new ColumnWeightData(bounds[0]));

		TableViewerColumn col1 = createTableViewerColumn(viewer, BBATImageManager.getInstance().getImage(BBATImageManager.TESTSUITE_GIF_16), titles[1],bounds[1]);
		layout.setColumnData(col1.getColumn(), new ColumnWeightData(bounds[1]));

		TableViewerColumn col2 = createTableViewerColumn(viewer, BBATImageManager.getInstance().getImage(BBATImageManager.PROJECT_GIF_16), titles[2],bounds[2]);
		layout.setColumnData(col2.getColumn(), new ColumnWeightData(bounds[2]));

		TableViewerColumn col3 = createTableViewerColumn(viewer, null, titles[3],bounds[3]);
		layout.setColumnData(col3.getColumn(), new ColumnWeightData(bounds[3]));

	}

	private void createTestRunViewer(Composite innerRight) {

		CTabFolder testRunFolder = new CTabFolder(innerRight, SWT.TOP|SWT.BORDER);
		testRunItem = new CTabItem(testRunFolder, SWT.BORDER);
		testRunItem.setText("Common Test Cases");
		testRunItem.setImage(BBATImageManager.getInstance().getImage(BBATImageManager.TESTCASE_GIF_16));
		Composite comp = new Composite(testRunFolder, SWT.BORDER);
		commonTestCaseViewer = new TableViewer(comp, SWT.MULTI | SWT.H_SCROLL| SWT.V_SCROLL);
		commonTestCaseViewer.setContentProvider(new ArrayContentProvider());
		commonTestCaseViewer.getTable().setLinesVisible(true);
		commonTestCaseViewer.getTable().setHeaderVisible(true);
		createColumns(comp, commonTestCaseViewer);
		commonTestCaseViewer.setLabelProvider(new TestRunnerLableProvider());
		try {
			commonTestCaseViewer.setInput(TestRunExecutionManager.getInstance().getTestRunCases());
		} catch (Exception e) {
			e.printStackTrace();
		}
		createDropSupportForTestCases();
		getViewSite().setSelectionProvider(commonTestCaseViewer);
		addMenuManager(commonTestCaseViewer);
		testRunItem.setControl(comp);
		testRunFolder.setSelection(0);
	}

	public void createTestDeviceTab(AndroidDevice testDevice, List<TestRunInstanceModel> input){
		CTabItem testRunItem = new CTabItem(testRunFolder, SWT.BORDER);
		testRunItem.setText("Test Devices");
		Composite comp = new Composite(testRunFolder, SWT.BORDER);
		Button button = new Button(comp, SWT.PUSH);
		button.setText("Hello");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		testRunItem.setControl(comp);

	}

	private void createDeviceViewer(Composite innerLeft) {

		SashForm form = new SashForm(innerLeft, SWT.VERTICAL);
		final CTabFolder tabFolder = new CTabFolder(form, SWT.TOP|SWT.BORDER);
		CTabItem testRunItem = new CTabItem(tabFolder, SWT.BORDER);
		testRunItem.setText("Test Devices");
		testRunItem.setImage(BBATImageManager.getInstance().getImage(BBATImageManager.ANDROID_DEVICE));
		Composite comp = new Composite(tabFolder, SWT.BORDER);
		testDeviceViewer = new TreeViewer(comp, SWT.H_SCROLL| SWT.V_SCROLL);
		testDeviceViewer.getTree().setHeaderVisible(true);
		testDeviceViewer.getTree().setLinesVisible(true);
		testDeviceViewer.setContentProvider(new DeviceTestRunContentProvider());
		testDeviceViewer.setLabelProvider(new DeviceTestRunLableProvider());
		testDeviceViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection  =(IStructuredSelection) event.getSelection();
				Object obj = selection.getFirstElement();
				if(obj  instanceof DeviceTestRun){
					DeviceTestRun run = (DeviceTestRun) obj;
					setRunViewerInput(run);
					createDeviceItem(tabFolder,run);
				}
			}
		});
		createDeviceRunColumns(comp);
		testDeviceViewer.setInput(TestRunExecutionManager.getInstance());
		testRunItem.setControl(comp);
		tabFolder.setSelection(testRunItem);
		createDropSupportForDevice();
		createTestRunViewer(form);
		form.setWeights(new int[]{50,50});

	}

	private void createDeviceItem(CTabFolder tabFolder,DeviceTestRun run) {
		if(deviceRunItem!=null){
			deviceRunItem.dispose();
			deviceTestRun =null;
		}
		deviceTestRun = run;
		deviceRunItem = new CTabItem(tabFolder, SWT.BORDER);
		deviceRunItem.setText(run.getDevice().getName());
		Composite comp = new Composite(tabFolder, SWT.BORDER);
		deviceTestCaseViewer = new TableViewer(comp, SWT.MULTI | SWT.H_SCROLL| SWT.V_SCROLL);
		deviceTestCaseViewer.setContentProvider(new ArrayContentProvider());
		deviceTestCaseViewer.getTable().setLinesVisible(true);
		deviceTestCaseViewer.getTable().setHeaderVisible(true);
		createColumns(comp, deviceTestCaseViewer);
		deviceTestCaseViewer.setLabelProvider(new TestRunnerLableProvider());
		try {
			deviceTestCaseViewer.setInput(run.getCases());
		} catch (Exception e) {
			e.printStackTrace();
		}
		addMenu(deviceTestCaseViewer);
		deviceRunItem.setControl(comp);
		tabFolder.setSelection(deviceRunItem);

	}

	public void addMenu(final Viewer viewer){


		final Action removeAction = new Action("Remove") {
			@Override
			public void run() {
				IStructuredSelection sel =(IStructuredSelection) viewer.getSelection();
				List<?> selectedObjs = sel.toList();
				for(Object obj : selectedObjs){
					deviceTestRun.removeCase((TestRunCaseModel) obj);	
				}
				try {
					refresh();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public boolean isEnabled() {
				return !TestRunExecutionManager.getInstance().isExecuting();
			}
		};
		final MenuManager menuManager = new MenuManager();
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(new IMenuListener() {

			@Override
			public void menuAboutToShow(IMenuManager manager) {
				removeAction.setEnabled(removeAction.isEnabled());
				manager.add(removeAction);
			}
		});
		viewer.getControl().setMenu(menuManager.createContextMenu(viewer.getControl()));

	}

	private void createDeviceRunColumns(Composite comp) {
		TreeColumnLayout lay = new TreeColumnLayout();
		comp.setLayout(lay);

		TreeColumn col = new TreeColumn(testDeviceViewer.getTree(), SWT.LEFT);
		col.setText("Device Name");
		col.setImage(BBATImageManager.getInstance().getImage(BBATImageManager.ANDROID_DEVICE));
		lay.setColumnData(col, new ColumnWeightData(36));

		col = new TreeColumn(testDeviceViewer.getTree(), SWT.LEFT);
		col.setText("Count");
		comp.setLayout(lay);
		lay.setColumnData(col, new ColumnWeightData(12));

		col = new TreeColumn(testDeviceViewer.getTree(), SWT.LEFT);
		col.setText("PASS");
		col.setImage(BBATImageManager.getInstance().getImage(BBATImageManager.PASS));
		comp.setLayout(lay);
		lay.setColumnData(col, new ColumnWeightData(12));

		col = new TreeColumn(testDeviceViewer.getTree(), SWT.LEFT);
		col.setText("FAIL");
		col.setImage(BBATImageManager.getInstance().getImage(BBATImageManager.FAIL));
		comp.setLayout(lay);
		lay.setColumnData(col, new ColumnWeightData(12));

		col = new TreeColumn(testDeviceViewer.getTree(), SWT.LEFT);
		col.setText("Status");
		comp.setLayout(lay);
		lay.setColumnData(col, new ColumnWeightData(28));
	}


	private void createDropSupportForDevice() {
		int operations = DND.DROP_COPY | DND.DROP_MOVE;
		Transfer[] transferTypes = new Transfer[] { LocalSelectionTransfer.getTransfer() };
		testDeviceViewer.addDropSupport(operations, transferTypes,new TestRunDropListener(testDeviceViewer));

	}

	private void createDropSupportForTestCases() {
		int operations = DND.DROP_COPY | DND.DROP_MOVE;
		Transfer[] transferTypes = new Transfer[] { LocalSelectionTransfer.getTransfer() };
		commonTestCaseViewer.addDropSupport(operations, transferTypes,new TestCaseDropListener(commonTestCaseViewer));

	}


	public void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "TestCase","TestSuite","Project" };
		int[] bounds = { 25,25,25,25 };

		TableColumnLayout layout = new TableColumnLayout();
		parent.setLayout(layout);

		TableViewerColumn col =createTableViewerColumn(viewer,BBATImageManager.getInstance().getImage(BBATImageManager.TESTCASE_GIF_16), titles[0],bounds[0]);
		layout.setColumnData(col.getColumn(), new ColumnWeightData(bounds[0]));

		TableViewerColumn col1 = createTableViewerColumn(viewer, BBATImageManager.getInstance().getImage(BBATImageManager.TESTSUITE_GIF_16), titles[1],bounds[1]);
		layout.setColumnData(col1.getColumn(), new ColumnWeightData(bounds[1]));

		TableViewerColumn col2 = createTableViewerColumn(viewer, BBATImageManager.getInstance().getImage(BBATImageManager.PROJECT_GIF_16), titles[2],bounds[2]);
		layout.setColumnData(col2.getColumn(), new ColumnWeightData(bounds[2]));

		/*	TableViewerColumn col3 = createTableViewerColumn(viewer, null, titles[3],bounds[3]);
		layout.setColumnData(col3.getColumn(), new ColumnWeightData(bounds[3]));*/

	}

	@Override
	protected void createDropSupport() {
		/*int operations = DND.DROP_COPY | DND.DROP_MOVE;
		Transfer[] transferTypes = new Transfer[] { LocalSelectionTransfer.getTransfer() };
		viewer.addDropSupport(operations, transferTypes,new TestRunDropListener(viewer));*/
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

	public void setRunViewerInput(DeviceTestRun execRun){
		deviceRunInfoViewer.setInput(execRun.getRunInstances());
		deviceRunInfoViewer.refresh();
	}

	public void clearRunViewerInput(){
		deviceRunInfoViewer.setInput(Collections.EMPTY_LIST);
		deviceRunInfoViewer.refresh();
	}

	public void clearDeviceRunItem(){
		if(deviceRunItem!=null){
			deviceRunItem.dispose();
			deviceTestRun =null;
		}
	}
	@Override
	public void setFocus() {
		try {
			refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
