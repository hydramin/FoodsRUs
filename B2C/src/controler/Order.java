package controler;

import java.io.IOException;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Engine;
import model.ItemBought;

/**
 * Servlet implementation class Order
 */
@WebServlet("/Order.do")
public class Order extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Order() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Engine engine = Engine.getInstance();
		HttpSession session = request.getSession();
		try {
			@SuppressWarnings("unchecked")
			TreeMap<String, ItemBought> allproducts = (TreeMap<String, ItemBought>) session.getAttribute("currentCart");
			engine.createOrders(allproducts);
			request.setAttribute("orders", engine.orderList());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (session.getAttribute("currentCart") != null)
			session.removeAttribute("currentCart");
		this.getServletContext().getRequestDispatcher("/pages/orders.jspx").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
