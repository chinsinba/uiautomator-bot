package in.bbat.presenter.views.developer;

import in.BBAT.abstrakt.presenter.device.model.TestDeviceManager;
import in.BBAT.abstrakt.presenter.pkg.model.TestProjectManager;
import in.BBAT.presenter.contentProviders.TestCaseBrowserContentProvider;
import in.BBAT.presenter.labelProviders.DeviceViewLabelProvider;
import in.BBAT.presenter.labelProviders.TestCaseLabelProvider;
import in.bbat.presenter.views.BBATViewPart;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class DeveloperDeviceView extends BBATViewPart {

	public static final String ID = "in.BBAT.presenter.developer.deviceView";
	private TableViewer viewer;
	

	@Override
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL);
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setLabelProvider(new DeviceViewLabelProvider());
		// Provide the input to the ContentProvider
		viewer.setInput(TestDeviceManager.getInstance().getDevices());
		addMenuManager(viewer);
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh() {
		
	}

}
