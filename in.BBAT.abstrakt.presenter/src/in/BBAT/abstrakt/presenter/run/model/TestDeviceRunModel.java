package in.BBAT.abstrakt.presenter.run.model;

import java.sql.Timestamp;

import org.eclipse.swt.graphics.Image;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.abstrakt.gui.model.IGUITreeNode;
import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.data.model.Entities.AbstractEntity;
import in.BBAT.data.model.Entities.TestDeviceRunEntity;
import in.BBAT.data.model.Entities.TestRunEntity;
import in.BBAT.data.model.Entities.TestRunInfoEntity;

public class TestDeviceRunModel extends AbstractTreeModel {

	protected TestDeviceRunModel(AbstractTreeModel parentNode,AbstractEntity entity) {
		super(parentNode, entity);
	}

	public TestDeviceRunModel(TestDeviceRunEntity childEntties) {
		super(null,childEntties);
	}

	public TestDeviceRunModel(TestRunModel runModel,AndroidDevice device) {
		super(runModel,new TestDeviceRunEntity((TestRunEntity) runModel.getEntity(),device.getDeviceEntity()));
	}

	@Override
	public void setParent(IGUITreeNode parent) {
		super.setParent(parent);
		((TestDeviceRunEntity)getEntity()).setTestRun((TestRunEntity) ((TestRunModel)parent).getEntity());
	}

	@Override
	public String getLabel() {
		return null;
	}

	@Override
	public Image getImage() {
		return null;
	}

	protected IGUITreeNode produceParent(AbstractEntity childEntties) {
		return new TestRunModel((TestRunEntity) childEntties);
	}

	@Override
	protected AbstractTreeModel getChild(AbstractEntity childEntity)
			throws Exception {
		return new TestRunInstanceModel(this,(TestRunInfoEntity) childEntity);
	}

	public void setStatus(TestStatus status){
		((TestDeviceRunEntity)getEntity()).setStatus(status.getStatus());
	}

	public String getStatus(){
		return ((TestDeviceRunEntity)getEntity()).getStatus();
	}

	public void setStartTime(long timeInMilis){
		((TestDeviceRunEntity)getEntity()).setStartTime(new Timestamp(timeInMilis));
	}

	public void setEndTime(long timeInMilis){
		((TestDeviceRunEntity)getEntity()).setEndTime(new Timestamp(timeInMilis));
	}

	public Timestamp getStartTime(){
		return ((TestDeviceRunEntity)getEntity()).getStartTime();
	} 
	public String getDeviceName(){
		return ((TestDeviceRunEntity)getEntity()).getDevice().getDeviceId();
	}

}
