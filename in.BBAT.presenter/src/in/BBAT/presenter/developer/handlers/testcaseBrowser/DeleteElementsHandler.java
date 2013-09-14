package in.BBAT.presenter.developer.handlers.testcaseBrowser;

import in.BBAT.abstrakt.presenter.pkg.model.TestSuiteModel;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;

public class DeleteElementsHandler extends AbstractTestCaseBrowserHandler {

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
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
