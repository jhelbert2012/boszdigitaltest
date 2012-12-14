package com.boszdigital.training.services;

import java.util.ArrayList;

import com.day.cq.wcm.api.Page;

public interface ListPropertiesServiceInterface {
	
	public void setCurrentPage(final Page currentPage);
	
	public ArrayList<String> getPropertyList();

}