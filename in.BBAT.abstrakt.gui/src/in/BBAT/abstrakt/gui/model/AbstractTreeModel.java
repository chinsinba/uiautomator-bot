package in.BBAT.abstrakt.gui.model;

import java.util.ArrayList;
import java.util.List;

import in.BBAT.data.model.Entities.AbstractEntity;
import in.BBAT.data.model.Entities.IBBATEntity;

/**
 * 
 * @author Syed Mehtab
 *
 */
public abstract class  AbstractTreeModel implements IGUITreeNode {

	AbstractEntity entity;

	private String name;


	protected AbstractTreeModel(AbstractEntity entity){
		this.entity =entity;
	}

	@Override
	public IGUITreeNode getParent(){
		return  produceParent(entity.getParent());
	}

	@Override
	public List<IGUITreeNode> getChildren() {
		List<IGUITreeNode> childNodes = new ArrayList<IGUITreeNode>();
		List<AbstractEntity> childEntities = (List<AbstractEntity>) entity.getChildren();
		if(childEntities==null)
			return null;
		for(AbstractEntity childEntity: childEntities){
			childNodes.add(getChild(childEntity));
		}
		return childNodes;
	}

	protected abstract IGUITreeNode getChild(AbstractEntity childEntity) ;

	public void save(){
		entity.save();
	}

	public void delete(){
		entity.delete();
	}

	public void update()
	{
		entity.update();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected abstract List<IGUITreeNode> produceChildren(List<AbstractEntity> childEntties );
	protected abstract IGUITreeNode produceParent(AbstractEntity childEntties );
}
