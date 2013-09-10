package in.BBAT.abstrakt.presenter.pkg.model;

import java.util.List;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.abstrakt.gui.model.IGUITreeNode;
import in.BBAT.data.model.Entities.AbstractEntity;
import in.BBAT.data.model.Entities.TestProjectEntity;

import org.eclipse.swt.graphics.Image;

/**
 * 
 * @author Syed Mehtab
 *
 */
public class TestProjectModel extends AbstractTreeModel{

	private String location;

	protected TestProjectModel(TestProjectEntity entity) {
		super(entity);
		
	}

	public TestProjectModel(String projectName) {
		this(new TestProjectEntity());
		this.location = projectName;
		createProjectDir();
	}

	@Override
	public String getLabel() {
		return getName();
	}

	private void createProjectDir() {

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return ((TestProjectEntity)getEntity()).getName();
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
		return null;
	}

	@Override
	protected IGUITreeNode getChild(AbstractEntity childEntity) {
		return new TestCaseModel(childEntity);
	}

	@Override
	public void setName(String name) {
		((TestProjectEntity)getEntity()).setName(name);
	}
	
	public String getLocation(){
		return this.location;
	}

}
