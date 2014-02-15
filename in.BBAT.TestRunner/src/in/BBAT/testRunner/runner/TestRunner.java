package in.BBAT.testRunner.runner;

import in.BBAT.TestRunner.device.IAndroidDevice;
import in.BBAT.TestRunner.device.ICpuUsageListener;
import in.BBAT.TestRunner.device.ILogListener;
import in.BBAT.TestRunner.device.IMemoryUsageListener;
import in.BBAT.TestRunner.device.IScreenShotListener;

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
	public void execute(String testCaseClassName, ITestRunListener testCaseExecutionListener, ILogListener deviceLogListener,IUiAutomatorListener autoListener, IScreenShotListener listener,IMemoryUsageListener memListner,ICpuUsageListener cpuListener) {
		preRun(deviceLogListener,listener);

		run(testCaseClassName,testCaseExecutionListener,autoListener, memListner, cpuListener);

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
	 * @param listener 
	 */
	private void preRun(ILogListener deviceLogListener, IScreenShotListener screenShotListener) {
		testDevice.setLogListener(deviceLogListener);
		testDevice.startLogging();
		testDevice.setScreenShotListener(screenShotListener);
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
	 * @param cpuListener 
	 * @param memListner 
	 */
	private void run(String testCaseClassName, ITestRunListener testCaseExecutionListener,IUiAutomatorListener autoListener, IMemoryUsageListener memListner, ICpuUsageListener cpuListener){
		testDevice.executeTestCase(testCaseClassName,autoListener, memListner,cpuListener,testCaseExecutionListener);
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
