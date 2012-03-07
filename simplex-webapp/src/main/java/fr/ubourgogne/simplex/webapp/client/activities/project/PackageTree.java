package fr.ubourgogne.simplex.webapp.client.activities.project;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import fr.ubourgogne.simplex.model.java.JavaPackage;
import fr.ubourgogne.simplex.model.java.meta.JavaObjectCommonInfos;

public class PackageTree extends Composite {
	public interface Delegate {
		void onObjectSelected(JavaObjectCommonInfos object);
	}

	private CellTree tree;
	private ListDataProvider<Object> packages;

	public PackageTree(final List<JavaPackage> packages, final Delegate delegate) {
		List<Object> objs = new ArrayList<Object>();
		objs.addAll(packages);
		this.packages = new ListDataProvider<Object>(objs);

		final SingleSelectionModel<Object> selectionModel = new SingleSelectionModel<Object>();
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					public void onSelectionChange(SelectionChangeEvent event) {
						Object o = selectionModel.getSelectedObject();
						if (o instanceof JavaObjectCommonInfos) {
							delegate.onObjectSelected((JavaObjectCommonInfos) o);
						}
					}
				});
		final PackageCell cell = new PackageCell();

		CellTree.Resources res = GWT.create(CellTree.BasicResources.class);
		tree = new CellTree(new TreeViewModel() {

			@Override
			public boolean isLeaf(Object value) {
				if (value instanceof JavaPackage) {
					return ((JavaPackage) value).getPackages().isEmpty()
							&& ((JavaPackage) value).getObjects().isEmpty();
				}
				if (value instanceof JavaObjectCommonInfos) {
					return true;
				}
				return false;
			}

			@Override
			public <T> NodeInfo<?> getNodeInfo(T value) {
				if (value == null) {
					// Roots
					return new DefaultNodeInfo<Object>(
							PackageTree.this.packages, cell, selectionModel,
							null);
				}
				if (value instanceof JavaPackage) {
					System.out.println("liste des fils de " + value+" => "+((JavaPackage) value).getObjects());
					for(JavaObjectCommonInfos infos : ((JavaPackage) value).getObjects()){
						System.out.println("=> "+infos+" => "+infos.getObjectId()+" => "+infos.getObjectType()+" => "+infos.getObjectName()+" => "+infos.getObjectJavaDoc());
					}
					List<Object> fils = new ArrayList<Object>();
					fils.addAll(((JavaPackage) value).getPackages());
					fils.addAll(((JavaPackage) value).getObjects());
					return new DefaultNodeInfo<Object>(
							new ListDataProvider<Object>(fils), cell,
							selectionModel, null);
				}
				return null;
			}
		}, null, res);
		tree.setAnimationEnabled(true);

		initWidget(tree);
	}

}
