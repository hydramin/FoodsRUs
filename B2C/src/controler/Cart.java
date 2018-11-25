package controler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
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
 * Servlet implementation class Cart
 */
@WebServlet("/Cart.do")
public class Cart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cart() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Engine engine = Engine.getInstance();
		
		String productName, productId, unitPrice,quantity,addToCart,internalUpdate,checkboxDelete;
		
		if(session.getAttribute("currentCart") == null) {
			session.setAttribute("currentCart", new TreeMap<String,ItemBought>());
		}
		
		addToCart  = request.getParameter("addToCart");		
		productName = request.getParameter("productName");
		productId = request.getParameter("productId");
		unitPrice = request.getParameter("unitPrice");
		quantity = request.getParameter("quantity");
		
		internalUpdate = request.getParameter("internalUpdate");
		checkboxDelete = request.getParameter("checkboxDelete");
		
		@SuppressWarnings("unchecked")
		TreeMap<String,ItemBought> allproducts = (TreeMap<String,ItemBought>) session.getAttribute("currentCart");
		
		if (addToCart != null) {
			/*external call to update cart*/			
			
			if(quantity == null || quantity.isEmpty() || Integer.parseInt(quantity)<= 0) {
				response.sendRedirect("Items.do");				
			} else {
				if(!allproducts.containsKey(productId)) {
					ItemBought newItem = engine.createItem(productId, productName, unitPrice,quantity);
					allproducts.put(productId,newItem);				
				} else {				
					ItemBought altered = allproducts.get(productId);
					altered.addQuantity(quantity);				
				}
			}
			
			
			System.out.printf("%s %s %s %s %s\n",productName, productId, unitPrice, addToCart, quantity);			
			
		} else if (internalUpdate != null) {
			/*internal call to update cart*/
			Enumeration<String> values = request.getParameterNames();
			ArrayList<String> delete = new ArrayList<>();
			ArrayList<String> change = new ArrayList<>();
			while (values.hasMoreElements()) {				
				String next = values.nextElement();
				if(next.endsWith("delete")) {
					delete.add(next);
				} else if (next.endsWith("quantity")) {
					change.add(next);					
				}
			}
			System.out.println(change);
			System.out.println(delete);
			
			/*process deletes*/
			for (String del : delete) {
				int pos = del.indexOf("_");
				String id = del.substring(0, pos);
				allproducts.remove(id);				
			}
			removeDeleted(delete,change);
			
			for (String ch : change) {
				int pos = ch.indexOf("_");
				String id = ch.substring(0, pos);
				int updateQuantity;
				if(request.getParameter(ch) != null && !request.getParameter(ch).isEmpty())
					updateQuantity = Integer.parseInt(request.getParameter(ch));
				else 
					continue;
				ItemBought updateItem = allproducts.get(id);
				if(updateItem != null) {
					if(updateQuantity == 0) {
						session.removeAttribute(id);
					} else if(updateQuantity < 0) {
						continue;
					} else {
						updateItem.setQuantity(updateQuantity);
					}
				}
			}
		}
					
		ArrayList<ItemBought> itemlist = new ArrayList<ItemBought>();
		for(Map.Entry<String, ItemBought> it: allproducts.entrySet()){
			itemlist.add(it.getValue());
		}
			
		request.setAttribute("list", itemlist);
		
		if(!response.isCommitted())
			this.getServletContext().getRequestDispatcher("/pages/cart.jspx").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void removeDeleted(ArrayList<String> delete, ArrayList<String> change) {
		for (String d : delete) {
			int pos = d.indexOf("_");
			String id = d.substring(0, pos);
			String rid = String.format("%s_quantity", id);
			change.remove(rid);
		}
	}

}
