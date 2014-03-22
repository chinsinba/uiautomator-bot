package in.BBAT.abstrakt.presenter.run.model;

import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;
import in.BBAT.abstrakt.presenter.pkg.model.TestProjectModel;

public class TestRunCaseModel {

	private TestCaseModel testcase ;

	public TestRunCaseModel(TestCaseModel model) {
		this.setTestcase(model);
	}

	public TestCaseModel getTestcase() {
		return testcase;
	}

	public void setTestcase(TestCaseModel testcase) {
		this.testcase = testcase;
	}

	public TestProjectModel getProject(){
		return (TestProjectModel) testcase.getParent().getParent();
	}
}
