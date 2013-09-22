package in.BBAT.presenter.tester.handlers;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;

public class StopTestRunHandler extends AbstractTestRunnerHandler {

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		return null;
	}

	@Override
	public Object run(ExecutionEvent event) {
		return null;
	}

	@Override
	public boolean isEnabled(List<?> object) {
		return true;
	}

}
