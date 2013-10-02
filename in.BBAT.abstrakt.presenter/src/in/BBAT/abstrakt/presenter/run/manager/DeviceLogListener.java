package in.BBAT.abstrakt.presenter.run.manager;

import java.util.List;

import com.android.ddmlib.logcat.LogCatMessage;

import in.BBAT.TestRunner.device.ILogListener;
import in.BBAT.abstrakt.presenter.run.model.TestRunCase;

public class DeviceLogListener implements ILogListener {

	private TestRunCase testRunCase;

	public DeviceLogListener(TestRunCase runCaseObj) {
		this.testRunCase = runCaseObj;
	}

	@Override
	public void processLogLine(List<LogCatMessage> logMessages) {
		// TODO Auto-generated method stub
		
	}

}
