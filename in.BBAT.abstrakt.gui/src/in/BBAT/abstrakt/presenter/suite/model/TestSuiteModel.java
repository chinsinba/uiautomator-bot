package in.BBAT.abstrakt.presenter.suite.model;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.abstrakt.gui.model.IGUITreeNode;
import in.bbat.data.model.Entities.IBBATEntity;

import java.util.List;

import org.eclipse.swt.graphics.Image;
/**
 * 
 * @author syed mehtab
 *
 */

public class TestSuiteModel  extends AbstractTreeModel{


	protected TestSuiteModel(IBBATEntity entity) {
		super(entity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getLabel() {
		return null;
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	protected List<IGUITreeNode> produceChildren(List<IBBATEntity> childEntties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IGUITreeNode produceParent(IBBATEntity childEntties) {
		return null;
	}

	@Override
	protected IGUITreeNode getChild(IBBATEntity childEntity) {
		return new TestSuiteCaseModel(childEntity);
	}

}
