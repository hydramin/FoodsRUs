package model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.shape.Ellipse;

public class Engine {
	private static Engine instance = null;	
	
	private Engine()
	{
		
	}

	/**
	 * @Description creates and delivers a singleton instance
	 * @return returns the singleton Engine object*/
	public synchronized static Engine getInstance()
	{
		if (instance == null) instance = new Engine();
		return instance;
	}
	
	/**
	 * @Description It takes in a search term and searches the database through the ItemDAO
	 * @param searchTerm
	 * @return a List<ItemBean> containing all items that match the search term
	 * @throws Exception 
	 * */
	public List<ItemBean> doItem(String searchTerm, String flag) throws Exception
	{
		/*split the search term by using regex
		 * loopthrough the array and pass a single search term to the DAO
		 * get the list from the DAO and add the contents to the total list
		 * return the total list*/
		
		if(flag != null) {
			String split[] = searchTerm.split("[\\s+]|[,+]");
			ArrayList<String> terms = new ArrayList<>();
			for (String term : split) {
				if(!term.isEmpty()) {
					terms.add(term);				
				}
			}
			return ItemDAO.retrieve(terms);
		} else {
			/*parse the catID to int and pass it to the DAO*/
			int catid = -1;
			try {
				catid = Integer.parseInt(searchTerm);
			} catch (Exception e) {
				throw new Exception("internal problem catID is not parsed to integer");
			}
			
			return ItemDAO.retrieve(catid);
		}
			
		
	}
	
	
	public List<CategoryBean> doCategory(String prefix) throws Exception
	{
		return CategoryDAO.retrieve(prefix);
	}
	
}