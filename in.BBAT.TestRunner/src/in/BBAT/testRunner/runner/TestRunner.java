package in.BBAT.testRunner.runner;


/**
 * 
 * @author Syed Mehtab
 *
 */
public class TestRunner implements ITestRunner{

	private TestArtifacts testArtifacts;

	public TestRunner(TestArtifacts artifacts){
		this.testArtifacts =artifacts;
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
