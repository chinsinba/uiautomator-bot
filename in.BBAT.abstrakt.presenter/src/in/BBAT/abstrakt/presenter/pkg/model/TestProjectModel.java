package in.BBAT.abstrakt.presenter.pkg.model;

import in.BBAT.data.model.Entities.AbstractEntity;
import in.BBAT.data.model.Entities.TestProjectEntity;
import in.BBAT.data.model.Entities.TestSuiteEntity;
import in.BBAT.dataMine.manager.JaxbExportImport;
import in.bbat.abstrakt.gui.BBATImageManager;
import in.bbat.utility.FileUtils;
import in.bbat.utility.ZipFiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.internal.wizards.datatransfer.ZipFileExporter;
import org.eclipse.ui.wizards.datatransfer.ZipFileStructureProvider;

/**
 * 
 * @author Syed Mehtab
 *
 */
public class TestProjectModel extends AbstractProjectTree {

	private IFolder folder ;
	protected TestProjectModel(TestProjectEntity entity) throws Exception {
		super(null,entity, entity.getName(),false);
	}

	protected TestProjectModel(TestProjectEntity entity,boolean createResource) throws Exception {
		super(null,entity, entity.getName(),createResource);
	}

	public TestProjectModel(String projectName) throws Exception {
		this(new TestProjectEntity(projectName),true);
	}

	@Override
	public String getLabel() {
		return getName();
	}

	public String getName() {
		return ((TestProjectEntity)getEntity()).getName();
	}

	public Image getImage() {
		return BBATImageManager.getInstance().getImage(BBATImageManager.PROJECT_GIF_16);
	}

	@Override
	protected AbstractProjectTree getChild(AbstractEntity childEntity) throws Exception {
		return new TestSuiteModel(this,(TestSuiteEntity) childEntity);
	}

	@Override
	public void setName(String name) {
		((TestProjectEntity)getEntity()).setName(name);
	}

	@Override
	public void createResource() {
		File f = new File(getPath());
		f.mkdir();
	}

	public void setDescription(String description) {
		((TestProjectEntity)getEntity()).setDescription(description);		
	}

	public String getDescription()
	{
		return ((TestProjectEntity)getEntity()).getDescription();
	}

	@Override
	public void linkToProject() {
		folder =BBATProjectUtil.getInstance().linkPackage(getResourcePath());
	}

	@Override
	public void deLinkFromProject() {
		BBATProjectUtil.getInstance().deletePack(folder);
	}

	public void export(String dirPath) throws Exception{
		//create temporary file
		String tempFilePath ="temp"+System.currentTimeMillis()+Path.SEPARATOR+"project";
		File tempFile = new File(tempFilePath);
		tempFile.mkdirs();
		
		//export xml
		JaxbExportImport exp = new JaxbExportImport(tempFile.getAbsolutePath(), (TestProjectEntity) getEntity());
		exp.export();
		
		//expport the project and scripts
		File proj = new File(tempFile.getAbsolutePath()+Path.SEPARATOR+getName());
		proj.mkdirs();
		FileUtils.copyFolder(new File(getResourcePath()), proj);
		
		//zip exported artifacts
		ZipFiles.zipDirectory(tempFile, dirPath+Path.SEPARATOR+getName()+".dat");
		
		//delete temporary file
		FileUtils.delete(tempFile);

	}
}
