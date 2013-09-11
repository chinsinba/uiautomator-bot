package com.android.uiautomator;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;

import com.android.uiautomator.actions.ExpandAllAction;
import com.android.uiautomator.actions.ToggleNafAction;
import com.android.uiautomator.tree.BasicTreeNode;
import com.android.uiautomator.tree.BasicTreeNodeContentProvider;

public class XMLArea{

	public TreeViewer mTreeViewer;
	private ScreenShot shot;
	private NodeDetails nodeDetail;
	private UiAutomatorModel mModel;

	public XMLArea(Composite rightSash, NodeDetails detail, ScreenShot shot) {
		this.setShot(shot);
		this.nodeDetail = detail;
		createXMLTreeArea(rightSash);

	}

	private void createXMLTreeArea(Composite rightSash) {
		// upper-right base contains the toolbar and the tree
		Composite upperRightBase = new Composite(rightSash, SWT.BORDER);
		upperRightBase.setLayout(new GridLayout(1, false));

		ToolBarManager toolBarManager = new ToolBarManager(SWT.FLAT);
		toolBarManager.add(new ExpandAllAction(this));
		toolBarManager.add(new ToggleNafAction(this));
		toolBarManager.createControl(upperRightBase);

		mTreeViewer = new TreeViewer(upperRightBase, SWT.NONE);
		mTreeViewer.setContentProvider(new BasicTreeNodeContentProvider());
		// default LabelProvider uses toString() to generate text to display
		mTreeViewer.setLabelProvider(new LabelProvider());
		mTreeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				BasicTreeNode selectedNode = null;
				if (event.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection selection = (IStructuredSelection) event.getSelection();
					Object o = selection.getFirstElement();
					if (o instanceof BasicTreeNode) {
						selectedNode = (BasicTreeNode) o;
					}
				}

				getmModel().setSelectedNode(selectedNode);
				redrawScreenshot();
				if (selectedNode != null) {
					loadAttributeTable();
				}
			}
		});
		Tree tree = mTreeViewer.getTree();
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		// move focus so that it's not on tool bar (looks weird)
		tree.setFocus();
	}

	public void expandAll() {
		mTreeViewer.expandAll();
	}

	public boolean shouldShowNafNodes() {
		return getmModel() != null ? getmModel().shouldShowNafNodes() : false;
	}

	public void toggleShowNaf() {
		if (getmModel() != null) {
			getmModel().toggleShowNaf();
		}
	}

	public void loadAttributeTable() {
		nodeDetail.loadAttributeTable();
	}

	/**
	 * Causes a redraw of the canvas.
	 *
	 * The drawing code of canvas will handle highlighted nodes and etc based on data
	 * retrieved from Model
	 */
	public void redrawScreenshot() {
		getShot().redrawScreenshot();
	}

	public void updateTreeSelection(BasicTreeNode node) {
		mTreeViewer.setSelection(new StructuredSelection(node), true);
	}

	public void setInputHierarchy(Object input) {
		mTreeViewer.setInput(input);
	}

	public UiAutomatorModel getmModel() {
		return mModel;
	}

	public void setmModel(UiAutomatorModel mModel) {
		this.mModel = mModel;
	}

	public void loadTree() {
		BasicTreeNode wrapper = new BasicTreeNode();
		// putting another root node on top of existing root node
		// because Tree seems to like to hide the root node
		wrapper.addChild(getmModel().getXmlRootNode());
		setInputHierarchy(wrapper);
		mTreeViewer.getTree().setFocus();		
	}

	public ScreenShot getShot() {
		return shot;
	}

	public void setShot(ScreenShot shot) {
		this.shot = shot;
	}
}