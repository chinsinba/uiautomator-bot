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

	private ArrayList<IGUITreeNode> childNodes;

	protected AbstractTreeModel(AbstractEntity entity){
		this.setEntity(entity);
	}

	@Override
	public IGUITreeNode getParent(){
		return  produceParent(getEntity().getParent());
	}

	@Override
	public List<IGUITreeNode> getChildren() {
		childNodes = new ArrayList<IGUITreeNode>();
		List<AbstractEntity> childEntities = (List<AbstractEntity>) getEntity().getChildren();
		if(childEntities==null)
			return null;
		for(AbstractEntity childEntity: childEntities){
			childNodes.add(getChild(childEntity));
		}
		return childNodes;
	}

	@Override
	public void addChild(IGUITreeNode childNode) {
		// the child nodes is really not necessary
		if(!childNodes.contains(childNode)){
			childNodes.add(childNode);
			getEntity().addChild(((AbstractTreeModel)childNode).getEntity());
		}
	}

	public void removeChild(IGUITreeNode childNode)
	{
		if(childNodes.contains(childNode)){
			childNodes.remove(childNode);
			getEntity().removeChild(((AbstractTreeModel)childNode).getEntity());
		}
	}

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

	public AbstractEntity getEntity() {
		return entity;
	}

	public void setEntity(AbstractEntity entity) {
		this.entity = entity;
	}

	protected abstract IGUITreeNode getChild(AbstractEntity childEntity) ;
//	protected abstract List<IGUITreeNode> produceChildren(List<AbstractEntity> childEntties );
	protected abstract IGUITreeNode produceParent(AbstractEntity childEntties );
}
