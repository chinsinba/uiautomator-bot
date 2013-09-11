package in.BBAT.presenter.developer.handlers.testcaseBrowser;

import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;
import in.BBAT.presenter.developer.handlers.BBATHandler;

import org.eclipse.core.commands.ExecutionEvent;

public class CreateTestCaseHandler extends BBATHandler {

	@Override
	protected Object run(ExecutionEvent event) {
		
		TestCaseModel m = new TestCaseModel("");
		return null;
	}
	
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return super.isEnabled();
	}

}
