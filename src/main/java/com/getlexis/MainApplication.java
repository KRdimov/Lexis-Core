package com.getlexis;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.getlexis.resources.TemplateResource;

@ApplicationPath("/")
public class MainApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		 return new HashSet<Class<?>>(Arrays.asList(TemplateResource.class));
	}
}
