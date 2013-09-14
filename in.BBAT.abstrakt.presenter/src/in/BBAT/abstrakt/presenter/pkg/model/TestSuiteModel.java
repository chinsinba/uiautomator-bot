package in.BBAT.abstrakt.presenter.pkg.model;

import in.BBAT.data.model.Entities.AbstractEntity;
import in.BBAT.data.model.Entities.TestCaseEntity;
import in.BBAT.data.model.Entities.TestSuiteEntity;

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
		this(parent,new TestSuiteEntity(name),true);
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
	protected AbstractProjectTree getChild(AbstractEntity childEntity) throws Exception {
		return new TestCaseModel(this,(TestCaseEntity) childEntity);
	}

	@Override
	public void createResource() {
		File f = new File(getPath());
		f.mkdir();
	}

}
