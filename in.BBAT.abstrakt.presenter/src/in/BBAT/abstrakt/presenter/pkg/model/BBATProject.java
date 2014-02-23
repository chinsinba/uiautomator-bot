package in.BBAT.abstrakt.presenter.pkg.model;

import in.bbat.configuration.BBATProperties;
import in.bbat.logger.BBATLogger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaModelMarker;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.LibraryLocation;

public class BBATProject {

	private IProject project;
	private static final Logger LOG = BBATLogger.getLogger(BBATProject.class.getName());
	int apiLevel ;
	public BBATProject(String projName, int apiLevel){
		this.apiLevel = apiLevel;
		initializeBBATProject(projName,apiLevel);
	}

	private IProject project(String projName)  throws Exception{

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		project = root.getProject(projName);
		try {
			if(!project.exists()){
				project.create(null);
				project.open(null);
				IProjectDescription description = project.getDescription();
				description.setNatureIds(new String[] { JavaCore.NATURE_ID });
				project.setDescription(description, null);

				IJavaProject javaProject = JavaCore.create(project);

				IFolder binFolder = project.getFolder("bin");
				javaProject.setOutputLocation(binFolder.getFullPath(), null);

				List<IClasspathEntry> entries = new ArrayList<IClasspathEntry>();
				entries.add(JavaCore.newLibraryEntry(new Path(BBATProperties.getInstance().getAndroid_AndroidJarPath(apiLevel)), null, null));
				entries.add(JavaCore.newLibraryEntry(new Path(BBATProperties.getInstance().getAndroid_UiAutomatorPath(apiLevel)), null, null));
				entries.add(JavaCore.newSourceEntry(project.getFullPath()));
				IVMInstall vmInstall = JavaRuntime.getDefaultVMInstall();
				LibraryLocation[] locations = JavaRuntime.getLibraryLocations(vmInstall);
				for (LibraryLocation element : locations) {
					entries.add(JavaCore.newLibraryEntry(element.getSystemLibraryPath(), null, null));
				}
				//add libs to project class pathproject
				javaProject.setRawClasspath(entries.toArray(new IClasspathEntry[entries.size()]), null);
			}
		} catch (CoreException e1) {
			LOG.error(e1);
		}

		return project;

	}


	private void initializeBBATProject(String projName, int apiLevel) {

		IProject pro =null;
		try {
			pro = 	project(projName);
			pro.refreshLocal(IResource.DEPTH_INFINITE,new NullProgressMonitor());
		} catch (Exception e1) {
			LOG.error(e1);
		}

	}

	/**
	 * @param testScriptPath
	 * @return
	 */
	public IFile linkScript(String testScriptPath){
		File scripFile = new File(testScriptPath);
		IPath locationPath = new Path(scripFile.getAbsolutePath());
		String[] seg = locationPath.segments();
		int len =seg.length;


		IFile scriptIfile = project.getFile(Path.SEPARATOR+seg[len-3]+Path.SEPARATOR+seg[len-2]+Path.SEPARATOR+seg[len-1]);
		try {
			if(!scriptIfile.isLinked())
				scriptIfile.createLink(locationPath, IResource.NONE, null);
		} catch (CoreException e) {
			return scriptIfile;
		}
		try {
			project.refreshLocal(IResource.DEPTH_INFINITE,new NullProgressMonitor());
		} catch (CoreException e) {
			LOG.error(e);
		}



		/*	for (IPackageFragment mypackage : ((IJavaProject)project).getPackageFragments()) {
			if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
				System.out.println("Package " + mypackage.getElementName());
				 for (ICompilationUnit unit : mypackage.getCompilationUnits()) {
				     unit.

				    }

			}

		}*/
		return scriptIfile;
	}

	public boolean hasErrors(IFile scriptIfile) {
		boolean error = false ;
		try {
			error = IMarker.SEVERITY_ERROR == scriptIfile.findMaxProblemSeverity(IJavaModelMarker.JAVA_MODEL_PROBLEM_MARKER, false, IResource.DEPTH_INFINITE);
		} catch (CoreException e) {
			LOG.error(e);
			return true;
		}
		return error;
	}

	public IFolder linkSuite(String testScriptPath){
		File scripFile = new File(testScriptPath);
		IPath locationPath = new Path(scripFile.getAbsolutePath());
		IFolder packFolder = null;
		String[] seg = locationPath.segments();
		int len =seg.length;
		packFolder = project.getFolder(Path.SEPARATOR+seg[len-2]+Path.SEPARATOR+seg[len-1]);	
		if(!packFolder.isLinked()){
			try {
				packFolder.createLink(locationPath, IResource.DEPTH_INFINITE, null);
			} catch (CoreException e) {
				LOG.error(e);
			}
		}
		try {
			project.refreshLocal(IResource.DEPTH_INFINITE,new NullProgressMonitor());
		} catch (CoreException e) {
			LOG.error(e);
		}

		return packFolder;
	}

	public IFolder linkPackage(String testScriptPath){
		File scripFile = new File(testScriptPath);
		IPath locationPath = new Path(scripFile.getAbsolutePath());
		IFolder packFolder = null;
		String[] seg = locationPath.segments();
		int len =seg.length;
		packFolder = project.getFolder(Path.SEPARATOR+seg[len-1]);	
		if(!packFolder.isLinked()){
			try {
				packFolder.createLink(locationPath, IResource.DEPTH_INFINITE, null);
			} catch (CoreException e) {
				LOG.error(e);
			}
		}

		try {
			project.refreshLocal(IResource.DEPTH_INFINITE,new NullProgressMonitor());
		} catch (CoreException e) {
			LOG.error(e);
		}

		return packFolder;
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

	/**
	 * @param folder
	 */
	public void deletePack (IFolder folder){
		try {
			folder.delete(true,new NullProgressMonitor());
		} catch (CoreException e) {
			return;
		}
	}


	/*public IProject getProject() {
		return project;
	}

	public void setProject(IProject project) {
		this.project = project;
	}*/


	public void deleteProject(){
		try {
			if(project.exists()){
				project.delete(true, new NullProgressMonitor());
			}
		}catch(Exception e){
			LOG.error(e);
		}
	}

	public static void  deleteAllProjects(){
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] projects = root.getProjects();
		for(IProject pro : projects){
			try {
				pro.delete(true, new NullProgressMonitor());
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}


	public void refresh(){
		try {
			project.refreshLocal(IResource.DEPTH_INFINITE,new NullProgressMonitor());
		} catch (CoreException e) {
			LOG.error(e);
		}

	}
	public void delete() {


	}

}
