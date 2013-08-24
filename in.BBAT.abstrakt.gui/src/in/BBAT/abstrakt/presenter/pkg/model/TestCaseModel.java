package in.BBAT.abstrakt.presenter.pkg.model;

import java.util.List;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.abstrakt.gui.model.IGUITreeNode;
import in.bbat.data.model.Entities.IBBATEntity;

import org.eclipse.swt.graphics.Image;

/**
 * 
 * @author Syed Mehtab
 *
 */
public class TestCaseModel extends AbstractTreeModel {

	protected TestCaseModel(IBBATEntity entity) {
		super(entity);
	}

	private String scriptPath;

	public String getScriptPath() {
		return scriptPath;
	}

	public void setScriptPath(String scriptPath) {
		this.scriptPath = scriptPath;
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
	protected List<IGUITreeNode> produceChildren(List<IBBATEntity> childEntties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IGUITreeNode produceParent(IBBATEntity childEntties) {
		// TODO Auto-generated method stub
		return new TestProjectModel(childEntties);
	}

	@Override
	protected IGUITreeNode getChild(IBBATEntity childEntity) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
