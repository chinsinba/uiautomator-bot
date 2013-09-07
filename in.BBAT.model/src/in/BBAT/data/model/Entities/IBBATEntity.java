package in.BBAT.data.model.Entities;

import java.util.List;

/**
 * 
 * @author Syed Mehtab
 *
 */
public interface IBBATEntity {

	List<? extends IBBATEntity> getChildren();

	void save();

	void delete();

	IBBATEntity update();

	IBBATEntity getParent();
	
	void addChild(IBBATEntity childEntity);
	
	void removeChild(IBBATEntity childEntity);
}
