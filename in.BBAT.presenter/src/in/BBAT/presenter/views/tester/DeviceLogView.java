package in.BBAT.presenter.views.tester;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;

import com.android.ddmlib.IDevice;
import com.android.ddmuilib.logcat.LogCatPanel;

import in.BBAT.presenter.Activator;
import in.BBAT.presenter.views.BBATViewPart;
import in.bbat.logger.BBATLogger;

public class DeviceLogView extends BBATViewPart {

	public static final String ID = "in.BBAT.presenter.tester.deviceLogView";
	private static final Logger LOG = BBATLogger.getLogger(DeviceLogView.class.getName());
	private LogCatPanel panel;
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
	public void createPartControl(Composite parent) {
		panel = new LogCatPanel(Activator.getDefault().getPreferenceStore());
		panel.createPanel(parent);

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	public void setDevice(IDevice device){
		panel.deviceSelected(device);
	}

	
	
}
