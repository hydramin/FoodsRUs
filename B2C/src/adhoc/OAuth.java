package adhoc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class Outh
 */
@WebFilter("/OAuth.do")
public class OAuth implements Filter {

    /**
     * Default constructor. 
     */
    public OAuth() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		// pass the request along the filter chain
		System.out.println("FILTER IS DETECTED!");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		
		
		if(req.getServletPath().equals("/Login.do/Loggedin.do")) {
			String user = request.getParameter("user");
			String name = request.getParameter("name");
			String hash = request.getParameter("hash");
			System.out.printf("%s %s   %s\n",user,name,hash);
			
			session.setAttribute("username", user);
			session.setAttribute("fullname", name);
			session.setAttribute("hash", hash);
			
			if(session.getAttribute("path").equals("/Order.do")) {
				String param = "?back=http://localhost:8080/B2C_cart_done/Order.do";
				String oauth = " https://www.eecs.yorku.ca/~roumani/servers/auth/oauth.cgi" + param;
				res.sendRedirect(oauth);
				session.removeAttribute("path");
			} else {
//				String param = "?back=http://localhost:8080/B2C_cart_done/pages/login.jspx";
//				String oauth = " https://www.eecs.yorku.ca/~roumani/servers/auth/oauth.cgi" + param;
				res.sendRedirect("pages/login.jspx");
			}			
		} else {		
			
			String username = (String) session.getAttribute("username");
			if(username == null) {
				/*not logged in*/
					session.setAttribute("path", req.getServletPath());
					String param = "?back=http://localhost:8080/B2C_cart_done/Login.do/Loggedin.do";
					String oauth = " https://www.eecs.yorku.ca/~roumani/servers/auth/oauth.cgi" + param;
					res.sendRedirect(oauth);
				
			} else {
				chain.doFilter(request, response);
			}		
		}
		
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
