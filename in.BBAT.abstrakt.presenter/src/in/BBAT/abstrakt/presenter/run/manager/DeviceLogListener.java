package in.BBAT.abstrakt.presenter.run.manager;

import in.BBAT.TestRunner.device.ILogListener;
import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;

import java.util.List;

import com.android.ddmlib.logcat.LogCatMessage;

public class DeviceLogListener implements ILogListener {

	private TestRunInstanceModel testRunCase;

	public DeviceLogListener(TestRunInstanceModel runCaseObj) {
		this.testRunCase = runCaseObj;
	}

	@Override
	public void processLogLine(List<LogCatMessage> logMessages) {
		
	}

}
