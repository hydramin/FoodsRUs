package controler;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Engine;
import model.ItemBean;

/**
 * Servlet implementation class Items
 */
@WebServlet("/Items.do")
public class Items extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Items() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* 
		 * gets list of items and gets category id from the dropdown
		 * sends both as parameters when serving items.jspx
		 * in items.jspx we only display the items in the category 
		 * that was selected using the catID we got from the dropdown 
		 * */
		Engine engine = Engine.getInstance();
		List<ItemBean> list = null;
		/*drop down; in categories page, browse categories link*/
		String catID = request.getParameter("catID"); /* is int value, intended to display the whole category*/
		String byCategoryId = request.getParameter("byCategoryId"); /*flag true or null*/
				
		
		/* if a search query is sent the searchBtn parameter will be sent,else it is empty*/
		String bySearchTerm = request.getParameter("bySearchTerm"); /* flag, true or null*/
		String searchTerm = request.getParameter("searchTerm"); /*is sent by search bar*/
		
		
		System.out.printf("=>%s    =>%s\n",catID,bySearchTerm);
		try {
				if(bySearchTerm != null) {
					list = engine.doItem(searchTerm, bySearchTerm);					
				} else if (byCategoryId != null) {
					list = engine.doItem(catID, "byCategoryId");
				}
				request.setAttribute("items", list);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		/*call services of the Engine. pass the search terms to the engine. the method is called doSearch, doItem*/
		/*do item returns a list of ItemBean objects*/
		this.getServletContext().getRequestDispatcher("/pages/items.jspx").forward(request, response);			

		
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
