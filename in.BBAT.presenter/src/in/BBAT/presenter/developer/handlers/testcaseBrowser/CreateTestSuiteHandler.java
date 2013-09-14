package in.BBAT.presenter.developer.handlers.testcaseBrowser;

import in.BBAT.abstrakt.presenter.pkg.model.TestProjectModel;
import in.BBAT.abstrakt.presenter.pkg.model.TestSuiteModel;
import in.BBAT.dataMine.manager.MineManager;
import in.bbat.presenter.views.BBATViewPart;
import in.bbat.presenter.views.developer.TestCaseBrowserView;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.PlatformUI;

public class CreateTestSuiteHandler extends AbstractTestCaseBrowserHandler {


	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		if(!selectedObjects.isEmpty()){
			TestProjectModel projModel = (TestProjectModel) selectedObjects.get(0);
			try {
				TestSuiteModel newTestSuite = new TestSuiteModel(projModel, "helloWorld");

				MineManager.getInstance().beginTransaction();
				newTestSuite.save();
				MineManager.getInstance().commitTransaction();
				BBATViewPart view = (BBATViewPart) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestCaseBrowserView.ID);
				try {
					view.refresh();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	@Override
	public boolean isEnabled(List<?> object) {
		if(!object.isEmpty())
		{
			if(object.size()==1)
			{
				if(object.get(0) instanceof TestProjectModel)
				{
					return true;
				}
			}
		}
		return false;
	}

}
