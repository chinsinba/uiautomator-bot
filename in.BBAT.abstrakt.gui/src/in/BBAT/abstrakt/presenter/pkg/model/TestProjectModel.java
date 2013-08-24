package in.BBAT.abstrakt.presenter.pkg.model;

import java.util.List;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.abstrakt.gui.model.IGUITreeNode;
import in.BBAT.data.model.Entities.AbstractEntity;

import org.eclipse.swt.graphics.Image;

/**
 * 
 * @author Syed Mehtab
 *
 */
public class TestProjectModel extends AbstractTreeModel{

	protected TestProjectModel(AbstractEntity entity) {
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
	protected List<IGUITreeNode> produceChildren(List<AbstractEntity> childEntties) {
		return null;
	}

	@Override
	protected IGUITreeNode produceParent(AbstractEntity childEntties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IGUITreeNode getChild(AbstractEntity childEntity) {
		// TODO Auto-generated method stub
		return new TestCaseModel(childEntity);
	}

}
