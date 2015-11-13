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
 * Servlet implementation class GoToInfoMusic
 */
@WebServlet("/GoToInfoMusic")
public class GoToInfoMusic extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	ActionsBeanRemote action;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GoToInfoMusic() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		if(session == null || session.getAttribute("user") == null){
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		}else{
			Users login = action.getUserByID(session.getAttribute("user").toString());
			request.setAttribute("nome", login.getNome());
			request.getRequestDispatcher("/sessao/infomusic.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
