package in.bbat.presenter.views.tester;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.abstrakt.presenter.run.model.TestRunCaseModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.BBAT.abstrakt.presenter.run.model.TestStatus;
import in.BBAT.presenter.DND.listeners.TestCaseDropListener;
import in.BBAT.presenter.DND.listeners.TestDeviceRunCaseDropListener;
import in.BBAT.presenter.DND.listeners.TestRunDropListener;
import in.BBAT.presenter.labelProviders.DeviceTestRunLableProvider;
import in.BBAT.presenter.labelProviders.TestRunInfoLabelProvider;
import in.BBAT.presenter.labelProviders.TestRunnerLableProvider;
import in.BBAT.presenter.wizards.ExportLogsWizard;
import in.bbat.abstrakt.gui.BBATImageManager;
import in.bbat.logger.BBATLogger;
import in.bbat.presenter.internal.DeviceTestRun;
import in.bbat.presenter.internal.TestRunExecutionManager;
import in.bbat.presenter.views.BBATViewPart;
import in.bbat.utils.ITestConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IContributionItem;
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
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.menus.CommandContributionItem;

import com.android.ddmlib.logcat.LogCatMessage;

public class TestRunnerView extends BBATViewPart {

	public static final String ID = "in.BBAT.presenter.tester.TestRunnerView";
	private static final Logger LOG = BBATLogger.getLogger(TestRunnerView.class.getName());
	private TableViewer commonTestCaseViewer;
	private TreeViewer testDeviceViewer;
	public static CTabFolder testRunFolder;
	private CTabItem testRunItem;
	//	private TableViewer executionViewer;
	private CTabItem deviceRunItem;
	private TableViewer deviceTestCaseViewer;
	private DeviceTestRun deviceTestRun;
	private CTabItem executionViewItem;
	private CTabFolder deviceRunTabFolder;

	public TestRunnerView() {
	}

