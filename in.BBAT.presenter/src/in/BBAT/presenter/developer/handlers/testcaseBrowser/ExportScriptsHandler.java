package in.BBAT.presenter.developer.handlers.testcaseBrowser;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;

public class ExportScriptsHandler extends AbstractTestCaseBrowserHandler {

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		return null;
	}

	
	@Override
	public boolean isEnabled(List<?> object) {
		return false;
	}
}
