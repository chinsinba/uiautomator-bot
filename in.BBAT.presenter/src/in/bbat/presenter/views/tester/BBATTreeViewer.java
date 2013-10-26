package in.bbat.presenter.views.tester;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
/**
 * 
 * This class  can be used to create a {@link TreeViewer}. 
 * Creates a tree viewer
 * @author Syed Mehtab
 *
 */
public class BBATTreeViewer extends TreeViewer {

	/**
	 * This constructor is used to create a treeviewer.
	 * NOTE: The size of <var>titles</var> array, <var>bounds</var> and <var>columnHeaderImage</var> array should be same.
	 * 
	 * @param parent {@link Composite} for which a treeviewer has to be created.
	 * @param titles array of titles for the columns 
	 * @param bounds array of integers each specifying the width of the column. 
	 */
	public BBATTreeViewer(Composite parent,String[] titles, int[] bounds, Image[] columnHeaderImage){
		super(parent, SWT.FULL_SELECTION  | SWT.BORDER | SWT.MULTI | SWT.V_SCROLL|SWT.H_SCROLL);
		Tree tree = this.getTree();
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);

		if(columnHeaderImage!=null){
			createColumnViewerWithImages(titles, bounds, columnHeaderImage, tree);
		}
		else {
			createColumnViewer(titles, bounds,tree);
		}
	}

	private void createColumnViewer(String[] titles, int[] bounds, Tree tree) {
		for(int i=0;i<titles.length;i++){
			getColumnViewer(titles[i], tree,bounds[i],null);
		}		
	}

	private void createColumnViewerWithImages(String[] titles, int[] bounds, Image[] columnHeaderImage, Tree tree) {
		for(int i=0;i<titles.length;i++){
			getColumnViewer(titles[i], tree,bounds[i],columnHeaderImage[i]);
		}
	}

	private TreeViewerColumn getColumnViewer(String title, Tree tree, int bound, Image columnImage) {
		TreeColumn column = new TreeColumn(tree, SWT.LEFT);
		column.setText(title);
		column.setWidth(bound);
		column.setToolTipText(title);
		column.setAlignment(SWT.LEFT);
		column.setImage(columnImage);
		return new TreeViewerColumn(this, column);
	}

}
