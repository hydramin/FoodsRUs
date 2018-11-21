package model;

import java.util.ArrayList;
import java.util.List;

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
	public List<ItemBean> doItem(String searchTerm) throws Exception
	{
		/*split the search term by using regex
		 * loopthrough the array and pass a single search term to the DAO
		 * get the list from the DAO and add the contents to the total list
		 * return the total list*/
		ArrayList<ItemBean> total = new ArrayList<>();
		String split[] = searchTerm.split("[\\s+]|[,+]");
		for (String term : split) {
			if(!term.isEmpty()) {
				/*call retrieve() and access the list of items 
				 * gathered related to the current search term
				 * */
				List<ItemBean> list = ItemDAO.retrieve(term);
				for (ItemBean itemBean : list) {
					total.add(itemBean);
				}
			}
		}
		
		return total;
	}
	
}
