package in.BBAT.testRunner.runner;

import in.bbat.testrunner.IAndroidDevice;
import in.bbat.testrunner.ILogListener;

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
	public void execute(String testCaseClassName, ITestRunListener testCaseExecutionListener, ILogListener deviceLogListener) {
		initialiseDevcieLoggers(deviceLogListener);
		runTestCase(testCaseClassName);
		postExecution();
	}

	private void postExecution() {
	}

	public void execute(String className, String testMethodName) {

	}

	/**
	 * Initialises the loggers
	 * @param deviceLogListener 
	 */
	private void initialiseDevcieLoggers(ILogListener deviceLogListener) {
		testDevice.addLogListener(deviceLogListener);
		testDevice.startLogging();
	}

	@Override
	public void abort() {

	}

	/**
	 * Runs the ant to build the jar.
	 */
	private void createTestJar()
	{

	}

	/**
	 * pushes the testcase jar to the device.
	 */
	private void pushJarToDevice(){

	}

	/**
	 * Starts the execution of testcases on the devices
	 * @param testCaseClassName 
	 */
	private void runTestCase(String testCaseClassName){

	}

	public TestArtifacts getTestArtifacts() {
		return testArtifacts;
	}

	public void setTestArtifacts(TestArtifacts testArtifacts) {
		this.testArtifacts = testArtifacts;
	}
}
