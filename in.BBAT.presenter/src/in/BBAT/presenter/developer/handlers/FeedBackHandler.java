package in.BBAT.presenter.developer.handlers;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.swt.program.Program;

public class FeedBackHandler extends BBATHandler {

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		return null;
	}

	@Override
	public Object run(ExecutionEvent event) {
		Program.launch("mailto:uiautomator.bot@gmail.com?subject=FeedBack");
		return null;
	}

	@Override
	public boolean isEnabled(List<?> object) {
		return true;
	}

}
