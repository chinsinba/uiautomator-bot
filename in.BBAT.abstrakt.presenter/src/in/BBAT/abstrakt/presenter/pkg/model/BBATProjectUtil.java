package in.BBAT.abstrakt.presenter.pkg.model;


import in.bbat.configuration.BBATConfigXml;
import in.bbat.logger.BBATLogger;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.LibraryLocation;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;

public class BBATProjectUtil {

	private static BBATProjectUtil instance;

	private IProject project;
	private static final Logger LOG = BBATLogger.getLogger(BBATProjectUtil.class.getName());
	private final static String UIAUTO_PROJECT_NAME = "Macac";
	private BBATProjectUtil(){
		initializeBBATProject();
	}

	public static BBATProjectUtil getInstance(){
		if(instance==null)
			instance = new BBATProjectUtil();
		return instance;
	}

	private IProject project()  throws Exception{

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(UIAUTO_PROJECT_NAME);
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
				entries.add(JavaCore.newLibraryEntry(new Path(BBATConfigXml.getInstance().getAndroid_AndroidJarPath()), null, null));
				entries.add(JavaCore.newLibraryEntry(new Path(BBATConfigXml.getInstance().getAndroid_UiAutomatorPath()), null, null));
				entries.add(JavaCore.newSourceEntry(project.getFullPath()));
				IVMInstall vmInstall = JavaRuntime.getDefaultVMInstall();
				LibraryLocation[] locations = JavaRuntime.getLibraryLocations(vmInstall);
				for (LibraryLocation element : locations) {
					entries.add(JavaCore.newLibraryEntry(element.getSystemLibraryPath(), null, null));
				}
				//add libs to project class pathproject
				javaProject.setRawClasspath(entries.toArray(new IClasspathEntry[entries.size()]), null);
				/*try {
					FileUtils.copyFolder(project.getFile(".classpath").getRawLocation().toFile(), new File(ITempConstants.USERWKSPC+"/.classpath"));
					FileUtils.copyFolder(project.getFile(".project").getRawLocation().toFile(), new File(ITempConstants.USERWKSPC+"/.project"));
				} catch (IOException e) {
					LOG.error(e);
				}*/
			}
		} catch (CoreException e1) {
			e1.printStackTrace();
		}

		return project;

	}


	private void initializeBBATProject() {

		IProject pro =null;
		try {
			pro = 	project();
			pro.refreshLocal(IResource.DEPTH_INFINITE,new NullProgressMonitor());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/*ProjectRecord record = new ProjectRecord(new File(ITempConstants.USERWKSPC+"/.project"));

		try {
			createExistingProject(record, new NullProgressMonitor());
		} catch (InvocationTargetException e2) {
			e2.printStackTrace();
		}

		try {
			pro.refreshLocal(IResource.DEPTH_INFINITE,new NullProgressMonitor());
		} catch (CoreException e) {
			LOG.error(e);
		}*/
		setProject(pro);
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


		IFile scriptIfile = getProject().getFile(locationPath.SEPARATOR+seg[len-3]+locationPath.SEPARATOR+seg[len-2]+locationPath.SEPARATOR+seg[len-1]);
		try {
			if(!scriptIfile.isLinked())
				scriptIfile.createLink(locationPath, IResource.NONE, null);
		} catch (CoreException e) {
			return scriptIfile;
		}
		try {
			getProject().refreshLocal(IResource.DEPTH_INFINITE,new NullProgressMonitor());
		} catch (CoreException e) {
			LOG.error(e);
		}
		return scriptIfile;
	}

	public IFolder linkSuite(String testScriptPath){
		File scripFile = new File(testScriptPath);
		IPath locationPath = new Path(scripFile.getAbsolutePath());
		IFolder packFolder = null;
		String[] seg = locationPath.segments();
		int len =seg.length;
		packFolder = getProject().getFolder(locationPath.SEPARATOR+seg[len-2]+locationPath.SEPARATOR+seg[len-1]);	
		if(!packFolder.isLinked()){
			try {
				packFolder.createLink(locationPath, IResource.DEPTH_INFINITE, null);
			} catch (CoreException e) {
				LOG.error(e);
			}
		}
		try {
			getProject().refreshLocal(IResource.DEPTH_INFINITE,new NullProgressMonitor());
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
		packFolder = getProject().getFolder(locationPath.SEPARATOR+seg[len-1]);	
		if(!packFolder.isLinked()){
			try {
				packFolder.createLink(locationPath, IResource.DEPTH_INFINITE, null);
			} catch (CoreException e) {
				LOG.error(e);
			}
		}
		
		try {
			getProject().refreshLocal(IResource.DEPTH_INFINITE,new NullProgressMonitor());
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


	public IProject getProject() {
		return project;
	}

	public void setProject(IProject project) {
		this.project = project;
	}

	private boolean createExistingProject(final ProjectRecord record,IProgressMonitor monitor) throws InvocationTargetException {
		String projectName = record.getProjectName();
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		project = workspace.getRoot().getProject(projectName);
		if (record.description == null) {
			// error case
			record.description = workspace.newProjectDescription(projectName);
			IPath locationPath = new Path(record.projectSystemFile
					.getAbsolutePath());

			// If it is under the root use the default location
			if (Platform.getLocation().isPrefixOf(locationPath)) {
				record.description.setLocation(null);
			} else {
				record.description.setLocation(locationPath);
			}
		} else {
			record.description.setName(projectName);
		}

		if(!project.isOpen()){
			try {
				if(!project.exists()){
					project.create(record.description, new NullProgressMonitor());
				}
				project.open(IResource.BACKGROUND_REFRESH, new NullProgressMonitor());
			} catch (CoreException e1) {
				e1.printStackTrace();
			}
		}	
		setProject(project);

		return true;
	}

	/**
	 * Class declared public only for test suite.
	 * 
	 */
	public class ProjectRecord {
		File projectSystemFile;

		Object projectArchiveFile;

		String projectName;

		Object parent;

		int level;

		boolean hasConflicts;

		IProjectDescription description;

		/**
		 * Create a record for a project based on the info in the file.
		 * 
		 * @param file
		 */
		ProjectRecord(File file) {
			projectSystemFile = file;
			setProjectName();
		}

		/**
		 * @param file
		 * 		The Object representing the .project file
		 * @param parent
		 * 		The parent folder of the .project file
		 * @param level
		 * 		The number of levels deep in the provider the file is
		 */
		ProjectRecord(Object file, Object parent, int level) {
			this.projectArchiveFile = file;
			this.parent = parent;
			this.level = level;
			setProjectName();
		}

		/**
		 * Set the name of the project based on the projectFile.
		 */
		private void setProjectName() {
			try {

				// If we don't have the project name try again
				if (projectName == null) {
					IPath path = new Path(projectSystemFile.getPath());
					// if the file is in the default location, use the directory
					// name as the project name
					if (isDefaultLocation(path)) {
						projectName = path.segment(path.segmentCount() - 2);
						description = IDEWorkbenchPlugin.getPluginWorkspace()
								.newProjectDescription(projectName);
					} else {
						description = IDEWorkbenchPlugin.getPluginWorkspace()
								.loadProjectDescription(path);
						projectName = description.getName();
					}

				}
			} catch (CoreException e) {
				// no good couldn't get the name
			}
		}

		/**
		 * Returns whether the given project description file path is in the
		 * default location for a project
		 * 
		 * @param path
		 * 		The path to examine
		 * @return Whether the given path is the default location for a project
		 */
		private boolean isDefaultLocation(IPath path) {
			// The project description file must at least be within the project,
			// which is within the workspace location
			if (path.segmentCount() < 2)
				return false;
			return path.removeLastSegments(2).toFile().equals(
					Platform.getLocation().toFile());
		}

		/**
		 * Get the name of the project
		 * 
		 * @return String
		 */
		public String getProjectName() {
			return projectName;
		}


		/**
		 * @return Returns the hasConflicts.
		 */
		public boolean hasConflicts() {
			return hasConflicts;
		}
	}
}
