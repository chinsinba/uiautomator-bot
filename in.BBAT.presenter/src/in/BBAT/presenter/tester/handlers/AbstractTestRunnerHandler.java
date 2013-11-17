package in.BBAT.presenter.tester.handlers;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;

import in.BBAT.presenter.developer.handlers.BBATHandler;
import in.bbat.logger.BBATLogger;
import in.bbat.presenter.views.tester.TestRunnerView;

public abstract class AbstractTestRunnerHandler extends BBATHandler {

	private static final Logger LOG = BBATLogger.getLogger(AbstractTestRunnerHandler.class.getName());
	@Override
	public Object run(ExecutionEvent event) {
		return run(event, getSelectedList());
	}

	@Override
	public boolean isEnabled() {
		List<?> list = getSelectedList();
		return isEnabled(list);
	}

	private List<?> getSelectedList() {
		TestRunnerView view  = (TestRunnerView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestRunnerView.ID);
		List<?> list = null;
		if(view == null){
			return list;
		}
		ISelection selection = view.getSelectedElements();
		
		if(selection instanceof IStructuredSelection)
		{
			list = ((IStructuredSelection) selection).toList();
		}
		return list;
	}

}
