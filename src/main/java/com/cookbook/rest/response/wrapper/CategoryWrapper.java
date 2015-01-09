package com.cookbook.rest.response.wrapper;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.cookbook.persistence.entity.CategoryEntity;

public class CategoryWrapper {
    @JsonProperty("categories")
    private List<CategoryEntity> list;

    public List<CategoryEntity> getList() {
        return list;
    }

    public void setList(List<CategoryEntity> list) {
        this.list = list;
    }

	public CategoryWrapper(List<CategoryEntity> list) {
		super();
		this.list = list;
	}
	
	public CategoryWrapper() {
		super();
	}
	
    
    
}


