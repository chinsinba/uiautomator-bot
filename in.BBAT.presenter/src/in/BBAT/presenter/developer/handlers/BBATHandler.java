package in.BBAT.presenter.developer.handlers;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public abstract class BBATHandler extends AbstractHandler implements IBBATHandler {

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
