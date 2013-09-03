package in.BBAT.testRunner.runner;

import java.io.File;

public class UiAutoTestCaseJar {


	private String jarPath;
	private File jarFile;

	public UiAutoTestCaseJar(String srcFolderPath){
		initializeBuildEnvironment();
		createJar();
	}

	private void createJar() {
		// TODO Auto-generated method stub

	}

	private void initializeBuildEnvironment() {
		// TODO Auto-generated method stub

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
}
