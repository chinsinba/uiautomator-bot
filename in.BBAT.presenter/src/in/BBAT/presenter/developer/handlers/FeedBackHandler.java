package in.BBAT.presenter.developer.handlers;

import in.BBAT.presenter.internal.TestRunExecutor;
import in.bbat.logger.BBATLogger;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.swt.program.Program;

public class FeedBackHandler extends BBATHandler {

	private static final Logger LOG = BBATLogger.getLogger(FeedBackHandler.class.getName());

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		return null;
	}

	@Override
	public Object run(ExecutionEvent event) {
		LOG.info("Show FeedBack");
		Program.launch("mailto:uiautomator.bot@gmail.com?subject=FeedBack");
		return null;
	}

	@Override
	public boolean isEnabled(List<?> object) {
		return true;
	}

}
