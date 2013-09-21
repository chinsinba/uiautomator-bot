package in.BBAT.testRunner.runner;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.ant.core.AntRunner;
import org.eclipse.core.runtime.CoreException;

import com.google.common.io.Files;

public class UiAutoTestCaseJar {

	private String jarPath;
	private File jarFile;
	private static final String jarName = "BBAT";

	private static final String tempFolderPath ="/home/syed/Documents/test";

	public UiAutoTestCaseJar(String srcFolderPath){
		initializeBuildEnvironment(srcFolderPath);
	}

	public UiAutoTestCaseJar(List<String> testScriptPaths){
		initializeBuildEnvironment(testScriptPaths);
	}

	private void initializeBuildEnvironment(List<String> testScriptPaths) {

		File temp = new File(tempFolderPath+"/src");
		try {
			for (String testScriptPath : testScriptPaths) {
				Files.copy(new File(testScriptPath), temp);	
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			Runtime.getRuntime().exec("/home/syed/Documents/Android_SDK_21/sdk/tools/android create uitest-project -n "+jarName+" -t 6 -p "+tempFolderPath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		createJar(tempFolderPath);
		setJarFile(new File(tempFolderPath+"/"+jarName+".jar"));

	}

	private void createJar(String tempfolderpath) {
		AntRunner runner = new AntRunner();
		runner.setBuildFileLocation(tempfolderpath+"/build.xml");
		try {
			runner.run();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	private void initializeBuildEnvironment(String srcFolderPath) {
		File temp = new File(tempFolderPath+"/src");
		try {
			FileUtils.copyFolder(new File(srcFolderPath), temp);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			Runtime.getRuntime().exec("/home/syed/Documents/Android_SDK_21/sdk/tools/android create uitest-project -n "+jarName+" -t 6 -p "+tempFolderPath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		createJar(tempFolderPath);
		setJarFile(new File(tempFolderPath+"/"+jarName+".jar"));
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

	public static void main(String[] args) {
		new UiAutoTestCaseJar("/home/syed/Documents/trrwree");
	}
}
