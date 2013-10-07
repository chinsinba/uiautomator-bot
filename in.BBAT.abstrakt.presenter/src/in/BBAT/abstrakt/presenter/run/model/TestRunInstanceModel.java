package in.BBAT.abstrakt.presenter.run.model;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.abstrakt.gui.model.IGUITreeNode;
import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;
import in.BBAT.data.model.Entities.AbstractEntity;
import in.BBAT.data.model.Entities.TestCaseEntity;
import in.BBAT.data.model.Entities.TestDeviceRunEntity;
import in.BBAT.data.model.Entities.TestRunInfoEntity;

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

	
	public void setTimeTaken(long timeTaken)
	{
		((TestRunInfoEntity)getEntity()).setTimeTaken(timeTaken);
	}
	
	public long getTimeTaken(){
		return ((TestRunInfoEntity)getEntity()).getTimeTaken();
	}
	
	public void addAutoLog(String message){
		
	}
}
