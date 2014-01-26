package in.BBAT.abstrakt.presenter.pkg.model;

import in.BBAT.data.model.Entities.TestCaseEntity;
import in.BBAT.data.model.Entities.TestProjectEntity;
import in.BBAT.data.model.Entities.TestSuiteEntity;
import in.BBAT.dataMine.manager.JaxbExportImport;
import in.BBAT.dataMine.manager.ProjectMineManager;
import in.bbat.configuration.BBATProperties;
import in.bbat.utility.FileUtils;
import in.bbat.utility.ZipFiles;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.core.runtime.Path;
import org.eclipse.ui.wizards.datatransfer.ZipFileStructureProvider;


public class TestProjectManager {

	private static TestProjectManager instance;
	private List<TestProjectModel> models;

	private TestProjectManager()
	{

	}

	public static TestProjectManager getInstance(){
		if(instance == null)
		{
			instance= new TestProjectManager();
		}
		return instance;
	}

	public List<TestProjectModel> getTestProjects() throws Exception{
		if(models!=null){
			return models;
		}
		models = new ArrayList<TestProjectModel>();
		for(TestProjectEntity entity: ProjectMineManager.getAllTesPackages()){
			TestProjectModel model = new TestProjectModel(entity);
			models.add(model);
		}
		return models;
	}

	public void importProject(String testProjectZipPath) throws Exception{

		String tempFilePath ="temp"+System.currentTimeMillis();
		File tempFile = new File(tempFilePath);
		tempFile.mkdirs();

		ZipFiles.unZipIt(testProjectZipPath, tempFile.getAbsolutePath());

		File[] listFiles = tempFile.listFiles();

		String bbatFilePath ="";
		for(File file : listFiles){
			if(file.getName().endsWith(".bbat")){
				bbatFilePath = file.getAbsolutePath();
			}
		}

		if(bbatFilePath.isEmpty())
		{
			throw new Exception("This is not a valid project");
		}
		JaxbExportImport jaxbImport = new JaxbExportImport();
		TestProjectEntity projEntity = jaxbImport.imp0rt(bbatFilePath);
		for(TestProjectModel model : getTestProjects()){
			if(model.getName().equals(projEntity.getName())){
				throw new Exception("Project with name ["+projEntity.getName()+"] already exists.");
			}
		}

		TestProjectModel proj = TestProjectModel.create(projEntity.getName(), projEntity.getDescription(),projEntity.getApiLevel());
		for(TestSuiteEntity suiteEntity : projEntity.getTestSuites()){
			TestSuiteModel suiteModel = TestSuiteModel.create(proj, suiteEntity.getName(), suiteEntity.getDescription());
			for(TestCaseEntity caseEntity: suiteEntity.getTestCases()){
				TestCaseModel.create(suiteModel, caseEntity.getName(), caseEntity.getDescription());
			}
		}
		FileUtils.delete(new File(bbatFilePath));
		FileUtils.copyFolder(new File(tempFile.getAbsolutePath()),new File(BBATProperties.getInstance().getWkspc_UiAutomator()));

		FileUtils.delete(tempFile);
	}

}
