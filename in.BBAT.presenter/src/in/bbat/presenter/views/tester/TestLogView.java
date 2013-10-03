package in.bbat.presenter.views.tester;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.abstrakt.presenter.device.model.TestDeviceManager;
import in.bbat.presenter.Activator;
import in.bbat.presenter.views.BBATViewPart;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;


public class TestLogView extends BBATViewPart {

	public final static String ID = "in.BBAT.presenter.tester.AndroidLogView";
	private LogCatPanel panel;

	@Override
	public void createPartControl(Composite parent) {
		panel = new LogCatPanel(Activator.getDefault().getPreferenceStore());
		panel.createControl(parent);
		startLogging(TestDeviceManager.getInstance().getDevices().get(0));

	}

	@Override
	public void refresh() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public ISelection getSelectedElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	public void startLogging(AndroidDevice device){
		panel.deviceSelected(device.getiDevice().getMonkeyDevice());
		setPartName(device.getiDevice().getName());
	}

	@Override
	public void dispose() {
		panel.stopLogging();
	}

}