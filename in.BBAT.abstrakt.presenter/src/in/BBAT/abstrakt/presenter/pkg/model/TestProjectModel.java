package in.BBAT.abstrakt.presenter.pkg.model;

import in.BBAT.data.model.Entities.AbstractEntity;
import in.BBAT.data.model.Entities.TestProjectEntity;
import in.BBAT.data.model.Entities.TestSuiteEntity;

import java.io.File;

import org.eclipse.swt.graphics.Image;

/**
 * 
 * @author Syed Mehtab
 *
 */
public class TestProjectModel extends AbstractProjectTree {


	protected TestProjectModel(TestProjectEntity entity) throws Exception {
		super(null,entity, entity.getName(),false);
	}

	protected TestProjectModel(TestProjectEntity entity,boolean createResource) throws Exception {
		super(null,entity, entity.getName(),createResource);
	}

	public TestProjectModel(String projectName) throws Exception {
		this(new TestProjectEntity(projectName),true);
	}

	@Override
	public String getLabel() {
		return getName();
	}

	public String getName() {
		return ((TestProjectEntity)getEntity()).getName();
	}

	public Image getImage() {
		return null;
	}

	@Override
	protected AbstractProjectTree getChild(AbstractEntity childEntity) throws Exception {
		return new TestSuiteModel(this,(TestSuiteEntity) childEntity);
	}

	@Override
	public void setName(String name) {
		((TestProjectEntity)getEntity()).setName(name);
	}

	@Override
	public void createResource() {
		File f = new File(getPath());
		f.mkdir();
	}

	public void setDescription(String description) {
		((TestProjectEntity)getEntity()).setDescription(description);		
	}

	public String getDescription()
	{
		return ((TestProjectEntity)getEntity()).getDescription();
	}
}
