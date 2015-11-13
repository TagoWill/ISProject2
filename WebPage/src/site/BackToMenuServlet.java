package site;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.Users;
import ejblogin.ActionsBeanRemote;

/**
 * Servlet implementation class BackToMenuServlet
 */
@WebServlet("/BackToMenuServlet")
public class BackToMenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	ActionsBeanRemote action;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BackToMenuServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("null")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session != null || session.getAttribute("user") != null){
			Users login = action.getUserByID(session.getAttribute("user").toString());
	    	if(login != null){
	            request.setAttribute("user", login.getUser());
	            request.setAttribute("nome", login.getNome());
	            request.setAttribute("error", "");
	            request.getRequestDispatcher("/sessao/menuprincipal.jsp").forward(request, response);
	    	}else{
	    		request.setAttribute("error", "An error occurred");
	    		request.getRequestDispatcher("/index.jsp").forward(request, response);
	    	}
		}else{
			response.sendRedirect("index.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Users login = action.getUserByID(session.getAttribute("user").toString());
    	if(login != null){
            request.setAttribute("user", login.getUser());
            request.setAttribute("nome", login.getNome());
            request.setAttribute("error", "");
            request.getRequestDispatcher("/sessao/menuprincipal.jsp").forward(request, response);
    	}else{
    		request.setAttribute("error", "An error occurred");
    		request.getRequestDispatcher("/index.jsp").forward(request, response);
    	}
	}

}
