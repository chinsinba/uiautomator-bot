package in.BBAT.abstrakt.presenter.run.model;

import org.eclipse.swt.graphics.Image;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.abstrakt.gui.model.IGUITreeNode;
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

	public TestDeviceRunModel() {
		super(null,new TestDeviceRunEntity());
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

}
