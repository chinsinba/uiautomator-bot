package in.BBAT.presenter.developer.handlers;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.swt.program.Program;

public class ReportBugHandler extends BBATHandler {

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object run(ExecutionEvent event) {
		Program.launch("http://sourceforge.net/p/uiautomator/discussion/?source=navbar");
		return null;
	}

	@Override
	public boolean isEnabled(List<?> object) {
		return true;
	}

}
