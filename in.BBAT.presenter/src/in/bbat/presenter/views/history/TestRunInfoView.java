package in.bbat.presenter.views.history;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.abstrakt.presenter.run.model.TestDeviceRunModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.BBAT.abstrakt.presenter.run.model.TestStatus;
import in.BBAT.presenter.labelProviders.HistoryTestRunInfoLabelProvider;
import in.bbat.abstrakt.gui.BBATImageManager;
import in.bbat.logger.BBATLogger;
import in.bbat.presenter.views.BBATViewPart;
import in.bbat.presenter.views.tester.AutomatorLogView;
import in.bbat.presenter.views.tester.TestLogView;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.android.ddmlib.logcat.LogCatMessage;

public class TestRunInfoView extends BBATViewPart {

	public static final String ID = "in.BBAT.presenter.history.TestRunInstanceView";
	private static final Logger LOG = BBATLogger.getLogger(TestRunInfoView.class.getName());

	private CTabFolder testRunFolder;
	private CTabItem testRunItem;
	private TableViewer viewer;
	@Override
	public void refresh() throws Exception {

	}

	@Override
	public ISelection getSelectedElements() {
		return viewer.getSelection();
	}

	@Override
	public void createPartControl(Composite parent) {
		createRunInstannceViewer(parent);
		addMenuManager(viewer);
	}

	private void createRunInstannceViewer(Composite innerRight) {
		testRunFolder = new CTabFolder(innerRight, SWT.TOP|SWT.BORDER);
		testRunItem = new CTabItem(testRunFolder, SWT.BORDER);
		testRunItem.setText("Device ");
		Composite comp = new Composite(testRunFolder, SWT.None);
		viewer = new TableViewer(comp, SWT.MULTI | SWT.H_SCROLL| SWT.V_SCROLL);
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.getTable().setLinesVisible(true);
		viewer.getTable().setHeaderVisible(true);
		viewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {

				final Object sel = ((IStructuredSelection)event.getSelection()).getFirstElement();
				if(((TestRunInstanceModel)sel).getStatus().equalsIgnoreCase(TestStatus.NOTEXECUTED.toString())){
					return ;
				}

				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						try {
							BBATViewPart.hideView(HistoryDeviceLogView.ID);
							TestLogView view  = (TestLogView) BBATViewPart.openView(HistoryDeviceLogView.ID);
							view.bufferChanged(((TestRunInstanceModel)sel).getDeviceLogsFromDB(), new ArrayList<LogCatMessage>());
						} catch (Exception e) {
							LOG.error(e);
						}	
					}
				});

				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {
						try{
							BBATViewPart.hideView(HistoryAutoLogView.ID);
							IViewPart autoLogView =  BBATViewPart.openView(HistoryAutoLogView.ID);
							if(autoLogView!= null){
								((AutomatorLogView)autoLogView).setInput((TestRunInstanceModel)sel);
							}
						} catch (Exception e) {
							LOG.error(e);
						}	
					}
				});

				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						try{
							BBATViewPart.hideView(ScreenShotHistoryView.ID);
							IViewPart scrShtView = BBATViewPart.openView(ScreenShotHistoryView.ID);
							if(scrShtView!= null){
								((ScreenShotHistoryView)scrShtView).setInput((TestRunInstanceModel)sel);
							}
						} catch (Exception e) {
							LOG.error(e);
						}	
					}
				});

				try {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(HistoryDeviceLogView.ID);
				} catch (PartInitException e) {
					LOG.error(e);
				}
			}
		});

		createColumns(comp, viewer);
		viewer.setLabelProvider(new HistoryTestRunInfoLabelProvider());
		testRunItem.setControl(comp);
		testRunFolder.setSelection(0);
	}

	public void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "TestCase","TestSuite","Project","Status","Time(s)" };
		int[] bounds = { 25,25,25,12,13 };

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

		TableViewerColumn col4 = createTableViewerColumn(viewer, BBATImageManager.getInstance().getImage(BBATImageManager.TIME), titles[4],bounds[4]);
		layout.setColumnData(col4.getColumn(), new ColumnWeightData(bounds[4]));


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

	public void setInput(final TestDeviceRunModel sel) throws Exception{
		final List<AbstractTreeModel> children = sel.getChildren();
		viewer.setInput(children);
		viewer.refresh();

		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				int count =0;
				for(AbstractTreeModel mod : children){
					if(!((TestRunInstanceModel)mod).getStatus().equalsIgnoreCase(TestStatus.NOTEXECUTED.toString())){
						count++;
					}
				}
				testRunItem.setText(sel.getDeviceName() +"("+count+"/"+children.size()+")");				
			}
		});



	}

	@Override
	public void setFocus() {

	}



}
