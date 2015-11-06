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
 * Servlet implementation class Prefil
 */
@WebServlet("/Prefil")
public class Prefil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	UserLoginRegisterRemote conta;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Prefil() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Users login = conta.devolverPorId(session.getAttribute("user").toString());
		request.setAttribute("nome", login.getNome());
		request.setAttribute("user", login.getUser());
		request.setAttribute("email", login.getEmail());
		request.setAttribute("password", login.getPassword());
		
		request.getRequestDispatcher("/sessao/prefil.jsp").forward(request, response);
	}

}
