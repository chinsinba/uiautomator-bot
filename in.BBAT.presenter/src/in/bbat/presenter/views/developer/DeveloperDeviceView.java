package in.bbat.presenter.views.developer;

import java.util.List;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.abstrakt.presenter.device.model.IDeviceModelChangeListener;
import in.BBAT.abstrakt.presenter.device.model.TestDeviceManager;
import in.BBAT.presenter.DND.listeners.DeviceDragListener;
import in.BBAT.presenter.labelProviders.DeviceViewLabelProvider;
import in.bbat.logger.BBATLogger;
import in.bbat.presenter.internal.TestRunContainer;
import in.bbat.presenter.internal.TestRunExecutionManager;
import in.bbat.presenter.views.BBATViewPart;
import in.bbat.presenter.views.tester.TestRunnerView;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.PlatformUI;

public class DeveloperDeviceView extends BBATViewPart {

	private static final Logger LOG = BBATLogger.getLogger(DeveloperDeviceView.class.getName());
	public static final String ID = "in.BBAT.presenter.developer.deviceView";
	private TableViewer viewer;


	@Override
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.H_SCROLL| SWT.V_SCROLL);
		viewer.getTable().setLinesVisible(true);
		//		viewer.getTable().setHeaderVisible(true);
		createRunColumns(parent, viewer);
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new DeviceViewLabelProvider());
		// Provide the input to the ContentProvider
		List<AndroidDevice> devices = null;
		try{
			TestDeviceManager.getInstance().addDeviceModelChangeListener(new DeviceModelListener());
			devices = TestDeviceManager.getInstance().getDevices();
			viewer.setInput(devices);
		}catch(Exception e){
			LOG.error(e);
		}

		addMenuManager(viewer);
		getViewSite().setSelectionProvider(viewer);
		createDragSupport();
	}

	private void createRunColumns(Composite parent, TableViewer viewer2) {

		String[] titles = { "","" };
		int[] bounds = { 72,28};

		TableColumnLayout layout = new TableColumnLayout();
		parent.setLayout(layout);

		TableViewerColumn col =createTableViewerColumn(viewer,null, titles[0],bounds[0]);
		layout.setColumnData(col.getColumn(), new ColumnWeightData(bounds[0]));

		TableViewerColumn col1 = createTableViewerColumn(viewer, null, titles[1],bounds[1]);
		layout.setColumnData(col1.getColumn(), new ColumnWeightData(bounds[1]));
	}

	private TableViewerColumn createTableViewerColumn(TableViewer viewer,
			Image imag, String titles, int bounds) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(titles);
		column.setToolTipText(titles);
		column.setWidth(bounds);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	@Override
	public void setFocus() {

	}

	@Override
	protected void createDragSupport() {

		int operations = DND.DROP_COPY| DND.DROP_MOVE;
		Transfer[] transferTypes = new Transfer[]{LocalSelectionTransfer.getTransfer()};
		viewer.addDragSupport(operations, transferTypes, new DeviceDragListener(viewer));
	}

	@Override
	public void refresh() {
		viewer.setInput(TestDeviceManager.getInstance().getDevices());
		viewer.refresh();
	}

	private class DeviceModelListener implements IDeviceModelChangeListener
	{
		@Override
		public void deviceAdded(AndroidDevice device) {
			refreshInUIThread();

		}


		private void refreshInUIThread() {
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					DeveloperDeviceView.this.refresh();	
				}
			});
		}


		@Override
		public void deviceRemoved(AndroidDevice device) {
			TestRunExecutionManager.getInstance().deviceRemoved(device);
			if(	TestRunExecutionManager.getInstance().getSelectedDevices().isEmpty())
			{
				TestRunExecutionManager.getInstance().setExecuting(false);
			}
			TestRunnerView.refreshView();
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Device Removed", "Device disconnected.");					
				}
			});
			refreshInUIThread();
		}


		@Override
		public void refresh(AndroidDevice device) {
			refreshInUIThread();			
		}
	}

	@Override
	public ISelection getSelectedElements() {
		return viewer.getSelection();
	}



}
