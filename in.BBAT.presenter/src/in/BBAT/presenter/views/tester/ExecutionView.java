package in.BBAT.presenter.views.tester;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PlatformUI;

import com.android.ddmlib.logcat.LogCatMessage;

import in.BBAT.abstrakt.gui.BBATImageManager;
import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.BBAT.abstrakt.presenter.run.model.TestStatus;
import in.BBAT.presenter.internal.DeviceTestRun;
import in.BBAT.presenter.labelProviders.TestRunInfoLabelProvider;
import in.BBAT.presenter.views.BBATViewPart;
import in.BBAT.presenter.wizards.ExportLogsWizard;
import in.BBAT.utils.ITestConstants;
import in.bbat.logger.BBATLogger;

public class ExecutionView extends BBATViewPart {
	public static final String ID = "in.BBAT.presenter.tester.TestExecutionView";
	private CTabItem executionViewItem;
	private TableViewer executionViewer;
	private static final Logger LOG = BBATLogger.getLogger(ExecutionView.class.getName());
	
	@Override
	public void refresh() throws Exception {
		if(executionViewer!=null)
			executionViewer.refresh();
	}

	@Override
	public ISelection getSelectedElements() {
		return null;
	}

	@Override
	public void createPartControl(Composite parent) {
		createDeviceRunViewerTabs(parent);
	}

	@Override
	public void setFocus() {

	}

	private void createDeviceRunViewerTabs(Composite innerleft) {

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
//				run(event);	
			}

			private void run(DoubleClickEvent event) {
				try {
					final Object sel = ((IStructuredSelection)event.getSelection()).getFirstElement();
					((TestRunInstanceModel)sel).setShowLogs(true);

					if(((TestRunInstanceModel)sel).getStatus().equalsIgnoreCase(TestStatus.NOTEXECUTED.getStatus()))
					{
						return;
					}

					if(!((TestRunInstanceModel)sel).getStatus().equalsIgnoreCase(TestStatus.EXECUTING.getStatus())){
						ScreenShotView.selectedTestRuncase((TestRunInstanceModel)sel);
						Display.getDefault().asyncExec(new Runnable() {
						
							@Override
							public void run() {
								try {
									BBATViewPart.hideView(TestLogView.ID);
									TestLogView view =  (TestLogView) BBATViewPart.openView(TestLogView.ID);
									view.setInput((TestRunInstanceModel)sel);
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

	}
	public void createRunColumns(final Composite parent, final TableViewer viewer) {
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


	private void createExecutionViewActions(final TableViewer viewer) {
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

	}
	
	public void setRunViewerInput(DeviceTestRun execRun){
		executionViewItem.setText(execRun.getDevice().getName());
		executionViewer.setInput(execRun.getRunInstances());
		executionViewer.refresh();
	}


}
