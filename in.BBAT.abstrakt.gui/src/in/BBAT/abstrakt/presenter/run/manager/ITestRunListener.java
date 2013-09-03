package in.BBAT.abstrakt.presenter.run.manager;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.abstrakt.presenter.run.model.TestRunModel;

public interface ITestRunListener {

	void preTestRunStart(TestRunModel testRunModel,AndroidDevice device);

	void postTestRunStart(TestRunModel testRunModel,AndroidDevice device);

}
