package in.BBAT.presenter.developer.handlers.testcaseBrowser;

import java.util.List;

import in.BBAT.abstrakt.presenter.pkg.model.TestProjectModel;
import in.BBAT.dataMine.manager.MineManager;
import in.bbat.presenter.views.BBATViewPart;
import in.bbat.presenter.views.developer.TestCaseBrowserView;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.PlatformUI;

public class CreateTestProjectHandler extends AbstractTestCaseBrowserHandler {

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		TestProjectModel newTestProject = null;
		try {
			newTestProject = new TestProjectModel("TestIT");
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	@Override
	public boolean isEnabled(List<?> object) {
		return true;
	}

}
