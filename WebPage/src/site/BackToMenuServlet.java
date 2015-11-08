package site;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ejblogin.UserLoginRegisterRemote;
import ligacao.Users;

/**
 * Servlet implementation class BackToMenuServlet
 */
@WebServlet("/BackToMenuServlet")
public class BackToMenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	UserLoginRegisterRemote conta;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BackToMenuServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		/*if(request.getSession() != null || request.getSession().getAttribute("user") != null){
			doPost(request, response);
		}else{
			response.sendRedirect("index.jsp");
		}*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		HttpSession session = request.getSession();
		Users login = conta.devolverPorId(session.getAttribute("user").toString());
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
