package in.BBAT.presenter.developer.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public abstract class BBATHandler extends AbstractHandler {

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

	abstract protected Object run(ExecutionEvent event);

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isHandled() {
		return true;
	}

}
