package in.BBAT.testRunner.runner;

import java.util.List;

import com.android.ddmlib.IDevice;
import com.android.ddmlib.testrunner.ITestRunListener;
/**
 * 
 * @author syed mehtab
 * This class is a container holding all the selected test cases to be executed.
 * 
 *
 */
public class TestArtifacts {

	private List<String> testCasePath;

	private IDevice device;

	private ITestRunListener listener; 
	private ILogListener logListener;

	/**
	 * 
	 * @param testcasePath
	 * @param testDevice
	 */
	public TestArtifacts(List<String> testcasePath, IDevice testDevice, ITestRunListener listener,ILogListener deviceLogListener){
		this.testCasePath = testcasePath;
		this.device = testDevice;
		this.listener=listener;
		this.setLogListener(deviceLogListener);
	}

	public List<String> getTestCasePath() {
		return testCasePath;
	}

	public void setTestCasePath(List<String> testCasePath) {
		this.testCasePath = testCasePath;
	}

	public IDevice getDevice() {
		return device;
	}

	public void setDevice(IDevice device) {
		this.device = device;
	}

	public ITestRunListener getListener() {
		return listener;
	}

	public void setListener(ITestRunListener listener) {
		this.listener = listener;
	}

	public ILogListener getLogListener() {
		return logListener;
	}

	public void setLogListener(ILogListener logListener) {
		this.logListener = logListener;
	}

}