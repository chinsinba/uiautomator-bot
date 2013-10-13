package in.BBAT.abstrakt.presenter.run.model;

import org.eclipse.swt.graphics.Image;

import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;

public class TestRunCase {

	private TestCaseModel testcase;
	private TestStatus status;

	public TestRunCase(TestCaseModel testcase){
		this.testcase = testcase;
		this.status = TestStatus.NOTEXECUTED;
	}

	public TestCaseModel getTestcase() {
		return testcase;
	}

	public void setTestcase(TestCaseModel testcase) {
		this.testcase = testcase;
	}

	public String getStatus() {
		return status.getStatus();
	}

	public void setStatus(TestStatus status) {
		this.status = status;
	}

	public Image getImage(){
		return null;
	}

	public String getLabel(){
		return testcase.getName();
	}

	public enum TestStatus{
		PASS("PASS"),FAIL("FAIL"),
		ERROR("ERROR"),EXECUTING("EXECUTING"),
		NOTEXECUTED("NOTEXECUTED"),EXECUTED("EXECUTED");

		String value;
		TestStatus(String value){
			this.value = value;
		}

		public String getStatus(){
			return value;
		}

	}
}
