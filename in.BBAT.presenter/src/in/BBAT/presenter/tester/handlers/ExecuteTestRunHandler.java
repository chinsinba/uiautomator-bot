package in.BBAT.presenter.tester.handlers;

import in.BBAT.abstrakt.presenter.run.model.TestRunCase;
import in.BBAT.abstrakt.presenter.run.model.TestRunManager;
import in.BBAT.presenter.developer.handlers.BBATHandler;
import in.BBAT.testRunner.runner.TestRunner;
import in.BBAT.testRunner.runner.UiAutoTestCaseJar;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;

public class ExecuteTestRunHandler extends BBATHandler {

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object run(ExecutionEvent event) {
		List<String> testScriptPaths = new ArrayList<String>();
		for (TestRunCase testRunCase : TestRunManager.getInstance().getTestRunCases()) {
			testScriptPaths.add(testRunCase.getTestcase().getTestScriptPath());
		}

		TestRunner runner = new TestRunner(new UiAutoTestCaseJar(testScriptPaths),null);

		return null;
	}

	@Override
	public boolean isEnabled(List<?> object) {
		// TODO Auto-generated method stub
		return true;
	}

}
