package in.BBAT.abstrakt.presenter.run.manager;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunModel;

public interface ITestCaseExecutionListener {

	boolean canExecute(TestCaseModel testCaseModel,TestRunModel testRunModel,AndroidDevice device);
	
	void preTestCaseStart(TestCaseModel testCaseModel,TestRunModel testRunModel,AndroidDevice device);

	void postTestCaseStart(TestCaseModel testCaseModel,TestRunModel testRunModel,AndroidDevice device);
}
