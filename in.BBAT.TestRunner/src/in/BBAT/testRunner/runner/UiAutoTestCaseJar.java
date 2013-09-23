package in.BBAT.testRunner.runner;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.ant.core.AntRunner;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;

public class UiAutoTestCaseJar {

	private String jarPath;
	private File jarFile;
	private static final String jarName = "BBAT";

	private static final String TEMP_FOLDER_PATH ="/home/syed/Documents/test";
	private static final String ANDROID_SDK_TOOLS = "/home/syed/Documents/Android_SDK_21/sdk/tools/";

	public UiAutoTestCaseJar(List<String> testScriptPaths){
		initializeBuildEnvironment(testScriptPaths);
	}

	private void initializeBuildEnvironment(List<String> testScriptPaths) {

		File temp = new File(TEMP_FOLDER_PATH+"/src");
		temp.mkdirs();
		try {
			for (String testScriptPath : testScriptPaths) {
				Path path = new Path(testScriptPath);
				FileUtils.copyFolder(new File(testScriptPath), new File(temp.getAbsolutePath()+"/"+path.lastSegment()));	
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			Runtime.getRuntime().exec(ANDROID_SDK_TOOLS+"/android create uitest-project -n "+jarName+" -t 6 -p "+TEMP_FOLDER_PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}

		createJar(TEMP_FOLDER_PATH);
		setJarFile(new File(TEMP_FOLDER_PATH+"/bin/"+jarName+".jar"));

	}

	private void createJar(String tempfolderpath) {
		AntRunner runner = new AntRunner();
		runner.setBuildFileLocation(tempfolderpath+"/build.xml");
		String[] target = {"build"};
		runner.setExecutionTargets(target);
		try {
			runner.run();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	/*private void initializeBuildEnvironment(String srcFolderPath) {
		File temp = new File(TEMP_FOLDER_PATH+"/src");
		try {
			FileUtils.copyFolder(new File(srcFolderPath), temp);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			Runtime.getRuntime().exec("/home/syed/Documents/Android_SDK_21/sdk/tools/android create uitest-project -n "+jarName+" -t 6 -p "+TEMP_FOLDER_PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}

		createJar(TEMP_FOLDER_PATH);
		setJarFile(new File(TEMP_FOLDER_PATH+"/"+jarName+".jar"));
	}
*/
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
