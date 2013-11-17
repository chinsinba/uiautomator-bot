package in.BBAT.presenter.developer.handlers;

import in.bbat.logger.BBATLogger;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public abstract class BBATHandler extends AbstractHandler implements IBBATHandler {

	private static final Logger LOG = BBATLogger.getLogger(BBATHandler.class.getName());
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
	
		if(!chechkLicense()){
			return null;
		}
		return run(event);
	}

	private boolean chechkLicense() {
		return true;
	}


	@Override
	public boolean isHandled() {
		return true;
	}

}
