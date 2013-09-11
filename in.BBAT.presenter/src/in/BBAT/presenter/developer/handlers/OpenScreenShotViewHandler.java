package in.BBAT.presenter.developer.handlers;

import in.bbat.presenter.views.developer.ScreenShotView;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class OpenScreenShotViewHandler extends BBATHandler {

	@Override
	protected Object run(ExecutionEvent event) {

		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ScreenShotView.ID);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		return null;
	}

}
