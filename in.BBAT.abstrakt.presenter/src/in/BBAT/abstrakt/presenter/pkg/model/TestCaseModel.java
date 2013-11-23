package in.BBAT.abstrakt.presenter.pkg.model;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import in.BBAT.data.model.Entities.TestCaseEntity;
import in.BBAT.data.model.Entities.TestSuiteEntity;
import in.bbat.abstrakt.gui.BBATImageManager;
import in.bbat.logger.BBATLogger;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

/**
 * 
 * @author Syed Mehtab
 *
 */
public class TestCaseModel extends AbstractProjectTree{

	public final static String JAVA = ".java";

	private static final Logger LOG = BBATLogger.getLogger(TestCaseModel.class.getName());
	public IFile testCaseFile;


	private String testScriptPath;

	protected TestCaseModel(TestSuiteModel parent ,TestCaseEntity entity) throws Exception {
		super(parent,entity,entity.getName()+JAVA,false);

	}

	protected TestCaseModel(TestSuiteModel parent ,TestCaseEntity entity,boolean createResource) throws Exception {
		super(parent,entity,entity.getName()+JAVA,createResource);
	}

	public TestCaseModel(TestSuiteModel parent,String testCaseName) throws Exception{
		this(parent,new TestCaseEntity((TestSuiteEntity)parent.getEntity(),testCaseName),true);

	}

	@Override
	public String getLabel() {
		return getName();
	}

	@Override
	public String getName() {
		return ((TestCaseEntity)getEntity()).getName();
	}
	@Override
	public Image getImage() {
		return BBATImageManager.getInstance().getImage(BBATImageManager.TESTCASE_GIF_16);
	}

	@Override
	public void createResource() throws IOException {
		File newFile = new File(getPath());
		newFile.createNewFile();
	}


	@Override
	public String getDescription() {
		return ((TestCaseEntity)getEntity()).getDescription();
	}

	@Override
	public void setDescription(String description) {
		((TestCaseEntity)getEntity()).setDescription(description);
	}

	@Override
	public void linkToProject() {
		testCaseFile = BBATProjectUtil.getInstance().linkScript(getTestScriptPath());
	}

	@Override
	public void deLinkFromProject() {
		BBATProjectUtil.getInstance().deleteLink(getIFile());
	}

	public IFile getIFile(){
		return testCaseFile;
	}

	public String getTestScriptPath() {
		return getResourcePath();
	}

	public String getCompleteScriptName(){
		return getParent().getParent().getName()+"."+getParent().getName()+"."+getName();
	}

	public void createContents() {

		Configuration cfgFtl =new Configuration();
		try {
			cfgFtl.setDirectoryForTemplateLoading(new File(
					"/home/syed/Documents/BlackAndro/BBAT/in.BBAT.abstrakt.presenter/lib/"));
			Template testCaseTemplate = cfgFtl.getTemplate("testcase.ftl");
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("package_name", getParent().getParent().getName()+"."+getParent().getName());
			data.put("testCase_name", getName());
			data.put("description", getDescription());
			Writer fileWriter = new FileWriter(new File(getTestScriptPath()));
			try {
				testCaseTemplate.process(data, fileWriter);
			} finally {
				fileWriter.close();
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (TemplateException e) {
			LOG.error(e);
		}
		//String packDecl = "package "+getParent().getParent().getName()+"."+getParent().getName()+";\n\n";
		//packDecl +="import com.android.uiautomator.core.*;\nimport com.android.uiautomator.testrunner.*;\n\n";
		//		packDecl+="public class "+getName()+" extends UiAutomatorTestCase {\n\n";
		//		packDecl+="/** "+getDescription()+"\n*/\n\tpublic void test"+getName()+"()throws UiObjectNotFoundException{\n}\n}";
		//		InputStream st = new ByteArrayInputStream(packDecl.getBytes());
		//		try {
		//			getIFile().setContents(st,0, null);
		//		} catch (CoreException e) {
		//			LOG.error(e);
		//		}

	}
	
	public void openEditor() throws Exception{
		IEditorInput input=null;
		input = new FileEditorInput(getIFile());
		try
		{
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(input, "org.eclipse.jdt.ui.CompilationUnitEditor");
		} catch (PartInitException e)
		{
			LOG.error(e);
			throw new Exception("Failled to open java editor");
		}
	}

}
