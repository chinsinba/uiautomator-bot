package in.BBAT.presenter.developer.handlers;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;

public interface IBBATHandler {

	public Object run(ExecutionEvent event);
	
	boolean isEnabled(List<?> object);
	
}
