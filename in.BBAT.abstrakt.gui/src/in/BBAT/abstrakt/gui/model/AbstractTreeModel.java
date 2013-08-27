package in.BBAT.abstrakt.gui.model;

import in.BBAT.data.model.Entities.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Syed Mehtab
 *
 */
public abstract class  AbstractTreeModel implements IGUITreeNode {

	private AbstractEntity entity;

	private String name;


	protected AbstractTreeModel(AbstractEntity entity){
		this.setEntity(entity);
	}
	

	@Override
	public IGUITreeNode getParent(){
		return  produceParent(getEntity().getParent());
	}

	@Override
	public List<IGUITreeNode> getChildren() {
		List<IGUITreeNode> childNodes = new ArrayList<IGUITreeNode>();
		List<AbstractEntity> childEntities = (List<AbstractEntity>) getEntity().getChildren();
		if(childEntities==null)
			return null;
		for(AbstractEntity childEntity: childEntities){
			childNodes.add(getChild(childEntity));
		}
		return childNodes;
	}

	protected abstract IGUITreeNode getChild(AbstractEntity childEntity) ;

	public void save(){
		getEntity().save();
	}

	public void delete(){
		getEntity().delete();
	}

	public void update()
	{
		getEntity().update();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected abstract List<IGUITreeNode> produceChildren(List<AbstractEntity> childEntties );
	protected abstract IGUITreeNode produceParent(AbstractEntity childEntties );

	public AbstractEntity getEntity() {
		return entity;
	}

	public void setEntity(AbstractEntity entity) {
		this.entity = entity;
	}
}
