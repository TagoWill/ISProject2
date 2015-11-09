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
import ejblogin.UserLoginRegisterRemote;

/**
 * Servlet implementation class DeletePrefilServlet
 */
@WebServlet("/DeletePrefilServlet")
public class DeletePrefilServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	@EJB
	ActionsBeanRemote action;
	@EJB
	UserLoginRegisterRemote conta;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeletePrefilServlet() {
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
		
		if(action.deleteProfile(conta.devolverPorId(session.getAttribute("user").toString()))){
			session.invalidate();
			response.sendRedirect("index.jsp");
		}else{
			request.setAttribute("error", "Error: Utilizador nao apagado");
    		request.getRequestDispatcher("/sessao/prefil.jsp").forward(request, response);
		}
		
	}

}
