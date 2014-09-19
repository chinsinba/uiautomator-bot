package in.BBAT.TestRunner.runner;

import in.bbat.configuration.BBATProperties;
import in.bbat.logger.BBATLogger;
import in.bbat.utility.FileUtils;
import in.bbat.utility.StreamGobbler;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.ant.core.AntRunner;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;

import com.android.SdkConstants;

public class UiAutoTestCaseJar {

	private static final Logger LOG = BBATLogger.getLogger(UiAutoTestCaseJar.class.getName());
	private String jarPath;
	private File jarFile;
	private static final String JAR_NAME = "BBAT";
	private static final String SRC = SdkConstants.FD_SOURCES;
	private static final String BIN = SdkConstants.FD_OUTPUT;
	private static final String BUILD_FILE = SdkConstants.FN_BUILD_XML;


	public static final String TEMP_FOLDER_PATH =System.getProperty("user.dir")+Path.SEPARATOR+"temp" ;
	private static final String ANDROID_SDK_TOOLS = BBATProperties.getInstance().getAndroid_SdkPath()+Path.SEPARATOR+SdkConstants.OS_SDK_TOOLS_FOLDER;
	//	private static final String CREATE_UI_PROJECT_COMMAND = "/"+SdkConstants.androidCmdName()+" create uitest-project -n "+JAR_NAME+" -t android-17 -p "+TEMP_FOLDER_PATH;

	public final static String UIAUTOMATOR_JAR =JAR_NAME+".jar";
	public final static String UIAUTOMATOR_JAR_PATH = "/data/local/tmp/"+UIAUTOMATOR_JAR;
	private String targetId ;

	public UiAutoTestCaseJar(List<String> testScriptPaths, String targetId) throws BuildJarException{
		this.targetId = targetId;
		initializeBuildEnvironment(testScriptPaths);
	}

	private void initializeBuildEnvironment(List<String> testScriptPaths) throws BuildJarException {

		clearFolder(new File(TEMP_FOLDER_PATH));

		File temp = createTempDir();

		createPackandCopyTestCases(testScriptPaths, temp);

		createUiProject();

		buildBBATJar(TEMP_FOLDER_PATH);

		setJarFile(new File(TEMP_FOLDER_PATH+Path.SEPARATOR+BIN+Path.SEPARATOR+UIAUTOMATOR_JAR));

	}

	private File createTempDir() {
		File temp = new File(TEMP_FOLDER_PATH+Path.SEPARATOR+SRC);
		temp.mkdirs();
		return temp;
	}

	private void createUiProject() throws BuildJarException {
		try {
			Process p = Runtime.getRuntime().exec(ANDROID_SDK_TOOLS+getCreateUiProjCommand());
			StreamGobbler errGobbler = new StreamGobbler(p.getErrorStream(),"error");
			StreamGobbler opGobbler = new StreamGobbler(p.getInputStream(),"output");
			errGobbler.start();
			opGobbler.start();
			p.waitFor();
		} catch (IOException e) {
			LOG.error(e);
			throw new BuildJarException("Failled to create UI automator project");
		} catch (Exception e) {
			LOG.error(e);
			throw new BuildJarException("Failled to create UI automator project");
		}
	}

	private void createPackandCopyTestCases(List<String> testScriptPaths,
			File temp) throws BuildJarException {
		try {
			for (String testScriptPath : testScriptPaths) {
				Path path =new Path(testScriptPath);
				String[] seg = path.segments();
				int len =seg.length;

				File createPack = new File(temp.getAbsolutePath()+Path.SEPARATOR+seg[len-3]+Path.SEPARATOR+seg[len-2]);
				createPack.mkdirs();

				FileUtils.copyFolder(new File(testScriptPath), new File(createPack.getAbsolutePath()+Path.SEPARATOR+path.lastSegment()));	
			}

		} catch (IOException e1) {
			LOG.error(e1);
			throw new BuildJarException("Build failled: Failed to create initial setup");

		}
	}

	private void clearFolder(File f) {
		if (f.isDirectory()) {
			for (File c : f.listFiles())
				clearFolder(c);
		}
		if (!f.delete()){
			LOG.error("failled to delete file "+ f.getAbsolutePath());
		}
	}

	private void buildBBATJar(String tempfolderpath) throws BuildJarException {

		AntRunner runner = new AntRunner();
		runner.setBuildFileLocation(tempfolderpath+Path.SEPARATOR+BUILD_FILE);
		String[] target = {"build"};
		runner.setExecutionTargets(target);
		try {

			runner.run();
		} catch (CoreException e) {
			LOG.error(e);
			throw new BuildJarException("Build failled : target id is not matching");
		}
	}


	public String getJarPath() {
		return jarPath;
	}

	public void setJarPath(String jarPath) {
		this.jarPath = jarPath;
	}

	public File getJarFile() {
		return jarFile;
	}

	public void setJarFile(File jarFile) {
		this.jarFile = jarFile;
		setJarPath(jarFile.getAbsolutePath());
	}

	public String getCreateUiProjCommand()
	{
		return "/"+SdkConstants.androidCmdName()+" create uitest-project -n "+JAR_NAME+" -t "+targetId+"  -p "+TEMP_FOLDER_PATH;
	}

}
