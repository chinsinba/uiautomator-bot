package in.BBAT.TestRunner.Listener;


import com.android.ddmlib.testrunner.ITestRunListener;

/**
 * 
 * @author syed mehtab
 *
 */
public interface ITestRunner {

	/**
	 * 
	 * @param testCaseClassName fully qualified java class name
	 * @param listener
	 * @param logListener
	 * @param autoListener
	 * @param shotListener
	 * @param memListner
	 * @param cpuListener
	 */
	void execute(String testCaseClassName,ITestRunListener listener, ILogListener logListener,IUiAutomatorListener autoListener,IScreenShotListener shotListener,IMemoryUsageListener memListner,ICpuUsageListener cpuListener);

	void abort();

}
