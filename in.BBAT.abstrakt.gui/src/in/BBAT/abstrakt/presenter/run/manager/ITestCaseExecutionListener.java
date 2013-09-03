package in.BBAT.abstrakt.presenter.run.manager;

import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;

public interface ITestCaseExecutionListener {

	boolean canExecute(TestCaseModel testCaseModel);
	
	void preTestCaseStart(TestCaseModel testCaseModel);

	void postTestCaseStart(TestCaseModel testCaseModel);
}
