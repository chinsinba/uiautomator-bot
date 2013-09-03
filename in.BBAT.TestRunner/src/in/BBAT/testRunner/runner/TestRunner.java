package in.BBAT.testRunner.runner;

import in.bbat.testrunner.IAndroidDevice;
import in.bbat.testrunner.TestDevice;


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

	}

	@Override
	public void execute() {
		createTestJar();
		pushJarToDevice();		
		initialiseDevcieLoggers();
		runTestcases();
	}

	/**
	 * Initialises the loggers
	 */
	private void initialiseDevcieLoggers() {

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
	 */
	private void runTestcases(){

	}

	public TestArtifacts getTestArtifacts() {
		return testArtifacts;
	}

	public void setTestArtifacts(TestArtifacts testArtifacts) {
		this.testArtifacts = testArtifacts;
	}


}
