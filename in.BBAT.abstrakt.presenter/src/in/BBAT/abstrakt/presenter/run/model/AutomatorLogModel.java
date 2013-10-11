package in.BBAT.abstrakt.presenter.run.model;

import in.BBAT.data.model.Entities.AutomatorLogEntity;
import in.BBAT.data.model.Entities.TestRunInfoEntity;

public class AutomatorLogModel {

	private AutomatorLogEntity logEntity;

	public AutomatorLogModel( AutomatorLogEntity logEntity) {
		this.logEntity =logEntity;
	}

	public AutomatorLogModel(TestRunInstanceModel model ,String logMessage) {
		logEntity = new AutomatorLogEntity((TestRunInfoEntity) model.getEntity(),logMessage);
	}

	public void save(){
		logEntity.save();
	}
	
	public String getMessage(){
		return logEntity.getMessage();
	}
}
