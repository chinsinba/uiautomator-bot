package in.BBAT.abstrakt.gui.model;

import in.BBAT.data.model.Entities.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Syed Mehtab
 */
public abstract class  AbstractTreeModel implements IGUITreeNode {

	private AbstractEntity entity;

	private AbstractTreeModel parent;

	private ArrayList<AbstractTreeModel> childNodes = new ArrayList<AbstractTreeModel>();

	protected AbstractTreeModel(AbstractTreeModel parentNode , AbstractEntity entity){
		this.parent = parentNode;
		this.setEntity(entity);
		/*if(parent!=null)
			this.parent.addChild(this);*/
	}

	@Override
	public AbstractTreeModel getParent(){
		return  parent;
	}

	@Override
	public void setParent(IGUITreeNode parent) {
		this.parent=(AbstractTreeModel) parent;		
	}
	
	@Override
	public List<AbstractTreeModel> getChildren() throws Exception {
		childNodes = new ArrayList<AbstractTreeModel>();
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
			childNodes.add((AbstractTreeModel) childNode);
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

	public void delete() throws Exception{
		getEntity().delete();
	}

	public void update()
	{
		getEntity().update();
	}

	public AbstractEntity getEntity() {
		return entity;
	}

	public void setEntity(AbstractEntity entity) {
		this.entity = entity;
	}

	public String getName()
	{
		return "";	
	}

	public void setName(String name){

	}

	public int getId() {
		return entity.getId();
	}
			

	protected abstract AbstractTreeModel getChild(AbstractEntity childEntity)throws Exception ;
	//	protected abstract IGUITreeNode produceParent(AbstractEntity childEntties );
}
