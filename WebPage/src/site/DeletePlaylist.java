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
 * Servlet implementation class DeletePlaylist
 */
@WebServlet("/DeletePlaylist")
public class DeletePlaylist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	@EJB
	ActionsBeanRemote action;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeletePlaylist() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(action.deletePlaylist(request.getParameter("getid"), true)){
			request.setAttribute("error", "Apagado");
		}else{
			request.setAttribute("error", "Cannot delete");
		}
		request.getRequestDispatcher("GoToPlaylist").forward(request, response);
	
	}

}
