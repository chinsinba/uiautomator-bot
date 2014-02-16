package in.BBAT.abstrakt.presenter.pkg.model;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunModel;
import in.BBAT.data.model.Entities.AbstractEntity;
import in.BBAT.data.model.Entities.TestRunEntity;
import in.bbat.configuration.BBATProperties;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Path;

public abstract class AbstractProjectTree extends AbstractTreeModel implements IResource {


	private String resourcePath;
	//	public final static String UI_AUTO_CASE_PATH ="/home/syed/Documents/Macac";

	/**
	 * 
	 * @param entity
	 * @param resourceName
	 * @param createResources
	 * @throws Exception 
	 */
	protected AbstractProjectTree(AbstractProjectTree parent ,AbstractEntity entity,String resourceName,boolean createResources) throws Exception {
		super(parent,entity);
		if(parent!=null)
			this.setResourcePath(parent.getPath()+Path.SEPARATOR+resourceName);
		else {
			this.setResourcePath(BBATProperties.getInstance().getWkspc_UiAutomator()+Path.SEPARATOR+resourceName);
		}
		if(createResources){
			createFiles();
		}
		linkToProject();
	}

	private void createFiles() throws Exception {
		File f = new File(getPath());
		if(!f.exists())
			createResource();
	}

	@Override
	public String getPath() {
		return getResourcePath();
	}

	@Override
	protected AbstractTreeModel getChild(AbstractEntity childEntity) throws Exception{
		return null;
	}

	public void setDescription(String description) {
	}

	public String getDescription()
	{
		return "";
	}

	public void delete() throws Exception {
		super.delete();
		deLinkFromProject();
		deleteResource();
	}

	@Override
	public void deleteResource() throws Exception {
		File f = new File(getPath());
		deleteFolder(f);
	}

	private void deleteFolder(File f) throws FileNotFoundException{
		if (f.isDirectory()) {
			for (File c : f.listFiles())
				deleteFolder(c);
		}
		if (!f.delete())
			throw new FileNotFoundException("Failed to delete file: " + f);
	}

	@Override
	public void linkToProject() {

	}

	@Override
	public void deLinkFromProject() {

	}

	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	/**
	 * 
	 * @return java project
	 */
	public abstract BBATProject getProject();

	/**
	 * 
	 * @return associated testruns 
	 */
	public List<TestRunModel> getRefrencedTestRuns(){
		List<TestRunModel> runModels = new ArrayList<TestRunModel>();
		List<TestRunEntity> allRunsContainingTestSuite = getRefTestRunEntities();
		if(allRunsContainingTestSuite !=null){
			for (TestRunEntity testRunEntity : allRunsContainingTestSuite) {
				runModels.add(new TestRunModel(testRunEntity));
			}
		}

		return runModels;
	}

	public abstract List<TestRunEntity> getRefTestRunEntities();

	public void refresh(){
		getProject().refresh();		
	}
}
