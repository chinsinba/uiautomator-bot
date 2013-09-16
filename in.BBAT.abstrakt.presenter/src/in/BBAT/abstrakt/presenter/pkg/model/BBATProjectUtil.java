package in.BBAT.abstrakt.presenter.pkg.model;


import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

public class BBATProjectUtil {
	
	private static BBATProjectUtil instance;

	private IProject project;

	private BBATProjectUtil(){
		initializeBBATProject();
	}

	public static BBATProjectUtil getInstance(){
		if(instance==null)
			instance = new BBATProjectUtil();
		return instance;
	}

	private void initializeBBATProject() {
		IWorkspace ws = ResourcesPlugin.getWorkspace();
		IProject project = ws.getRoot().getProject("BBAT");
		boolean created =false;
		if(!project.isOpen()){
			try {
				if(!project.exists()){
					project.create(new NullProgressMonitor());
					created =true;
				}
				project.open(new NullProgressMonitor());
			} catch (CoreException e1) {
				e1.printStackTrace();
			}
		}	

		/*if(created){
			createPydevProjXml(project);
		}*/
		try {
			project.refreshLocal(IResource.DEPTH_INFINITE,new NullProgressMonitor());
		} catch (CoreException e) {
			e.printStackTrace();
		}
		setProject(project);
	}

	/**
	 * @param testScriptPath
	 * @return
	 */
	public IFile createLink(String testScriptPath){
		File scripFile = new File(testScriptPath);
		IPath locationPath = new Path(scripFile.getAbsolutePath());
		IFile scriptIfile = getProject().getFile(locationPath.lastSegment());
		try {
			if(!scriptIfile.isLinked())
				scriptIfile.createLink(locationPath, IResource.NONE, null);
		} catch (CoreException e) {
			return scriptIfile;
		}
		return scriptIfile;
	}

	/**
	 * @param file
	 */
	public void deleteLink (IFile file){
		try {
			file.delete(true,new NullProgressMonitor());
		} catch (CoreException e) {
			return;
		}
	}


	public IProject getProject() {
		return project;
	}

	public void setProject(IProject project) {
		this.project = project;
	}
}
