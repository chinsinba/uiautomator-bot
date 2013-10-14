package in.bbat.presenter.views.history;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.BBAT.presenter.labelProviders.HistoryTestRunInfoLabelProvider;
import in.bbat.abstrakt.gui.BBATImageManager;
import in.bbat.presenter.views.BBATViewPart;
import in.bbat.presenter.views.tester.AutomatorLogView;
import in.bbat.presenter.views.tester.TestLogView;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.android.ddmlib.logcat.LogCatMessage;

public class TestRunInfoView extends BBATViewPart {

	public static final String ID = "in.BBAT.presenter.history.TestRunInstanceView";

	private CTabFolder testRunFolder;
	private CTabItem testRunItem;
	private TableViewer viewer;
	@Override
	public void refresh() throws Exception {

	}

	@Override
	public ISelection getSelectedElements() {
		return null;
	}

	@Override
	public void createPartControl(Composite parent) {
		createRunInstannceViewer(parent);
	}

	private void createRunInstannceViewer(Composite innerRight) {
		testRunFolder = new CTabFolder(innerRight, SWT.TOP|SWT.BORDER);
		testRunItem = new CTabItem(testRunFolder, SWT.BORDER);
		testRunItem.setText("Device Name");
		Composite comp = new Composite(testRunFolder, SWT.None);
		viewer = new TableViewer(comp, SWT.MULTI | SWT.H_SCROLL| SWT.V_SCROLL);
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.getTable().setLinesVisible(true);
		viewer.getTable().setHeaderVisible(true);
		viewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				try {
					Object sel = ((IStructuredSelection)event.getSelection()).getFirstElement();
					TestLogView view  = (TestLogView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestLogView.ID);

					if(view != null){
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(view);
					}
					view  = (TestLogView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(TestLogView.ID);
					view.getPanel().clearBuffer();
					view.getPanel().bufferChanged(((TestRunInstanceModel)sel).getDeviceLogsFromDB(), new ArrayList<LogCatMessage>());

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
		viewer.setLabelProvider(new HistoryTestRunInfoLabelProvider());
		testRunItem.setControl(comp);
		testRunFolder.setSelection(0);
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

	public void setInput(List<AbstractTreeModel> runInstances){
		viewer.setInput(runInstances);
		viewer.refresh();

	}

	@Override
	public void setFocus() {

	}

}
