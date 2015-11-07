package site;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ejblogin.ActionsBean;

/**
 * Servlet implementation class EditarPrefilSevlet
 */
@WebServlet("/EditarPrefilSevlet")
public class EditarPrefilSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	@EJB
	ActionsBean conta;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditarPrefilSevlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		HttpSession session = request.getSession();
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		System.out.println("PRINTES! nome: "+nome +" email: "+email+" password: "+password);
		
		conta.editProfile(session.getAttribute("user").toString(), nome, email, password);
	}

}
