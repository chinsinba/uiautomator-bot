package in.BBAT.testRunner.runner;

import java.util.Map;

import com.android.ddmlib.IDevice;
import com.android.ddmlib.testrunner.ITestRunListener;
import com.android.ddmlib.testrunner.TestIdentifier;

public class TestRunListener implements ITestRunListener {

	private IDevice testDevice;

	public TestRunListener(IDevice device){
		this.testDevice = device;
	}

	@Override
	public void testEnded(TestIdentifier arg0, Map<String, String> arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public void testFailed(TestFailure arg0, TestIdentifier arg1, String arg2) {

	}

	@Override
	public void testRunEnded(long arg0, Map<String, String> arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void testRunFailed(String arg0) {

	}

	@Override
	public void testRunStarted(String arg0, int arg1) {

	}

	@Override
	public void testRunStopped(long arg0) {

	}

	@Override
	public void testStarted(TestIdentifier arg0) {

	}

}
