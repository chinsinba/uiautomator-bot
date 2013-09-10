package in.BBAT.presenter.developer.handlers.testcaseBrowser;

import org.eclipse.core.commands.ExecutionEvent;

import in.BBAT.abstrakt.presenter.pkg.model.TestProjectModel;
import in.BBAT.dataMine.manager.MineManager;
import in.BBAT.presenter.developer.handlers.BBATHandler;

public class CreateTestProjectHandler extends BBATHandler {

	@Override
	protected Object run(ExecutionEvent event) {
		TestProjectModel newTestProject = new TestProjectModel("TestIT");
		newTestProject.setName("dsdgsdjs");
		MineManager.getInstance().beginTransaction();
		newTestProject.save();
		MineManager.getInstance().commitTransaction();
		return null;
	}

}