	@Override
	public void refresh() throws Exception {
		commonTestCaseViewer.refresh();
		if(testDeviceViewer!=null)
			testDeviceViewer.refresh();
		/*	if(executionViewer!=null)
			executionViewer.refresh();*/
		if(deviceTestCaseViewer !=null)
			deviceTestCaseViewer.refresh();
		{
			try {
				IViewPart findView = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(ExecutionView.ID);
				if(findView !=null){
					((ExecutionView)findView).refresh();
				}
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		}

		IContributionItem[] items = getViewSite().getActionBars().getToolBarManager().getItems();
		for(IContributionItem item : items){
			if(item instanceof CommandContributionItem){
				item.update();
			}
		}
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
		//		createDeviceRunViewerTabs(form);

		//		form.setWeights(new int[]{50,50});
		createDropSupport();
		getViewSite().setSelectionProvider(commonTestCaseViewer);
	}


	/*private void createDeviceRunViewerTabs(Composite innerleft) {

		CTabFolder testRunFolder = new CTabFolder(innerleft, SWT.TOP|SWT.BORDER);
		testRunFolder.setSimple(false);
		executionViewItem = new CTabItem(testRunFolder, SWT.None);
		executionViewItem.setText("Execution View");
		Composite comp = new Composite(testRunFolder, SWT.NONE);

		executionViewer = new TableViewer(comp, SWT.MULTI | SWT.H_SCROLL| SWT.V_SCROLL);
		executionViewer.setContentProvider(new ArrayContentProvider());
		executionViewer.getTable().setLinesVisible(true);
		executionViewer.getTable().setHeaderVisible(true);
		executionViewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				try {
					final Object sel = ((IStructuredSelection)event.getSelection()).getFirstElement();
					((TestRunInstanceModel)sel).setShowLogs(true);

					if(((TestRunInstanceModel)sel).getStatus().equalsIgnoreCase(TestStatus.NOTEXECUTED.getStatus()))
					{
						return;
					}

					if(!((TestRunInstanceModel)sel).getStatus().equalsIgnoreCase(TestStatus.EXECUTING.getStatus())){
						Display.getDefault().asyncExec(new Runnable() {

							@Override
							public void run() {
								try {
									BBATViewPart.hideView(TestLogView.ID);
									TestLogView view =  (TestLogView) BBATViewPart.openView(TestLogView.ID);
									view.bufferChanged(((TestRunInstanceModel)sel).getDeviceLogsFromDB(), new ArrayList<LogCatMessage>());
								}catch (Exception e) {
									LOG.error(e);
								}

							}
						});
						Display.getDefault().asyncExec(new Runnable() {
							@Override
							public void run() {
								try {
									BBATViewPart.hideView(AutomatorLogView.ID);
									AutomatorLogView autoLogView =  (AutomatorLogView)BBATViewPart.openView(AutomatorLogView.ID);
									if(autoLogView!= null){
										autoLogView.setInput((TestRunInstanceModel)sel);
									}	
								} catch (Exception e) {
									LOG.error(e);
								}
							}
						});

						Display.getDefault().asyncExec(new Runnable() {

							@Override
							public void run() {
								try {
									BBATViewPart.hideView(ScreenShotView.ID);
									ScreenShotView shotView =  (ScreenShotView) BBATViewPart.openView(ScreenShotView.ID);
									shotView.setInput((TestRunInstanceModel)sel);
								} catch (Exception e) {
									LOG.error(e);
								}

							}
						});
					}
				} catch (Exception e) {
					LOG.error(e);
				}	
			}
		});

		createRunColumns(comp, executionViewer);
		executionViewer.setLabelProvider(new TestRunInfoLabelProvider());
		executionViewItem.setImage(BBATImageManager.getInstance().getImage(BBATImageManager.EXECUTING));
		executionViewItem.setControl(comp);
		createExecutionViewActions(executionViewer);
		testRunFolder.setSelection(executionViewItem);

	}*/

	/*private void createExecutionViewActions(final TableViewer viewer) {
		final Action removeAction = new Action("Export Logs") {
			@Override
			public void run() {

				IStructuredSelection sel =(IStructuredSelection) viewer.getSelection();
				List<?> selectedObjs = sel.toList();
				WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), new ExportLogsWizard((List<TestRunInstanceModel>) selectedObjs));
				dialog.open();
				try {
					refresh();
				} catch (Exception e) {
					LOG.error(e);
				}
			}

			@Override
			public boolean isEnabled() {


				IStructuredSelection sel =(IStructuredSelection) viewer.getSelection();
				List<?> selectedObjs = sel.toList();

				if(selectedObjs.isEmpty()){
					return false;
				}

				return true;
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

	}*/

	/*public void createRunColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { ITestConstants.TESTCASE, ITestConstants.TESTSUITE, ITestConstants.TESTPROJECT,"Status" };
		int[] bounds = { 30,25,25,20 };

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

	}*/

	private void createTestRunViewer(Composite innerRight) {

		CTabFolder testRunFolder = new CTabFolder(innerRight, SWT.TOP|SWT.BORDER);
		testRunFolder.setSimple(false);
		testRunItem = new CTabItem(testRunFolder, SWT.NONE);
		testRunItem.setText("Common Test Cases");
		testRunItem.setImage(BBATImageManager.getInstance().getImage(BBATImageManager.TESTCASE_GIF_16));
		Composite comp = new Composite(testRunFolder, SWT.NONE);
		commonTestCaseViewer = new TableViewer(comp, SWT.MULTI | SWT.H_SCROLL| SWT.V_SCROLL);
		commonTestCaseViewer.setContentProvider(new ArrayContentProvider());
		commonTestCaseViewer.getTable().setLinesVisible(true);
		commonTestCaseViewer.getTable().setHeaderVisible(true);
		createColumns(comp, commonTestCaseViewer);
		commonTestCaseViewer.setLabelProvider(new TestRunnerLableProvider());
		try {
			commonTestCaseViewer.setInput(TestRunExecutionManager.getInstance().getTestRunCases());
		} catch (Exception e) {
			LOG.error(e);
		}
		createDropSupportForTestCases();
		getViewSite().setSelectionProvider(commonTestCaseViewer);
		addMenuManager(commonTestCaseViewer);
		testRunItem.setControl(comp);
		testRunFolder.setSelection(0);
	}

	public void createTestDeviceTab(AndroidDevice testDevice, List<TestRunInstanceModel> input){
		CTabItem testRunItem = new CTabItem(testRunFolder, SWT.NONE);
		testRunItem.setText("Device Runs");
		Composite comp = new Composite(testRunFolder, SWT.NONE);
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
		createTestRunViewer(form);
		createDeviceRun(form);
		form.setWeights(new int[]{50,50});

	}

	private void createDeviceRun(SashForm form) {
		deviceRunTabFolder = new CTabFolder(form, SWT.TOP|SWT.BORDER);
		deviceRunTabFolder.setSimple(false);
		CTabItem testRunItem = new CTabItem(deviceRunTabFolder, SWT.NONE);
		testRunItem.setText("Test Devices");
		testRunItem.setImage(BBATImageManager.getInstance().getImage(BBATImageManager.ANDROID_DEVICE));
		Composite comp = new Composite(deviceRunTabFolder, SWT.NONE);
		testDeviceViewer = new TreeViewer(comp, SWT.H_SCROLL| SWT.V_SCROLL);
		testDeviceViewer.getTree().setHeaderVisible(true);
		testDeviceViewer.getTree().setLinesVisible(true);
		testDeviceViewer.setContentProvider(new DeviceTestRunContentProvider());
		testDeviceViewer.setLabelProvider(new DeviceTestRunLableProvider());
		/*testDeviceViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection  =(IStructuredSelection) event.getSelection();
				Object obj = selection.getFirstElement();
				openExecutionView(obj);
			}
		});*/

		testDeviceViewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection  =(IStructuredSelection) event.getSelection();
				Object obj = selection.getFirstElement();
				openExecutionView(obj);
			}

		});
		createDeviceRunColumns(comp);
		try{
			testDeviceViewer.setInput(TestRunExecutionManager.getInstance());
		}catch(Exception e){
			LOG.error(e);
		}

		addMenu2DeviceRun(testDeviceViewer);
		testRunItem.setControl(comp);
		deviceRunTabFolder.setSelection(testRunItem);
		createDropSupportForDevice();
	}


	private void createDeviceTabItem(final CTabFolder tabFolder, Object obj) {
		if(obj  instanceof DeviceTestRun){
			DeviceTestRun run = (DeviceTestRun) obj;
			createDeviceItem(tabFolder,run);
		}
	}


	private void openExecutionView(Object obj) {
		if(obj  instanceof DeviceTestRun){
			DeviceTestRun run = (DeviceTestRun) obj;

			if(run.getStatus().toString().equalsIgnoreCase(TestStatus.NOTEXECUTED.toString())){
				return;
			}
			//					setRunViewerInput(run);
			try {
				ExecutionView view = (ExecutionView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ExecutionView.ID);
				view.setRunViewerInput(run);
			} catch (PartInitException e) {
				LOG.error(e);
			}

			//					createDeviceItem(tabFolder,run);
		}
	}


	private void addMenu2DeviceRun(final TreeViewer viewer) {


		final Action removeAction = new Action("Remove") {
			@Override
			public void run() {
				IStructuredSelection sel =(IStructuredSelection) viewer.getSelection();
				List<?> selectedObjs = sel.toList();
				for(Object obj : selectedObjs){
					TestRunExecutionManager.getInstance().removeDevice((DeviceTestRun)obj);	
				}
				try {
					refresh();
				} catch (Exception e) {
					LOG.error(e);
				}
			}

			@Override
			public boolean isEnabled() {

				if(TestRunExecutionManager.getInstance().isExecuting()){
					return false;
				}
				IStructuredSelection sel =(IStructuredSelection) viewer.getSelection();
				List<?> selectedObjs = sel.toList();

				if(selectedObjs.isEmpty()){
					return false;
				}
				for(Object obj : selectedObjs){

					if(!(obj instanceof DeviceTestRun)){
						return false;
					}

				}
				return true;
			}


		};

		final Action editAction = new Action("Edit") {

			@Override
			public void run() {

				IStructuredSelection sel =(IStructuredSelection) viewer.getSelection();
				List<?> selectedObjs = sel.toList();
				createDeviceTabItem(deviceRunTabFolder, selectedObjs.get(0));
			}

			@Override
			public boolean isEnabled() {

				if(TestRunExecutionManager.getInstance().isExecuting()){
					return false;
				}
				IStructuredSelection sel =(IStructuredSelection) viewer.getSelection();
				List<?> selectedObjs = sel.toList();

				if(selectedObjs.isEmpty()){
					return false;
				}
				for(Object obj : selectedObjs){

					if(!(obj instanceof DeviceTestRun)){
						return false;
					}

				}
				return true;
			}

		};
		final MenuManager menuManager = new MenuManager();
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(new IMenuListener() {

			@Override
			public void menuAboutToShow(IMenuManager manager) {
				removeAction.setEnabled(removeAction.isEnabled());
				editAction.setEnabled(editAction.isEnabled());
				manager.add(removeAction);
				manager.add(editAction);
			}
		});
		viewer.getControl().setMenu(menuManager.createContextMenu(viewer.getControl()));

	}

	private void createDeviceItem(CTabFolder tabFolder,DeviceTestRun run) {
		if(deviceRunItem!=null){
			deviceRunItem.dispose();
			deviceTestRun =null;
		}
		deviceTestRun = run;
		deviceRunItem = new CTabItem(tabFolder, SWT.NONE);
		deviceRunItem.setText(run.getDevice().getName());
		Composite comp = new Composite(tabFolder, SWT.NONE);
		deviceTestCaseViewer = new TableViewer(comp, SWT.MULTI | SWT.H_SCROLL| SWT.V_SCROLL);
		deviceTestCaseViewer.setContentProvider(new ArrayContentProvider());
		deviceTestCaseViewer.getTable().setLinesVisible(true);
		deviceTestCaseViewer.getTable().setHeaderVisible(true);
		createColumns(comp, deviceTestCaseViewer);
		deviceTestCaseViewer.setLabelProvider(new TestRunnerLableProvider());
		try {
			deviceTestCaseViewer.setInput(run.getCases());
		} catch (Exception e) {
			LOG.error(e);
		}
		addMenu(deviceTestCaseViewer);
		deviceRunItem.setControl(comp);
		createDropForDeviceRunItem(run);
		tabFolder.setSelection(deviceRunItem);

	}

	private void createDropForDeviceRunItem(DeviceTestRun run) {
		int operations = DND.DROP_COPY | DND.DROP_MOVE;
		Transfer[] transferTypes = new Transfer[] { LocalSelectionTransfer.getTransfer() };
		deviceTestCaseViewer.addDropSupport(operations, transferTypes,new TestDeviceRunCaseDropListener(deviceTestCaseViewer,run));
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
					LOG.error(e);
				}
			}

			@Override
			public boolean isEnabled() {

				if(TestRunExecutionManager.getInstance().isExecuting()){
					return false;
				}

				IStructuredSelection sel =(IStructuredSelection) viewer.getSelection();
				List<?> selectedObjs = sel.toList();
				if(selectedObjs.isEmpty()){
					return false;
				}

				return true;
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
		String[] titles = {  ITestConstants.TESTCASE, ITestConstants.TESTSUITE, ITestConstants.TESTPROJECT };
		int[] bounds = { 40,30,30 };

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

	/*	public void setRunViewerInput(DeviceTestRun execRun){
		executionViewItem.setText(execRun.getDevice().getName());
		executionViewer.setInput(execRun.getRunInstances());
		executionViewer.refresh();
	}

	public void clearRunViewerInput(){
		executionViewer.setInput(Collections.EMPTY_LIST);
		executionViewer.refresh();

	}*/

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
			LOG.error(e);
		}
	}

	public static void refreshView()
	{
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				BBATViewPart testRunView = (BBATViewPart) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(ID);
				try {
					testRunView.refresh();
				} catch (Exception e) {
					LOG.error(e);
				}


			}
		});
	}

}
