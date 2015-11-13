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
 * Servlet implementation class DeletePrefilServlet
 */
@WebServlet("/DeleteProfile")
public class DeleteProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	@EJB
	ActionsBeanRemote action;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteProfile() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		HttpSession session = request.getSession();
		if(action.deleteProfile(session.getAttribute("user").toString())){
			session.invalidate();
			response.sendRedirect("index.jsp");
		}else{
			Users login = action.getUserByID(session.getAttribute("user").toString());
			request.setAttribute("nome", login.getNome());
			request.setAttribute("error", "Error: Couldn't Delete User");
    		request.getRequestDispatcher("/sessao/menuprofile.jsp").forward(request, response);
		}
		
	}

}
