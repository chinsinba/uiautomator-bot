package in.BBAT.abstrakt.presenter.run.model;
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
