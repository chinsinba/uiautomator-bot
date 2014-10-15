package in.BBAT.abstrakt.presenter.pkg.model;

import freemarker.ext.util.ModelCache;
import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.bbat.configuration.BBATProperties;
import in.bbat.utility.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.LibraryLocation;

public class BBATProjectUtils {



	public static IProject project(TestProjectModel testProj,String destination)  throws Exception{


		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(testProj.getName()+System.currentTimeMillis());
		if(project.exists())
		{
			//this is a hack 
			project.delete(true, new NullProgressMonitor());
		}
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

				IFolder srcFolder = project.getFolder("src");
				List<IClasspathEntry> entries = new ArrayList<IClasspathEntry>();
				entries.add(JavaCore.newLibraryEntry(new Path(BBATProperties.getInstance().getAndroid_AndroidJarPath(testProj.getApiLevel())), null, null));
				entries.add(JavaCore.newLibraryEntry(new Path(BBATProperties.getInstance().getAndroid_UiAutomatorPath(testProj.getApiLevel())), null, null));
				entries.add(JavaCore.newSourceEntry(srcFolder.getFullPath()));
				IVMInstall vmInstall = JavaRuntime.getDefaultVMInstall();
				LibraryLocation[] locations = JavaRuntime.getLibraryLocations(vmInstall);
				for (LibraryLocation element : locations) {
					entries.add(JavaCore.newLibraryEntry(element.getSystemLibraryPath(), null, null));
				}
				//add libs to project class pathproject
				javaProject.setRawClasspath(entries.toArray(new IClasspathEntry[entries.size()]), null);

				File dest = new File(destination+Path.SEPARATOR+testProj.getName());
				FileUtils.copyFolder(project.getLocation().toFile(), dest);
				File srcFold = new File(dest,"src");
				srcFold.mkdirs();
				File suiteFile = new File(srcFold, testProj.getName());
				suiteFile.mkdirs();
				FileUtils.copyFolder(new File(testProj.getResourcePath()), suiteFile);

				project.delete(true, new NullProgressMonitor());
			}
		} catch (CoreException e1) {
		}

		return project;

	}

}
