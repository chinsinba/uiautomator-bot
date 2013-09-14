package in.BBAT.abstrakt.presenter.pkg.model;

import java.util.List;

import org.eclipse.swt.graphics.Image;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.abstrakt.gui.model.IGUITreeNode;
import in.BBAT.data.model.Entities.AbstractEntity;
import in.BBAT.data.model.Entities.TestProjectEntity;

public class TestSuiteModel extends AbstractTreeModel {

	protected TestSuiteModel(AbstractEntity entity) {
		super(entity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IGUITreeNode getChild(AbstractEntity childEntity) {
		return new TestCaseModel(childEntity);
	}


	@Override
	protected IGUITreeNode produceParent(AbstractEntity childEntties) {
		return new TestProjectModel((TestProjectEntity) childEntties);
	}

}
