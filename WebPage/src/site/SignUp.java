package site;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		request.getRequestDispatcher("/register.jsp").forward(request, response);
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

        if(conta.verifyRegister(email)){
        	if(password.equals(password2)){
            	if(conta.registerUser(nome,username, password,email)){
            		request.setAttribute("error", "Registo feito com sucesso");
            		request.getRequestDispatcher("index.jsp").forward(request, response);
            	}else{
            		request.setAttribute("error", "Erro: Problemas no registo");
            		request.getRequestDispatcher("/register.jsp").forward(request, response);
            	}
            	
            }else{
            	request.setAttribute("error", "Erro: Password nao estao iguais");
            	request.getRequestDispatcher("/register.jsp").forward(request, response);
            }
        }else{
        	request.setAttribute("error", "Erro: Email ja existe");
        	request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
	}

}
