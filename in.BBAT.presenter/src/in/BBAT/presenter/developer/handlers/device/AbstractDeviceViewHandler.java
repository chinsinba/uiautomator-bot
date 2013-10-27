package in.BBAT.presenter.developer.handlers.device;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;

import in.BBAT.presenter.developer.handlers.BBATHandler;
import in.bbat.presenter.views.developer.DeveloperDeviceView;

public abstract class AbstractDeviceViewHandler  extends BBATHandler{

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
		DeveloperDeviceView view  = (DeveloperDeviceView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(DeveloperDeviceView.ID);
		ISelection selection = view.getSelectedElements();
		List<?> list = null;
		if(selection instanceof IStructuredSelection)
		{
			list = ((IStructuredSelection) selection).toList();
		}
		return list;
	}

}
