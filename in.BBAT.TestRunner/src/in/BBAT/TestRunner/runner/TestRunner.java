package in.BBAT.TestRunner.runner;

import in.BBAT.TestRunner.Listener.IAndroidDevice;
import in.BBAT.TestRunner.Listener.ICpuUsageListener;
import in.BBAT.TestRunner.Listener.ILogListener;
import in.BBAT.TestRunner.Listener.IMemoryUsageListener;
import in.BBAT.TestRunner.Listener.IScreenShotListener;
import in.BBAT.TestRunner.Listener.ITestRunner;
import in.BBAT.TestRunner.Listener.IUiAutomatorListener;

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
	
	public TestRunner(String jarPath,IAndroidDevice device ) {
	}

	@Override
	public void execute(String testCaseClassName, ITestRunListener testCaseExecutionListener, ILogListener deviceLogListener,IUiAutomatorListener autoListener, IScreenShotListener listener,IMemoryUsageListener memListner,ICpuUsageListener cpuListener) {
		preRun(deviceLogListener,listener, memListner, cpuListener);

		run(testCaseClassName,testCaseExecutionListener,autoListener);

		postRun();
	}

	private void postRun() {
		testDevice.stopLogging();
		testDevice.stopCpuUsageThread();
		testDevice.stopMemoryUsageThread();
	}

	public void execute(String className, String testMethodName) {

	}

	/**
	 * Initialises the loggers
	 * @param deviceLogListener 
	 * @param listener 
	 */
	private void preRun(ILogListener deviceLogListener, IScreenShotListener screenShotListener,IMemoryUsageListener memListner,ICpuUsageListener cpuListener) {
		testDevice.setLogListener(deviceLogListener);
		testDevice.startLogging();
		testDevice.setScreenShotListener(screenShotListener);
		testDevice.setMemoryListener(memListner);
		testDevice.setCpuListener(cpuListener);
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
	private void run(String testCaseClassName, ITestRunListener testCaseExecutionListener,IUiAutomatorListener autoListener){
		testDevice.executeTestCase(testCaseClassName,autoListener,testCaseExecutionListener);
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
