package in.BBAT.presenter.developer.handlers.testcaseBrowser;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;

import in.BBAT.presenter.developer.handlers.BBATHandler;
import in.bbat.logger.BBATLogger;
import in.bbat.presenter.perstpectives.DeveloperPerspective;
import in.bbat.presenter.views.developer.TestCaseBrowserView;

public abstract class AbstractTestCaseBrowserHandler extends BBATHandler {

	private static final Logger LOG = BBATLogger.getLogger(AbstractTestCaseBrowserHandler.class.getName());
	@Override
	public Object run(ExecutionEvent event) {
		return run(event, getSelectedList());
	}

	@Override
	public boolean isEnabled() {

		List<?> list = getSelectedList();
		if(!PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
				getPerspective().getId().equalsIgnoreCase(DeveloperPerspective.ID)){
			return false;
		}
		return isEnabled(list);
	}

	private List<?> getSelectedList() {
		if( PlatformUI.getWorkbench().getActiveWorkbenchWindow()==null){
			return null;
		}
		TestCaseBrowserView view  = (TestCaseBrowserView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestCaseBrowserView.ID);
		ISelection selection = view.getSelectedElements();
		List<?> list = null;
		if(selection instanceof IStructuredSelection)
		{
			list = ((IStructuredSelection) selection).toList();
		}
		return list;
	}

}
