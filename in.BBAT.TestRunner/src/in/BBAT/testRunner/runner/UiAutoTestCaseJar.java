package in.BBAT.testRunner.runner;

import in.bbat.configuration.ConfigXml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.eclipse.ant.core.AntRunner;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;

public class UiAutoTestCaseJar {

	private String jarPath;
	private File jarFile;
	private static final String JAR_NAME = "BBAT";
	private static final String SRC = "src";
	private static final String BIN = "bin";
	private static final String BUILD_FILE = "build.xml";
	

	private static final String TEMP_FOLDER_PATH ="/home/syed/Documents/test";
	private static final String ANDROID_SDK_TOOLS = ConfigXml.getInstance().getAndroid_SdkPath()+Path.SEPARATOR+"tools";
	private static final String CREATE_UI_PROJECT_COMMAND = "/android create uitest-project -n "+JAR_NAME+" -t 6 -p "+TEMP_FOLDER_PATH;

	
	public UiAutoTestCaseJar(List<String> testScriptPaths){
		initializeBuildEnvironment(testScriptPaths);
	}

	private void initializeBuildEnvironment(List<String> testScriptPaths) {

		File temp = new File(TEMP_FOLDER_PATH+Path.SEPARATOR+SRC);

		try {
			clearFolder(new File(TEMP_FOLDER_PATH));
		} 
		catch (FileNotFoundException e2)
		{
			e2.printStackTrace();
		}

		temp.mkdirs();
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
			e1.printStackTrace();
		}
		try {
			Process p = Runtime.getRuntime().exec(ANDROID_SDK_TOOLS+CREATE_UI_PROJECT_COMMAND);
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		createJar(TEMP_FOLDER_PATH);
		setJarFile(new File(TEMP_FOLDER_PATH+Path.SEPARATOR+BIN+Path.SEPARATOR+JAR_NAME+".jar"));

	}

	private void clearFolder(File f) throws FileNotFoundException{
		if (f.isDirectory()) {
			for (File c : f.listFiles())
				clearFolder(c);
		}
		if (!f.delete())
			throw new FileNotFoundException("Failed to delete file: " + f);
	}

	private void createJar(String tempfolderpath) {

		AntRunner runner = new AntRunner();
		runner.setBuildFileLocation(tempfolderpath+Path.SEPARATOR+BUILD_FILE);
		String[] target = {"build"};
		runner.setExecutionTargets(target);
		try {

			runner.run();
		} catch (CoreException e) {
			e.printStackTrace();
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

}
