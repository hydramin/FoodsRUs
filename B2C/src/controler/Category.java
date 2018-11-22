package controler;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CategoryBean;
import model.Engine;

/**
 * Servlet implementation class Catalog
 */
@WebServlet("/Category.do")
public class Category extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Category() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
			String prefix = null;
			try
			{
				Engine engine = Engine.getInstance();
				prefix = request.getParameter("prefix");
				List<CategoryBean> a = engine.doCategory(prefix);
				a.size();
				request.setAttribute("result", engine.doCategory(prefix));
				
			}
			catch (Exception e)
			{
				request.setAttribute("error", e.getMessage());
				
			}	
			request.setAttribute("prefix", prefix);	
	
			this.getServletContext().getRequestDispatcher("/pages/category.jspx").forward(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
