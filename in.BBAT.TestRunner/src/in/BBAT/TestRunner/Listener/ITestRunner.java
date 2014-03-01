package in.BBAT.TestRunner.Listener;


import com.android.ddmlib.testrunner.ITestRunListener;

public interface ITestRunner {

	
	void execute(String testCaseClassName,ITestRunListener listener, ILogListener logListener,IUiAutomatorListener autoListener,IScreenShotListener shotListener,IMemoryUsageListener memListner,ICpuUsageListener cpuListener);

	void abort();

}
