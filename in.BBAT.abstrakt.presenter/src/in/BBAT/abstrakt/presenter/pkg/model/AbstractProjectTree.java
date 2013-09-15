package in.BBAT.abstrakt.presenter.pkg.model;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.data.model.Entities.AbstractEntity;
import in.BBAT.data.model.Entities.TestProjectEntity;

import java.io.File;
import java.io.FileNotFoundException;

import org.eclipse.core.runtime.Path;

public abstract class AbstractProjectTree extends AbstractTreeModel implements IResource {


	private String resourcePath;
	public final static String UI_AUTO_CASE_PATH ="/home/syed/Documents/BlackAndro/UserPack";

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
			this.resourcePath =parent.getPath()+Path.SEPARATOR+resourceName;
		else {
			this.resourcePath =UI_AUTO_CASE_PATH+Path.SEPARATOR+resourceName;
		}
		if(createResources){
			createFiles();
		}
	}

	private void createFiles() throws Exception {
		File f = new File(getPath());
		if(!f.exists())
			createResource();
	}

	@Override
	public String getPath() {
		return resourcePath;
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
}
