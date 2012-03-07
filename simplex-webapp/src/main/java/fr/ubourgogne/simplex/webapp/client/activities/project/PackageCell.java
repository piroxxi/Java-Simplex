package fr.ubourgogne.simplex.webapp.client.activities.project;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import fr.ubourgogne.simplex.model.java.JavaPackage;
import fr.ubourgogne.simplex.model.java.meta.JavaObjectCommonInfos;

public class PackageCell extends AbstractCell<Object> {
	@Override
	public void render(Context context, Object value, SafeHtmlBuilder sb) {
		if (value instanceof JavaPackage) {
			String pName = ((JavaPackage) value).getName();
			if (pName.indexOf(".") != -1) {
				pName = pName.substring(pName.lastIndexOf(".") + 1);
			}
			sb.appendHtmlConstant("<img src=\"d_java.png\"/>&nbsp;");
			sb.appendEscaped(pName);
		}
		if (value instanceof JavaObjectCommonInfos) {
			sb.appendHtmlConstant("<img src=\"f_java.png\"/>&nbsp;");
			JavaObjectCommonInfos obj = ((JavaObjectCommonInfos) value);
			if (obj.getObjectName() != null)
				sb.appendEscaped(obj.getObjectName());
			else
				sb.appendEscaped("<nom introuvable>");
		}
	}
}