package in.BBAT.abstrakt.presenter.suite.model;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.abstrakt.gui.model.UserModel;
import in.BBAT.data.model.Entities.AbstractEntity;
import in.BBAT.data.model.Entities.TestSuiteEntity;

import org.eclipse.swt.graphics.Image;
/**
 * 
 * @author syed mehtab
 *
 */

public class TestSuiteModel  extends AbstractTreeModel{


	protected TestSuiteModel(TestSuiteEntity entity) {
		super(null,entity);
	}


	/*public TestSuiteModel(){
		super(null,new TestSuiteEntity(null,""));
	}
*/
	@Override
	public String getLabel() {
		return getName();
	}

	@Override
	public Image getImage() {
		return null;
	}


	public long getCreatedTime(){
		return 0;
	}

	public void setCreatedTime(long milliseconds)
	{

	}

	public UserModel createdBy(){
		return null;
	}



	@Override
	protected AbstractTreeModel getChild(AbstractEntity childEntity) throws Exception {
		return new TestSuiteCaseModel((TestSuiteEntity) childEntity,this);
	}

}
