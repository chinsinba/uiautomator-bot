package in.BBAT.abstrakt.presenter.pkg.model;

import in.BBAT.data.model.Entities.TestCaseEntity;
import in.BBAT.data.model.Entities.TestSuiteEntity;

import java.io.File;
import java.io.IOException;

import org.eclipse.swt.graphics.Image;

/**
 * 
 * @author Syed Mehtab
 *
 */
public class TestCaseModel extends AbstractProjectTree{

	public final static String JAVA = ".java";
	protected TestCaseModel(TestSuiteModel parent ,TestCaseEntity entity) throws Exception {
		super(parent,entity,entity.getName()+JAVA,false);

	}
	
	protected TestCaseModel(TestSuiteModel parent ,TestCaseEntity entity,boolean createResource) throws Exception {
		super(parent,entity,entity.getName()+JAVA,createResource);
	}

	public TestCaseModel(TestSuiteModel parent,String testCaseName) throws Exception{
		this(parent,new TestCaseEntity((TestSuiteEntity)parent.getEntity(),testCaseName),true);
		
	}

	@Override
	public String getLabel() {
		return getName();
	}

	@Override
	public String getName() {
		return ((TestCaseEntity)getEntity()).getName();
	}
	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public void createResource() throws IOException {
		File newFile = new File(getPath());
		newFile.createNewFile();
	}
	
	@Override
	public String getDescription() {
		return ((TestCaseEntity)getEntity()).getDescription();
	}
	
	@Override
	public void setDescription(String description) {
		((TestCaseEntity)getEntity()).setDescription(description);
	}

}
