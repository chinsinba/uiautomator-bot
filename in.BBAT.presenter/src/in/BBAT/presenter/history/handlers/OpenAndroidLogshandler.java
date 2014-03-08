package in.BBAT.presenter.history.handlers;

import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;

public class OpenAndroidLogshandler extends AbstractTestRunInfoHandler {

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEnabled(List<?> object) {
		if(!object.isEmpty())
		{
			if(object.size()==1)
			{
				if(object.get(0) instanceof TestRunInstanceModel)
				{
					return true;
				}
			}
		}
		return false;
	}

}
