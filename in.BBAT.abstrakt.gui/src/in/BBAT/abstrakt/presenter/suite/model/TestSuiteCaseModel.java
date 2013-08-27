package in.BBAT.abstrakt.presenter.suite.model;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.abstrakt.gui.model.IGUITreeNode;
import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;
import in.BBAT.data.model.Entities.AbstractEntity;
import in.BBAT.data.model.Entities.TestSuiteEntity;

import org.eclipse.swt.graphics.Image;

/**
 * 
 * @author Syed Mehtab
 *
 */
public class TestSuiteCaseModel extends TestCaseModel {

	private TestSuiteModel parentSuite;

	protected TestSuiteCaseModel(TestSuiteEntity entity,AbstractTreeModel parent) {
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
	public IGUITreeNode getParent() {
		return getParentSuite();
	}

	public TestSuiteModel getParentSuite() {
		return parentSuite;
	}

	public void setParentSuite(TestSuiteModel parentSuite) {
		this.parentSuite = parentSuite;
	}
}
