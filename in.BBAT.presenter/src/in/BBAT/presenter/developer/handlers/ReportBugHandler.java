package in.BBAT.presenter.developer.handlers;

import in.bbat.logger.BBATLogger;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.swt.program.Program;

public class ReportBugHandler extends BBATHandler {
	
	private static final Logger LOG = BBATLogger.getLogger(ReportBugHandler.class.getName());

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object run(ExecutionEvent event) {
		LOG.info("Report Bug");
		Program.launch("http://sourceforge.net/p/uiautomator/discussion/?source=navbar");
		return null;
	}

	@Override
	public boolean isEnabled(List<?> object) {
		return true;
	}

}
