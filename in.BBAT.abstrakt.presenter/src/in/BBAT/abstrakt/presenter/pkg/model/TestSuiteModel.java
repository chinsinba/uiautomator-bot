package in.BBAT.abstrakt.presenter.pkg.model;

import in.BBAT.data.model.Entities.AbstractEntity;
import in.BBAT.data.model.Entities.TestCaseEntity;
import in.BBAT.data.model.Entities.TestProjectEntity;
import in.BBAT.data.model.Entities.TestSuiteEntity;
import in.bbat.abstrakt.gui.BBATImageManager;

import java.io.File;

import org.eclipse.swt.graphics.Image;

public class TestSuiteModel extends AbstractProjectTree{

	protected TestSuiteModel(TestProjectModel parent,TestSuiteEntity entity) throws Exception {
		super(parent,entity,entity.getName(),false);
	}

	protected TestSuiteModel(TestProjectModel parent,TestSuiteEntity entity,boolean createResource) throws Exception {
		super(parent,entity,entity.getName(),createResource);
	}


	public TestSuiteModel(TestProjectModel parent,String name) throws Exception{
		this(parent,new TestSuiteEntity((TestProjectEntity) parent.getEntity(),name),true);
	}

	public String getName() {
		return ((TestSuiteEntity)getEntity()).getName();
	}

	@Override
	public String getLabel() {
		return getName();
	}

	@Override
	public Image getImage() {
		return BBATImageManager.getInstance().getImage(BBATImageManager.TESTSUITE_GIF_16);
	}

	@Override
	protected AbstractProjectTree getChild(AbstractEntity childEntity) throws Exception {
		return new TestCaseModel(this,(TestCaseEntity) childEntity);
	}

	@Override
	public void createResource() {
		File f = new File(getPath());
		f.mkdirs();
	}

	@Override
	public String getDescription() {
		return ((TestSuiteEntity)getEntity()).getDescription();
	}

	@Override
	public void setDescription(String description) {
		((TestSuiteEntity)getEntity()).setDescription(description);
	}

}
