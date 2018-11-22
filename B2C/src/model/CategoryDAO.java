package model;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class CategoryDAO {
	public static List<CategoryBean> retrieve(String prefix) throws Exception
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
			String pre;
			if (prefix == null)
			{
				pre = "";
			}
			else
			{
				pre = prefix;
			}
			//String sql = "SELECT * FROM CATEGORY WHERE NAME LIKE ?";
			String sql = "SELECT A.COUNT, CATEGORY.* FROM CATEGORY LEFT OUTER JOIN (SELECT CATID, COUNT(*) AS COUNT FROM ITEM GROUP BY CATID) AS A ON ID = CATID WHERE NAME LIKE ?";
					
			s = conn.prepareStatement(sql);
			s.setString(1, "%"+pre+"%");
			
			r = s.executeQuery();
			
			List<CategoryBean> result = new ArrayList<CategoryBean>();
			while(r.next())
			{
				Blob image = r.getBlob("PICTURE");
				byte[] imgData = null;
				imgData = image.getBytes(1, (int)image.length());
				String picture =new String(Base64.getEncoder().encode(imgData));
				CategoryBean bean = new CategoryBean(r.getInt("ID"), r.getString("NAME"), r.getString("DESCRIPTION"), picture, r.getInt("COUNT"));
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
