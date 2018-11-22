package model;

import java.sql.Blob;

public class CategoryBean {
	
	private int id;
	private String name;
	private String description;
	private String picture;
	private int number;
	
	public CategoryBean(int id, String name, String description, String picture, int number) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.picture = picture;
		this.number = number;
	}

	public String toString()
	{
		return "Category [id" + id + ", name=" + name + ", description=" + description + ", picture=" + picture + "]";
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String name)
	{
		this.name = description;
	}
	
	public String getPicture()
	{
		return picture;
	}
	
	public void setPicture(String picture)
	{
		this.picture = picture;
	}
	
	public int getNumber()
	{
		return number;
	}

	public void setNumber(int number)
	{
		this.number = number;
	}	
}
