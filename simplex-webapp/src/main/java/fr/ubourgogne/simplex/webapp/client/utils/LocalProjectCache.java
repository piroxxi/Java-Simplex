package fr.ubourgogne.simplex.webapp.client.utils;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import fr.ubourgogne.simplex.model.java.JavaProject;
import fr.ubourgogne.simplex.webapp.client.rpc.OperationCallback;
import fr.ubourgogne.simplex.webapp.client.rpc.ServiceProvider;

@Singleton
public class LocalProjectCache {
	public interface CacheCallback {
		public void onProjectFound(JavaProject project);
	}

	private Map<String, JavaProject> projects = new HashMap<String, JavaProject>();
	private final ServiceProvider serviceProvider;

	@Inject
	public LocalProjectCache(ServiceProvider serviceProvider) {
		this.serviceProvider = new ServiceProvider();
	}

	// public LocalProjectCache(ServiceProvider serviceProvider) {
	// this.projects =
	// this.serviceProvider = serviceProvider;
	// }

	public void getProjectFromCache(final String projectId,
			final CacheCallback callback) {
		JavaProject p = projects.get(projectId);
		if (p != null) {
			callback.onProjectFound(p);
			return;
		}

		serviceProvider.getSimplexBaseService().getProject(projectId,
				new OperationCallback<JavaProject>() {
					@Override
					public void onSuccess(JavaProject result) {
						projects.put(projectId, result);
						callback.onProjectFound(result);
					}
				});

	}
}
