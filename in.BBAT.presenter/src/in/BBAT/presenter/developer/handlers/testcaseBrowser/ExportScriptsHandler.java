package in.BBAT.presenter.developer.handlers.testcaseBrowser;

import in.bbat.logger.BBATLogger;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;

public class ExportScriptsHandler extends AbstractTestCaseBrowserHandler {

	private static final Logger LOG = BBATLogger.getLogger(ExportScriptsHandler.class.getName());
	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		LOG.info("Export scripts ");
		return null;
	}

	
	@Override
	public boolean isEnabled(List<?> object) {
		return false;
	}
}
