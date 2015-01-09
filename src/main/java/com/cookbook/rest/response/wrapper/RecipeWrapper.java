package com.cookbook.rest.response.wrapper;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.cookbook.persistence.entity.RecipeEntity;

public class RecipeWrapper {
    @JsonProperty("recipies")
    private List<RecipeEntity> list;

    public List<RecipeEntity> getList() {
        return list;
    }

    public void setList(List<RecipeEntity> list) {
        this.list = list;
    }

	public RecipeWrapper(List<RecipeEntity> list) {
		super();
		this.list = list;
	}
	
	public RecipeWrapper() {
		super();
	}
	
    
    
}


