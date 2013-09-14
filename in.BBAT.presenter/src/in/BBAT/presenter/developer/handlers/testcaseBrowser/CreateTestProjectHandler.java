package in.BBAT.presenter.developer.handlers.testcaseBrowser;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.PlatformUI;

import in.BBAT.abstrakt.presenter.pkg.model.TestProjectModel;
import in.BBAT.dataMine.manager.MineManager;
import in.BBAT.presenter.developer.handlers.BBATHandler;
import in.bbat.presenter.views.BBATViewPart;
import in.bbat.presenter.views.developer.TestCaseBrowserView;

public class CreateTestProjectHandler extends BBATHandler {

	@Override
	protected Object run(ExecutionEvent event) {
		TestProjectModel newTestProject = null;
		try {
			newTestProject = new TestProjectModel("TestIT");
		} catch (Exception e) {
			e.printStackTrace();
		}
		newTestProject.setName("TestProj");
		MineManager.getInstance().beginTransaction();
		newTestProject.save();
		MineManager.getInstance().commitTransaction();

		BBATViewPart view = (BBATViewPart) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestCaseBrowserView.ID);
		try {
			view.refresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
