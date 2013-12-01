package in.BBAT.data.model.Entities;

import in.BBAT.dataMine.manager.MineManager;

import java.util.List;

public abstract class AbstractEntity implements IBBATEntity {

	@Override
	public void save() {
		MineManager.getInstance().persist(this);
	}

	@Override
	public void delete() {
		MineManager.getInstance().remove(this);
	}

	@Override
	public AbstractEntity update() {
		return (AbstractEntity) MineManager.getInstance().merge(this);
	}

	@Override
	public AbstractEntity getParent() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public List<? extends AbstractEntity> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void addChild(IBBATEntity childEntity) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void removeChild(IBBATEntity childEntity) {
		
	}

	public int getId() {
		return 0;
	}
	
}
