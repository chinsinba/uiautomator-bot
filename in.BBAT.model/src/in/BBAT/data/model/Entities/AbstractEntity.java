package in.BBAT.data.model.Entities;

import in.BBAT.dataMine.manager.MineManager;

import java.util.List;

public abstract class AbstractEntity implements IBBATEntity {

	public List<AbstractEntity> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save() {
		MineManager.persist(this);
	}

	@Override
	public void delete() {
		MineManager.remove(this);
	}

	@Override
	public AbstractEntity update() {
		return (AbstractEntity) MineManager.merge(this);
	}

	@Override
	public AbstractEntity getParent() {
		return null;
	}

}
