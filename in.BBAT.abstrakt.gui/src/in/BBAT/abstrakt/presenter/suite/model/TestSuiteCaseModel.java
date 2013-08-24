package in.BBAT.abstrakt.presenter.suite.model;

import org.eclipse.swt.graphics.Image;

import in.BBAT.abstrakt.gui.model.IGUITreeNode;
import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;
import in.bbat.data.model.Entities.IBBATEntity;

/**
 * 
 * @author Syed Mehtab
 *
 */
public class TestSuiteCaseModel extends TestCaseModel {

	protected TestSuiteCaseModel(IBBATEntity entity) {
		super(entity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return super.getImage();
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return super.getLabel();
	}
	
	@Override
	protected IGUITreeNode produceParent(IBBATEntity childEntties) {
		// TODO Auto-generated method stub
		return new TestSuiteModel(childEntties);
	}
}
