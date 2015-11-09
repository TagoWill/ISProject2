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
import sun.awt.RepaintArea;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	ActionsBeanRemote action;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession(false);
		if(session == null || session.getAttribute("user") == null){
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}else{
			request.getRequestDispatcher("GoToMenuPrincipal").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        /* ainda posso melhorar*/
        if(!email.equals("")){
        	Users login = action.loginUser(email, password);
        	if(login != null){
        		HttpSession session = request.getSession();
                session.setAttribute("user", login.getId());
                request.getRequestDispatcher("GoToMenuPrincipal").forward(request, response);
        	}else{
        		request.setAttribute("error", "User não valido");
        		request.getRequestDispatcher("/index.jsp").forward(request, response);
        	}
            
        }else{
        	request.setAttribute("error", "Username esta vazio");
        	request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
        
	}

}
