package site;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ejblogin.ActionsBeanRemote;
import ligacao.Users;

/**
 * Servlet implementation class BackToMenuServlet
 */
@WebServlet("/GoToMenuPrincipal")
public class GoToMenuPrincipal extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	ActionsBeanRemote action;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GoToMenuPrincipal() {
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
			Users login = action.devolverPorId(session.getAttribute("user").toString());
			if(login != null){
				request.setAttribute("user", login.getUser());
				request.setAttribute("nome", login.getNome());
				request.setAttribute("error", "");
				request.getRequestDispatcher("/sessao/menuprincipal.jsp").forward(request, response);
			}else{
				request.setAttribute("error", "Por algum problema aconteceu SignOut");
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		HttpSession session = request.getSession();
		Users login = action.devolverPorId(session.getAttribute("user").toString());
		if(login != null){
			request.setAttribute("user", login.getUser());
			request.setAttribute("nome", login.getNome());
			request.setAttribute("error", "");
			request.getRequestDispatcher("/sessao/menuprincipal.jsp").forward(request, response);
		}else{
			request.setAttribute("error", "Por algum problema aconteceu SignOut");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}

}
