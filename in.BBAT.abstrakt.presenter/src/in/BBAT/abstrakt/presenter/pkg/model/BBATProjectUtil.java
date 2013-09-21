package in.BBAT.abstrakt.presenter.pkg.model;


import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;

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

		ProjectRecord record = new ProjectRecord(new File("/home/syed/Documents/trrwree/.project"));

		try {
			createExistingProject(record, new NullProgressMonitor());
		} catch (InvocationTargetException e2) {
			e2.printStackTrace();
		}

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
