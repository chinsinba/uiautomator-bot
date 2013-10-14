package in.BBAT.abstrakt.presenter.run.model;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.abstrakt.gui.model.IGUITreeNode;
import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;
import in.BBAT.data.model.Entities.AbstractEntity;
import in.BBAT.data.model.Entities.AutomatorLogEntity;
import in.BBAT.data.model.Entities.TestCaseEntity;
import in.BBAT.data.model.Entities.TestDeviceRunEntity;
import in.BBAT.data.model.Entities.TestRunInfoEntity;
import in.BBAT.dataMine.manager.LogsMineManager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.android.ddmlib.logcat.LogCatMessage;

/**
 * 
 * @author syed Mehtab
 *
 */
public class TestRunInstanceModel extends AbstractTreeModel {

	private TestCaseModel testCaseModel ;
	private boolean showLogs;
	private List<AutomatorLogModel> autoLogs = new ArrayList<AutomatorLogModel>();
	private List<DeviceLogModel> deviceLogs = new ArrayList<DeviceLogModel>();

	protected TestRunInstanceModel(TestDeviceRunModel parent,TestRunInfoEntity entity) {
		super(parent,entity);
	}

	public TestRunInstanceModel(TestDeviceRunModel parent,TestCaseModel testCase,String status) {
		super(parent,new TestRunInfoEntity((TestDeviceRunEntity) parent.getEntity(),(TestCaseEntity) testCase.getEntity(),status));
		this.setTestCaseModel(testCase);
	}
	@Override
	public String getLabel() {
		return getName();
	}

	@Override
	public Image getImage() {
		return null;
	}

	protected IGUITreeNode produceParent(AbstractEntity childEntties) {
		return new TestDeviceRunModel((TestDeviceRunEntity) childEntties);
	}

	@Override
	protected AbstractTreeModel getChild(AbstractEntity childEntity) {
		return null;
	}

	public String getName(){
		return getTestCaseModel().getName();
	}

	public TestCaseModel getTestCaseModel() {
		return testCaseModel;
	}

	public void setTestCaseModel(TestCaseModel testCaseModel) {
		this.testCaseModel = testCaseModel;
	}

	public String getStatus() {
		return ((TestRunInfoEntity)getEntity()).getVerdict();
	}

	public void setStatus(String status){
		((TestRunInfoEntity)getEntity()).setVerdict(status);
	}

	public void addLogs(List<LogCatMessage> messsages ){

	}

	public boolean isShowLogs() {
		return showLogs;
	}

	public void setShowLogs(boolean showLogs) {
		this.showLogs = showLogs;
	}

	public void addAutoLog(AutomatorLogModel log){
		autoLogs.add(log);

	}
	
	public void addDeviceLog(DeviceLogModel log){
		deviceLogs.add(log);
	}

	
	public List<DeviceLogModel> getDeviceLogs(){
		return deviceLogs;
	}
	
	public List<AutomatorLogModel> getAutoLogs(){
		return autoLogs;
	}

	public void setTimeTaken(long timeTaken)
	{
		((TestRunInfoEntity)getEntity()).setTimeTaken(timeTaken);
	}

	public long getTimeTaken(){
		return ((TestRunInfoEntity)getEntity()).getTimeTaken();
	}

	public void addAutoLog(String message){

	}

	public void setStartTime(long timeInMillis) {
		((TestRunInfoEntity)getEntity()).setStartTime(new Timestamp(timeInMillis));
	}

	public void setEndTime(long timeInMillis) {
		((TestRunInfoEntity)getEntity()).setEndTime(new Timestamp(timeInMillis));		
	}
	
	public TestCaseEntity getTestCaseEntity(){
		return ((TestRunInfoEntity)getEntity()).getTestCase();
	}

	public List<AutomatorLogModel> getAutoLogsFromDB() {
		List<AutomatorLogModel> logs = new ArrayList<AutomatorLogModel>();
		for( AutomatorLogEntity entity :LogsMineManager.getAutoLogs((TestRunInfoEntity) getEntity())){
			logs.add(new AutomatorLogModel(entity));
		}
		return logs;
	}
}
