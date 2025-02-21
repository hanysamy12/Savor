package com.example.savor.remote.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientResponse{
	@SerializedName("meals")
	private List<IngredientItem> ingredient;

	public List<IngredientItem> getIngredient(){
		return ingredient;
	}
}