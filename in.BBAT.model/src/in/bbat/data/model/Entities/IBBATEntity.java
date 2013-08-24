package in.bbat.data.model.Entities;

import java.util.List;

/**
 * 
 * @author Syed Mehtab
 *
 */
public interface IBBATEntity {

	List<IBBATEntity> getChildren();

	void save();

	void delete();

	void update();

	IBBATEntity getParent();
	
}
