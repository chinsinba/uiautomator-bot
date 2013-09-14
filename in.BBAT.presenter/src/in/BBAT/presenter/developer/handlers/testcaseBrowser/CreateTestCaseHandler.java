package in.BBAT.presenter.developer.handlers.testcaseBrowser;

import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;
import in.BBAT.abstrakt.presenter.pkg.model.TestSuiteModel;
import in.BBAT.dataMine.manager.MineManager;
import in.bbat.presenter.views.BBATViewPart;
import in.bbat.presenter.views.developer.TestCaseBrowserView;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.PlatformUI;

public class CreateTestCaseHandler extends AbstractTestCaseBrowserHandler {

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		if(!selectedObjects.isEmpty()){
			TestSuiteModel suiteModel = (TestSuiteModel) selectedObjects.get(0);
			try {
				TestCaseModel newTestCase = new TestCaseModel(suiteModel, "helloWorld");

				MineManager.getInstance().beginTransaction();
				newTestCase.save();
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
				if(object.get(0) instanceof TestSuiteModel)
				{
					return true;
				}
			}
		}
		return false;
	}

}
