package in.BBAT.abstrakt.presenter.run.model;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

import org.eclipse.core.runtime.Path;
import org.eclipse.swt.graphics.Image;

import in.BBAT.abstrakt.gui.BBATImageManager;
import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.abstrakt.gui.model.IGUITreeNode;
import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.abstrakt.presenter.device.model.DeviceDetails;
import in.BBAT.data.model.Entities.AbstractEntity;
import in.BBAT.data.model.Entities.TestDeviceEntity;
import in.BBAT.data.model.Entities.TestDeviceRunEntity;
import in.BBAT.data.model.Entities.TestRunEntity;
import in.BBAT.data.model.Entities.TestRunInfoEntity;
import in.bbat.configuration.BBATProperties;

public class TestDeviceRunModel extends AbstractTreeModel {

	protected TestDeviceRunModel(AbstractTreeModel parentNode,AbstractEntity entity) {
		super(parentNode, entity);
	}

	public TestDeviceRunModel(TestDeviceRunEntity childEntties) {
		super(null,childEntties);
	}

	public TestDeviceRunModel(TestRunModel runModel,AndroidDevice device) {
		super(runModel,new TestDeviceRunEntity((TestRunEntity) runModel.getEntity(),device.getDeviceEntity()));
	}

	@Override
	public void setParent(IGUITreeNode parent) {
		super.setParent(parent);
		((TestDeviceRunEntity)getEntity()).setTestRun((TestRunEntity) ((TestRunModel)parent).getEntity());
	}

	@Override
	public String getLabel() {
		return null;
	}

	public void exportDeviceDetails(String path) throws IOException{
		FileWriter devicefr = new FileWriter(path+Path.SEPARATOR+"Device_details.txt");
		TestDeviceEntity device = ((TestDeviceRunEntity)getEntity()).getDevice();
		StringBuffer buffer = new StringBuffer(DeviceDetails.BUILD_BOARD);
		buffer.append(" = ");
		buffer.append(device.getBuild_board());
		buffer.append(" \n ");

		buffer.append(DeviceDetails.BUILD_BRAND);
		buffer.append(" = ");
		buffer.append(device.getBuild_brand());
		buffer.append(" \n ");

		buffer.append(DeviceDetails.BUILD_CPU_ABI);
		buffer.append(" = ");
		buffer.append(device.getBuild_cpu_abi());
		buffer.append(" \n ");

		buffer.append(DeviceDetails.BUILD_CPU_ABI2);
		buffer.append(" = ");
		buffer.append(device.getBuild_cpu_abi2());
		buffer.append(" \n ");

		buffer.append(DeviceDetails.BUILD_DEVICE);
		buffer.append(" = ");
		buffer.append(device.getBuild_device());
		buffer.append(" \n ");

		buffer.append(DeviceDetails.BUILD_DISPLAY);
		buffer.append(" = ");
		buffer.append(device.getBuild_display());
		buffer.append(" \n ");

		buffer.append(DeviceDetails.BUILD_FINGERPRINT);
		buffer.append(" = ");
		buffer.append(device.getBuild_fingerprint());
		buffer.append(" \n ");

		buffer.append(DeviceDetails.BUILD_HARDWARE);
		buffer.append(" = ");
		buffer.append(device.getBuild_hardware());
		buffer.append(" \n ");

		buffer.append(DeviceDetails.BUILD_HOST);
		buffer.append(" = ");
		buffer.append(device.getBuild_host());
		buffer.append(" \n ");

		buffer.append(DeviceDetails.BUILD_ID);
		buffer.append(" = ");
		buffer.append(device.getBuild_id());
		buffer.append(" \n ");

		buffer.append(DeviceDetails.BUILD_MANUFACTURER);
		buffer.append(" = ");
		buffer.append(device.getBuild_manufacturer());
		buffer.append(" \n ");

		buffer.append(DeviceDetails.BUILD_MODEL);
		buffer.append(" = ");
		buffer.append(device.getBuild_model());
		buffer.append(" \n ");

		buffer.append(DeviceDetails.BUILD_PRODUCT);
		buffer.append(" = ");
		buffer.append(device.getBuild_product());
		buffer.append(" \n ");

		buffer.append(DeviceDetails.BUILD_RADIO);
		buffer.append(" = ");
		buffer.append(device.getBuild_radio());
		buffer.append(" \n ");

		buffer.append(DeviceDetails.BUILD_TAGS);
		buffer.append(" = ");
		buffer.append(device.getBuild_tags());
		buffer.append(" \n ");

		buffer.append(DeviceDetails.BUILD_TYPE);
		buffer.append(" = ");
		buffer.append(device.getBuild_type());
		buffer.append(" \n ");

		buffer.append(DeviceDetails.BUILD_USER);
		buffer.append(" = ");
		buffer.append(device.getBuild_user());
		buffer.append(" \n ");

		buffer.append(DeviceDetails.VERSION_CODENAME);
		buffer.append(" = ");
		buffer.append(device.getVersion_codename());
		buffer.append(" \n ");

		buffer.append(DeviceDetails.VERSION_INCREMENTAL);
		buffer.append(" = ");
		buffer.append(device.getVersion_incremental(	));
		buffer.append(" \n ");

		buffer.append(DeviceDetails.VERSION_RELEASE);
		buffer.append(" = ");
		buffer.append(device.getVersion_release());
		buffer.append(" \n ");

		buffer.append(DeviceDetails.VERSION_SDK);
		buffer.append(" = ");
		buffer.append(device.getVersion_sdk());
		buffer.append(" \n ");

		devicefr.write(buffer.toString());
		devicefr.close();


	}

