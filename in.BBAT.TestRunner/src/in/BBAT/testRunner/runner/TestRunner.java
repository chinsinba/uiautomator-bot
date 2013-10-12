package in.BBAT.testRunner.runner;

import in.BBAT.TestRunner.device.IAndroidDevice;
import in.BBAT.TestRunner.device.ILogListener;

import com.android.ddmlib.testrunner.ITestRunListener;
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
	public void execute(String testCaseClassName, ITestRunListener testCaseExecutionListener, ILogListener deviceLogListener,IUiAutomatorListener autoListener) {
		preRun(deviceLogListener);

		run(testCaseClassName,testCaseExecutionListener,autoListener);

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
	private void run(String testCaseClassName, ITestRunListener testCaseExecutionListener,IUiAutomatorListener autoListener){
		testDevice.executeTestCase(testCaseClassName,autoListener, testCaseExecutionListener);
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
	
}
