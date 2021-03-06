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

/**
 * Servlet implementation class Login
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	ActionsBeanRemote conta;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session == null || session.getAttribute("user") == null){
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		}else{
			request.getRequestDispatcher("GoToMenuPrincipal").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");

        if(conta.verifyRegister(email)){
        	if(password.equals(password2)){
            	if(conta.registerUser(nome,username, password,email)){
            		request.setAttribute("error", "Sign Up Successful");
            		request.getRequestDispatcher("index.jsp").forward(request, response);
            	}else{
            		request.setAttribute("error", "Error: Something Went Wrong");
            		request.getRequestDispatcher("/register.jsp").forward(request, response);
            	}
            }else{
            	request.setAttribute("error", "Error: Passwords Do Not Match");
            	request.getRequestDispatcher("/register.jsp").forward(request, response);
            }
        }else{
        	request.setAttribute("error", "Error: Email Already Registered");
        	request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
	}

}
