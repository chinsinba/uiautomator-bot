package com.android.uiautomator;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.android.uiautomator.tree.AttributePair;

public class NodeDetails {

		private TableViewer mTableViewer;
		private UiAutomatorModel mModel;


		public NodeDetails(Composite rightSash) {
			createNodeDetailsArea(rightSash);
		}

		private void createNodeDetailsArea(Composite rightSash) {
			// lower-right base contains the detail group
			Composite lowerRightBase = new Composite(rightSash, SWT.BORDER);
			lowerRightBase.setLayout(new FillLayout());
			Group grpNodeDetail = new Group(lowerRightBase, SWT.NONE);
			grpNodeDetail.setLayout(new FillLayout(SWT.HORIZONTAL));
			grpNodeDetail.setText("Node Detail");

			Composite tableContainer = new Composite(grpNodeDetail, SWT.NONE);

			TableColumnLayout columnLayout = new TableColumnLayout();
			tableContainer.setLayout(columnLayout);

			mTableViewer = new TableViewer(tableContainer, SWT.NONE | SWT.FULL_SELECTION);
			Table table = mTableViewer.getTable();
			table.setLinesVisible(true);
			// use ArrayContentProvider here, it assumes the input to the TableViewer
			// is an array, where each element represents a row in the table
			mTableViewer.setContentProvider(new ArrayContentProvider());

			TableViewerColumn tableViewerColumnKey = new TableViewerColumn(mTableViewer, SWT.NONE);
			TableColumn tblclmnKey = tableViewerColumnKey.getColumn();
			tableViewerColumnKey.setLabelProvider(new ColumnLabelProvider() {
				@Override
				public String getText(Object element) {
					if (element instanceof AttributePair) {
						// first column, shows the attribute name
						return ((AttributePair)element).key;
					}
					return super.getText(element);
				}
			});
			columnLayout.setColumnData(tblclmnKey,
					new ColumnWeightData(1, ColumnWeightData.MINIMUM_WIDTH, true));

			TableViewerColumn tableViewerColumnValue = new TableViewerColumn(mTableViewer, SWT.NONE);
			tableViewerColumnValue.setEditingSupport(new AttributeTableEditingSupport(mTableViewer));
			TableColumn tblclmnValue = tableViewerColumnValue.getColumn();
			columnLayout.setColumnData(tblclmnValue,
					new ColumnWeightData(2, ColumnWeightData.MINIMUM_WIDTH, true));
			tableViewerColumnValue.setLabelProvider(new ColumnLabelProvider() {
				@Override
				public String getText(Object element) {
					if (element instanceof AttributePair) {
						// second column, shows the attribute value
						return ((AttributePair)element).value;
					}
					return super.getText(element);
				}
			});
		}
		
		
		private class AttributeTableEditingSupport extends EditingSupport {

			private TableViewer mViewer;

			public AttributeTableEditingSupport(TableViewer viewer) {
				super(viewer);
				mViewer = viewer;
			}

			@Override
			protected boolean canEdit(Object arg0) {
				return true;
			}

			@Override
			protected CellEditor getCellEditor(Object arg0) {
				return new TextCellEditor(mViewer.getTable());
			}

			@Override
			protected Object getValue(Object o) {
				return ((AttributePair)o).value;
			}

			@Override
			protected void setValue(Object arg0, Object arg1) {
			}
		}
		
		public void loadAttributeTable() {
			// udpate the lower right corner table to show the attributes of the node
			mTableViewer.setInput(getmModel().getSelectedNode().getAttributesArray());
		}

		public UiAutomatorModel getmModel() {
			return mModel;
		}

		public void setmModel(UiAutomatorModel mModel) {
			this.mModel = mModel;
		}


	}