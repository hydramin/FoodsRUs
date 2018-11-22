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
 * Servlet implementation class Header
 */
@WebServlet("/Header.do")
public class Header extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Header() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* gets list of categories from engine
		 * then returns results as a list that
		 * is parsed in the jsp.				 */
		try
		{
			Engine engine = Engine.getInstance();
			List<CategoryBean> categories = engine.doCategory("");
			request.setAttribute("result", categories);
		}
		catch (Exception e)
		{
			request.setAttribute("error", e.getMessage());
			
		}		
		//this.getServletContext().getRequestDispatcher("/common/header.jspx").include(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
