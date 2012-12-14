package com.boszdigital.training.services;

import java.util.ArrayList;
import java.util.Set;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;

import com.day.cq.wcm.api.Page;

import org.osgi.framework.Constants;

/**
 * Example OSGi service using SCR annotations.
 */
@Component(immediate = true, metatype = true)
@Service(ListPropertiesService.class)
@Properties({
    @Property(name = Constants.SERVICE_VENDOR, value = "Boszdigital"),
    @Property(name = Constants.SERVICE_DESCRIPTION, value = "Helping list properties.")
})

public class ListPropertiesService implements ListPropertiesServiceInterface{
	
	private Page currentPage = null;
	
	public void setCurrentPage(final Page currentPage){
		this.currentPage = currentPage;
	}
	
	public ArrayList<String> getPropertyList(){
		ArrayList<String> output = new ArrayList<String>();
		Set<String> props = currentPage.getProperties().keySet();
		for (String key : props ) {
			String prop = currentPage.getProperties().get(key).toString();
			if(prop.length() < 50){
				output.add(key + " - " + prop);
			} else {
				output.add(key + " - " + prop.substring(0,40) + "[...]");
			}
		}
		return output;
	}

}
