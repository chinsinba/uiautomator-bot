package in.BBAT.testRunner.runner;

import java.util.Map;

import in.BBAT.TestRunner.device.IAndroidDevice;
import in.BBAT.TestRunner.device.ILogListener;

import com.android.chimpchat.adb.AdbBackend;
import com.android.ddmlib.testrunner.ITestRunListener;
import com.android.ddmlib.testrunner.TestIdentifier;
import com.android.monkeyrunner.MonkeyDevice;
import com.android.monkeyrunner.recorder.MonkeyRecorder;
/**
 * 
 * @author Syed Mehtab
 *
 */
public class TestRunner implements ITestRunner{

	private TestArtifacts testArtifacts;

	private UiAutoTestCaseJar jar;

	private IAndroidDevice testDevice;

	public TestRunner(TestArtifacts artifacts){
		this.testArtifacts =artifacts;
	}

	public TestRunner(UiAutoTestCaseJar jar, IAndroidDevice device){
		this.jar = jar;
		this.testDevice = device;
		pushJarToDevice();
		
	}

	@Override
	public void execute(String testCaseClassName, ITestRunListener testCaseExecutionListener, ILogListener deviceLogListener) {
		preRun(deviceLogListener);

		run(testCaseClassName,testCaseExecutionListener);

		postRun();
	}

	private void postRun() {
		testDevice.stopLogging();
	}

	public void execute(String className, String testMethodName) {

	}

	/**
	 * Initialises the loggers
	 * @param deviceLogListener 
	 */
	private void preRun(ILogListener deviceLogListener) {
		testDevice.setLogListener(deviceLogListener);
		testDevice.startLogging();
	}

	@Override
	public void abort() {

	}

	/**
	 * pushes the testcase jar to the device.
	 */
	private void pushJarToDevice(){
		testDevice.pushTestJar(jar);
	}

	/**
	 * Starts the execution of testcases on the devices
	 * @param testCaseClassName 
	 * @param testCaseExecutionListener 
	 */
	private void run(String testCaseClassName, ITestRunListener testCaseExecutionListener){
		testDevice.executeTestCase(testCaseClassName, testCaseExecutionListener);
		waitForCompletion();
	}

	private void waitForCompletion() {

	}

	public TestArtifacts getTestArtifacts() {
		return testArtifacts;
	}

	public void setTestArtifacts(TestArtifacts testArtifacts) {
		this.testArtifacts = testArtifacts;
	}
	
	private class TestRunListener implements ITestRunListener
	{

		@Override
		public void testEnded(TestIdentifier arg0, Map<String, String> arg1) {
			
		}

		@Override
		public void testFailed(TestFailure arg0, TestIdentifier arg1,
				String arg2) {
		}

		@Override
		public void testRunEnded(long arg0, Map<String, String> arg1) {
			
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
	
}