	public Image getImage() {

		if(getStatus().equalsIgnoreCase(TestStatus.EXECUTING.getStatus()))
			return BBATImageManager.getInstance().getImage(BBATImageManager.EXECUTING);

		if(getStatus().equalsIgnoreCase(TestStatus.EXECUTED.getStatus()))
			return BBATImageManager.getInstance().getImage(BBATImageManager.PASS);

		if(getStatus().equals(TestStatus.ERROR.getStatus()))
			return BBATImageManager.getInstance().getImage(BBATImageManager.ERROR);

		return null;
	}

	protected IGUITreeNode produceParent(AbstractEntity childEntties) {
		return new TestRunModel((TestRunEntity) childEntties);
	}

	@Override
	protected AbstractTreeModel getChild(AbstractEntity childEntity)
			throws Exception {
		return new TestRunInstanceModel(this,(TestRunInfoEntity) childEntity);
	}

	public void setStatus(TestStatus status){
		((TestDeviceRunEntity)getEntity()).setStatus(status.getStatus());
	}

	public String getStatus(){
		return ((TestDeviceRunEntity)getEntity()).getStatus();
	}

	public void setStartTime(long timeInMilis){
		((TestDeviceRunEntity)getEntity()).setStartTime(new Timestamp(timeInMilis));
	}

	public void setEndTime(long timeInMilis){
		((TestDeviceRunEntity)getEntity()).setEndTime(new Timestamp(timeInMilis));
	}

	public Timestamp getStartTime(){
		return ((TestDeviceRunEntity)getEntity()).getStartTime();
	} 

	public Timestamp getEndTime(){
		return ((TestDeviceRunEntity)getEntity()).getEndTime();
	} 

	/**
	 *  be careful in changing this method. This is used to create and fetch screshots directory structure.
	 *  any change to this method will effect the screenshot directory.
	 * @return
	 */
	public String getDeviceName(){
		return ((TestDeviceRunEntity)getEntity()).getDevice().getDeviceId();
	}

	public String getDeviceLabel(){
		return ((TestDeviceRunEntity)getEntity()).getDevice().getBuild_model()+"-"+((TestDeviceRunEntity)getEntity()).getDevice().getDeviceId();
	}

	public long getTimeTaken(){
		if(getEndTime()== null){
			return 0;
		}
		return getEndTime().getTime()-getStartTime().getTime();
	}

	public String	convertTimeToHH_MM_SS(long miliseconds){
		long second = (miliseconds / 1000) % 60;
		long minute = (miliseconds / (1000 * 60)) % 60;
		long hour = (miliseconds / (1000 * 60 * 60)) % 24;
		return String.format("%02d:%02d:%02d", hour, minute, second);
	}

	public String getScreenShotDir()
	{
		return ((TestRunModel)getParent()).getScreenShotDir() + Path.SEPARATOR +getDeviceName();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return getDeviceName();
	}
}
