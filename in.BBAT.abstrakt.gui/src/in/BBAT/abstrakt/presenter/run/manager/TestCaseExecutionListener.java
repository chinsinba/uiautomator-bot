package in.BBAT.abstrakt.presenter.run.manager;

import in.BBAT.abstrakt.presenter.run.model.TestRunCase;

import java.util.Map;

import com.android.ddmlib.testrunner.ITestRunListener;
import com.android.ddmlib.testrunner.TestIdentifier;

public class TestCaseExecutionListener implements ITestRunListener {

	private TestRunCase runCase;
	
	public TestCaseExecutionListener(TestRunCase testRunCase){
		this.runCase = testRunCase;
	}
	@Override
	public void testEnded(TestIdentifier arg0, Map<String, String> arg1) {

	}

	@Override
	public void testFailed(TestFailure arg0, TestIdentifier arg1, String arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void testRunEnded(long arg0, Map<String, String> arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void testRunFailed(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void testRunStarted(String arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void testRunStopped(long arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void testStarted(TestIdentifier arg0) {
		// TODO Auto-generated method stub

	}

}
