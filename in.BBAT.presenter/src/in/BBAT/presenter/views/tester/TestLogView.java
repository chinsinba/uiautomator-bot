package in.BBAT.presenter.views.tester;

import java.util.ArrayList;
import java.util.List;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.BBAT.presenter.Activator;
import in.BBAT.presenter.views.BBATViewPart;
import in.bbat.logger.BBATLogger;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;

import com.android.ddmlib.logcat.LogCatMessage;


public class TestLogView extends BBATViewPart {

	public final static String ID = "in.BBAT.presenter.tester.AndroidLogView";
	private static final Logger LOG = BBATLogger.getLogger(TestLogView.class.getName());
	private LogCatPanel panel;

	@Override
	public void createPartControl(Composite parent) {
		setPanel(new LogCatPanel(Activator.getDefault().getPreferenceStore()));
		getPanel().createControl(parent);
		//		startLogging(TestDeviceManager.getInstance().getDevices().get(0));

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
		getPanel().deviceSelected(device.getiDevice().getMonkeyDevice());
		setPartName(device.getiDevice().getName());
	}

	public void setName(String name){
		setPartName(name);
	}
	@Override
	public void dispose() {
		super.dispose();
		//		getPanel().stopLogging();
	}

	public LogCatPanel getPanel() {
		return panel;
	}

	public void setPanel(LogCatPanel panel) {
		this.panel = panel;
	}

	private void bufferChanged(List<LogCatMessage> addedMessages, ArrayList<LogCatMessage> deletedMessages){
		getPanel().clearBuffer();
		getPanel().bufferChanged(addedMessages, deletedMessages);

	}

	public void setInput(TestRunInstanceModel testRunInstanceModel) {
		setName("Device Logs: "+testRunInstanceModel.getTestCaseEntity().getName());
		getPanel().setTestRunInstance(testRunInstanceModel);
		bufferChanged((testRunInstanceModel).getDeviceLogsFromDB(), new ArrayList<LogCatMessage>());		
	}


}
