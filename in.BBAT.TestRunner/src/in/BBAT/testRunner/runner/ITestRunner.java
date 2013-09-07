package in.BBAT.testRunner.runner;

import in.bbat.testrunner.ILogListener;

import com.android.ddmlib.testrunner.ITestRunListener;

public interface ITestRunner {

	
	void execute(String testCaseClassName,ITestRunListener listener, ILogListener logListener);

	void abort();
	
}
