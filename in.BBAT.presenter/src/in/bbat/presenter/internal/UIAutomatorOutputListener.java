package in.bbat.presenter.internal;

import in.BBAT.abstrakt.presenter.run.model.AutomatorLogModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.BBAT.testRunner.runner.IUiAutomatorListener;

public class UIAutomatorOutputListener implements IUiAutomatorListener {

	private TestRunInstanceModel runInstance;

	public UIAutomatorOutputListener(TestRunInstanceModel runInstance) {
		this.runInstance = runInstance;
	}

	@Override
	public void processLine(String line) {
		System.out.println(line);
		AutomatorLogModel log = new AutomatorLogModel(runInstance,line);
		log.save();
		runInstance.addAutoLog(log);
	}

}
