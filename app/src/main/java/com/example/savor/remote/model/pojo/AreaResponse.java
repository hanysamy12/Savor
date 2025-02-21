package com.example.savor.remote.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreaResponse{
	@SerializedName("meals")
	private List<AreasItem> areas;

	public List<AreasItem> getAreas(){
		return areas;
	}
}