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
	
	public static List<ItemBean> retrieve(String prefix) throws Exception
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
			
			//String sql = "SELECT * FROM SIS WHERE SURNAME LIKE ? AND GPA >= ?";  
			String sql = "SELECT * FROM ITEM WHERE NAME LIKE ?";
//			String pre;
			//float min;
			
//			if (prefix.isEmpty())
//				pre = "";
//			else
//				pre = prefix.substring(0, 1).toUpperCase() + prefix.substring(1);
	
			s = conn.prepareStatement(sql);
			s.setString(1, "%"+prefix+"%");
			
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
