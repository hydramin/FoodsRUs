package controler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Engine;
import model.ItemBean;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search.do")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*grab the search term
		 * split it into words
		 * use each word to search the database*/
		Engine engine = Engine.getInstance();
		List<ItemBean> list;
		String search = request.getParameter("search");
		
		/*call services of the Engine. pass the search terms to the engine. the method is called doSearch, doItem*/
		/*do item returns a list of ItemBean objects*/
		try {
			list = engine.doItem(search);
			System.out.println(list.size());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		this.getServletContext().getRequestDispatcher("/pages/search.jspx").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
