package in.bbat.presenter.views.developer;

import java.util.List;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.abstrakt.presenter.device.model.IDeviceModelChangeListener;
import in.BBAT.abstrakt.presenter.device.model.TestDeviceManager;
import in.BBAT.presenter.DND.listeners.DeviceDragListener;
import in.BBAT.presenter.labelProviders.DeviceViewLabelProvider;
import in.bbat.logger.BBATLogger;
import in.bbat.presenter.views.BBATViewPart;

import org.apache.log4j.Logger;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class DeveloperDeviceView extends BBATViewPart {

	private static final Logger LOG = BBATLogger.getLogger(DeveloperDeviceView.class.getName());
	public static final String ID = "in.BBAT.presenter.developer.deviceView";
	private TableViewer viewer;


	@Override
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.H_SCROLL| SWT.V_SCROLL);
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

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

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
					refresh();	
				}
			});
		}


		@Override
		public void deviceRemoved(AndroidDevice device) {
			refreshInUIThread();
		}
	}

	@Override
	public ISelection getSelectedElements() {
		return viewer.getSelection();
	}
	
	
	
}
