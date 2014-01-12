package in.BBAT.abstrakt.presenter.run.model;

import java.sql.Timestamp;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.data.model.Entities.AbstractEntity;
import in.BBAT.data.model.Entities.TestDeviceRunEntity;
import in.BBAT.data.model.Entities.TestRunEntity;
import in.bbat.configuration.BBATProperties;

import org.eclipse.core.runtime.Path;
import org.eclipse.swt.graphics.Image;

/**
 * 
 * @author syed Mehtab
 *
 */
public class TestRunModel extends AbstractTreeModel {

	protected TestRunModel(TestRunEntity entity) {
		super(null,entity);
	}

	public TestRunModel() {
		super(null,new TestRunEntity());
	}

	@Override
	public String getLabel() {
		return getName();
	}

	@Override
	public String getName() {
		return "RUN_"+getEntity().getId();
	}
	
	@Override
	public Image getImage() {
		return null;
	}

	@Override
	protected AbstractTreeModel getChild(AbstractEntity childEntity) {
		return new TestDeviceRunModel(this,(TestDeviceRunEntity) childEntity);
	}
	public Timestamp getStartTime() {
		return ((TestRunEntity)getEntity()).getStartTime();
	}

	public void setStartTime(Timestamp startTime) {
		((TestRunEntity)getEntity()).setStartTime(startTime);
	}

	public Timestamp getEndtiTime() {
		return ((TestRunEntity)getEntity()).getEndtiTime();
	}

	public void setEndTime(Timestamp endtiTime) {
		((TestRunEntity)getEntity()).setEndtiTime(endtiTime);
	}
	
	public String getScreenShotDir()
	{
		return BBATProperties.getInstance().getScreenShotDirectory()+Path.SEPARATOR+getName();
	}

}
