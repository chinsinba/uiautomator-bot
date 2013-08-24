package in.BBAT.abstrakt.gui.model;

import java.util.ArrayList;
import java.util.List;

import in.BBAT.data.model.Entities.IBBATEntity;

/**
 * 
 * @author Syed Mehtab
 *
 */
public abstract class  AbstractTreeModel implements IGUITreeNode {

	IBBATEntity entity;

	private String name;


	protected AbstractTreeModel(IBBATEntity entity){
		this.entity =entity;
	}

	@Override
	public IGUITreeNode getParent(){
		return  produceParent(entity.getParent());
	}

	@Override
	public List<IGUITreeNode> getChildren() {
		List<IGUITreeNode> childNodes = new ArrayList<IGUITreeNode>();
		List<IBBATEntity> childEntities = entity.getChildren();
		if(childEntities==null)
			return null;
		for(IBBATEntity childEntity: childEntities){
			childNodes.add(getChild(childEntity));
		}
		return childNodes;
	}

	protected abstract IGUITreeNode getChild(IBBATEntity childEntity) ;

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

	protected abstract List<IGUITreeNode> produceChildren(List<IBBATEntity> childEntties );
	protected abstract IGUITreeNode produceParent(IBBATEntity childEntties );
}
