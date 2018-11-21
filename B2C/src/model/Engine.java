package model;

import java.util.ArrayList;
import java.util.List;

public class Engine {
	private static Engine instance = null;
	
	private Engine()
	{
	}

	public synchronized static Engine getInstance()
	{
		if (instance == null) instance = new Engine();
		return instance;
	}
	
	public List<ItemBean> doItem(String string)
	{
		return new ArrayList<ItemBean>();
	}
	
}
