package in.BBAT.abstrakt.presenter.run.model;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.data.model.Entities.AbstractEntity;
import in.BBAT.data.model.Entities.TestDeviceRunEntity;
import in.BBAT.data.model.Entities.TestRunEntity;
import in.BBAT.data.model.Entities.TestRunInfoEntity;

import org.eclipse.swt.graphics.Image;

/**
 * 
 * @author syed Mehtab
 *
 */
public class TestRunModel extends AbstractTreeModel {

	protected TestRunModel(TestRunEntity entity) {
		super(null,entity);
	}

	public TestRunModel() {
		super(null,new TestRunEntity());
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
	protected AbstractTreeModel getChild(AbstractEntity childEntity) {
		return new TestDeviceRunModel(this,(TestDeviceRunEntity) childEntity);
	}

	
}
