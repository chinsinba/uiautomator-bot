package in.BBAT.testRunner.runner;

import java.io.File;
import java.io.IOException;

import org.eclipse.ant.core.AntRunner;
import org.eclipse.core.runtime.CoreException;

public class UiAutoTestCaseJar {

	private String jarPath;
	private File jarFile;

	public UiAutoTestCaseJar(String srcFolderPath){
		initializeBuildEnvironment(srcFolderPath);
		createJar();
	}

	private void createJar() {
		//run ant build command
		AntRunner runner = new AntRunner();
//		runner.setAntHome("/home/syed/Documents/runtime-BBAT.product/trrwree");
		runner.setBuildFileLocation("/home/syed/Documents/runtime-BBAT.product/trrwree/build.xml");
		try {
			runner.run();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	private void initializeBuildEnvironment(String srcFolderPath) {
		// <android-sdk>/tools/android create uitest-project -n <name> -t 1 -p <path>

		try {
			Runtime.getRuntime().exec("/home/syed/Documents/Android_SDK_21/sdk/tools/android create uitest-project -n Tedfdmf -t 6 -p "+srcFolderPath);
		} catch (IOException e) {
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
	}

	public static void main(String[] args) {
		new UiAutoTestCaseJar("/home/syed/Documents/runtime-BBAT.product/trrwree/src");
	}
}
