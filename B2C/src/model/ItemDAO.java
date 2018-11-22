package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
	
	/**
	 * @Description it searches the database using the list of search terms passed
	 * @param a non empty list of string search terms
	 * @return a list of ItemBean objects
	 * @throws several exceptions*/	
	
	public static List<ItemBean> retrieve(ArrayList<String> terms) throws Exception
	{
		/*if the list is empty dont do any search*/
		if (terms.isEmpty()) return new ArrayList<ItemBean>();
		
		Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
		Connection conn = DriverManager.getConnection("jdbc:derby://localhost:64413/EECS;user=student;password=secret");
		PreparedStatement s = null;
		ResultSet r = null;	
		try
		{
			s = conn.prepareStatement("set schema roumani");
			s.executeUpdate();
			s.close();
			
			String sql = "SELECT * FROM ITEM WHERE UPPER(NAME) LIKE ";
//			String sql = "SELECT * FROM ITEM WHERE UPPER(NAME) LIKE UPPER('%fruit%')";
			String query = "";			
			String p = "%";
			
			for (String term : terms) {
				query += (terms.indexOf(term) == 0)? String.format("UPPER('%s%s%s')",p,term,p): String.format(" OR UPPER(NAME) LIKE UPPER('%s%s%s')", p,term,p);
			}
			System.out.println(query);
			sql+=" "+query;
			s = conn.prepareStatement(sql);
//			s.setString(1, query);
			
			r = s.executeQuery();
			
			List<ItemBean> result = new ArrayList<ItemBean>();
			
			while(r.next())
			{
				ItemBean bean = new ItemBean(r.getInt("CATID"), r.getString("NUMBER"), r.getString("NAME"), r.getDouble("PRICE"));
				result.add(bean);
			}
			r.close(); s.close(); conn.close();
			return result;
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	/**
	 * @Description retrieves all the items in the database given a category id
	 * @param cid - categoryID
	 * @return a list of all ItemBean objects that have the same category id*/
	public static List<ItemBean> retrieve(int cid) throws Exception
	{	
		Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
		Connection conn = DriverManager.getConnection("jdbc:derby://localhost:64413/EECS;user=student;password=secret");
		PreparedStatement s = null;
		ResultSet r = null;	
		try
		{
			s = conn.prepareStatement("set schema roumani");
			s.executeUpdate();
			s.close();
			
			String sql = "SELECT * FROM ITEM WHERE CATEGORY = " + cid; 			
		
			System.out.println(sql);
			s = conn.prepareStatement(sql);
			
			r = s.executeQuery();
			
			List<ItemBean> result = new ArrayList<ItemBean>();
			
			while(r.next())
			{
				ItemBean bean = new ItemBean(r.getInt("CATID"), r.getString("NUMBER"), r.getString("NAME"), r.getDouble("PRICE"));
				result.add(bean);
			}
			r.close(); s.close(); conn.close();
			return result;
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	
}
