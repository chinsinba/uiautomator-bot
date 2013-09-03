package in.BBAT.abstrakt.presenter.run.manager;

import in.BBAT.abstrakt.presenter.run.model.TestRunModel;

public interface ITestRunListener {

	void preTestRunStart(TestRunModel testRunModel);
	
	void postTestRunStart(TestRunModel testRunModel);
	
}
