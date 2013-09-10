package in.BBAT.abstrakt.presenter.suite.model;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.abstrakt.gui.model.IGUITreeNode;
import in.BBAT.abstrakt.gui.model.UserModel;
import in.BBAT.data.model.Entities.AbstractEntity;
import in.BBAT.data.model.Entities.TestSuiteEntity;

import java.util.List;

import org.eclipse.swt.graphics.Image;
/**
 * 
 * @author syed mehtab
 *
 */

public class TestSuiteModel  extends AbstractTreeModel{


	protected TestSuiteModel(TestSuiteEntity entity) {
		super(entity);
		// TODO Auto-generated constructor stub
	}


	public TestSuiteModel(){
		super(new TestSuiteEntity());
	}

	@Override
	public String getLabel() {
		return getName();
	}

	@Override
	public Image getImage() {
		return null;
	}


	public long getCreatedTime(){
		return 0;
	}

	public void setCreatedTime(long milliseconds)
	{

	}

	public UserModel createdBy(){
		return null;
	}


	@Override
	protected List<IGUITreeNode> produceChildren(List<AbstractEntity> childEntties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IGUITreeNode produceParent(AbstractEntity childEntties) {
		return null;
	}

	@Override
	protected IGUITreeNode getChild(AbstractEntity childEntity) {
		return new TestSuiteCaseModel((TestSuiteEntity) childEntity,this);
	}

}
