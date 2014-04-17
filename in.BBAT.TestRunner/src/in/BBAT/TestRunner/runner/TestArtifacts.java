package in.BBAT.TestRunner.runner;

import in.BBAT.TestRunner.Listener.IAndroidDevice;
import in.BBAT.TestRunner.Listener.ILogListener;

import java.util.List;

import com.android.ddmlib.testrunner.ITestRunListener;
/**
 * 
 * @author syed mehtab
 * This class is a container holding all the selected test cases to be executed.
 *
 */
public class TestArtifacts {

	private List<String> testCasePath;

	private IAndroidDevice device;

	private ITestRunListener listener; 
	private ILogListener logListener;

	/**
	 * 
	 * @param testcasePath
	 * @param testDevice
	 */
	public TestArtifacts(List<String> testcasePath, IAndroidDevice testDevice, ITestRunListener listener,ILogListener deviceLogListener){
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

	public IAndroidDevice getDevice() {
		return device;
	}

	public void setDevice(IAndroidDevice device) {
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
