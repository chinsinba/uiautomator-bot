package in.BBAT.presenter.developer.handlers.testcaseBrowser;

import in.BBAT.abstrakt.presenter.pkg.model.TestProjectModel;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;

public class CreateTestSuiteHandler extends AbstractTestCaseBrowserHandler {


	@Override
	public Object run(ExecutionEvent event) {
		// TODO Auto-generated method stub
		return super.run(event);
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
