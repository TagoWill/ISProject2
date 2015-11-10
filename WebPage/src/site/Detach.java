package site;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ejblogin.ActionsBeanRemote;

/**
 * Servlet implementation class Detach
 */
@WebServlet("/Detach")
public class Detach extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	ActionsBeanRemote action;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Detach() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("GoToMusic").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(action.detachFromMusic(request.getParameter("getid"), true)){
			request.setAttribute("error", "Music detached");
			request.getRequestDispatcher("GoToMusic").forward(request, response);
		}else{
			request.setAttribute("error", "Error: Cannot detach music");
			request.getRequestDispatcher("GoToMusic").forward(request, response);
		}
	}

}
