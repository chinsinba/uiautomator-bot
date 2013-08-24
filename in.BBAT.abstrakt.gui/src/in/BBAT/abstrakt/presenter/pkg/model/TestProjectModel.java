package in.BBAT.abstrakt.presenter.pkg.model;

import java.util.List;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.abstrakt.gui.model.IGUITreeNode;
import in.bbat.data.model.Entities.IBBATEntity;

import org.eclipse.swt.graphics.Image;

/**
 * 
 * @author Syed Mehtab
 *
 */
public class TestProjectModel extends AbstractTreeModel{

	protected TestProjectModel(IBBATEntity entity) {
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
		return null;
	}

	@Override
	protected IGUITreeNode produceParent(IBBATEntity childEntties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IGUITreeNode getChild(IBBATEntity childEntity) {
		// TODO Auto-generated method stub
		return new TestCaseModel(childEntity);
	}

}
