package site;

import java.io.IOException;
import java.net.URLEncoder;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ejblogin.UserLoginRegisterRemote;

/**
 * Servlet implementation class Login
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	UserLoginRegisterRemote login;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
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
		// TODO Auto-generated method stub
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        
        String message;
        System.out.println("Cheguei aqui");
        if(password.equals(password2)){
        	login.registerUser(nome,username, password,email);
        	message = "Registo feito com sucesso";
            response.sendRedirect("index.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
        }else{
        	request.setAttribute("error", "Erro: Password nao estao iguais");
        	//response.sendRedirect("register.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
        	request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
	}

}
