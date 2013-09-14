package in.BBAT.presenter.developer.handlers.testcaseBrowser;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;

import in.BBAT.presenter.developer.handlers.BBATHandler;
import in.bbat.presenter.views.developer.TestCaseBrowserView;

public abstract class AbstractTestCaseBrowserHandler extends BBATHandler {

	@Override
	public Object run(ExecutionEvent event) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEnabled() {

		TestCaseBrowserView view  = (TestCaseBrowserView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestCaseBrowserView.ID);
		ISelection selection = view.getSelectedElements();
		List<?> list = null;
		if(selection instanceof IStructuredSelection)
		{
			list = ((IStructuredSelection) selection).toList();
		}

		return isEnabled(list);
	}

}
