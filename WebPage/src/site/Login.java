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
import ligacao.Users;;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	UserLoginRegisterRemote teste;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
		//doGet(request, response);
		String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("user: "+username);
        /* ainda posso melhorar*/
        if(!username.equals("")){
        	Users conta = teste.loginUser(username, password);
            HttpSession session = request.getSession();
            session.setAttribute("user", conta.getUser());
            request.setAttribute("nome", "adeus");
            //response.sendRedirect(request.getContextPath() + "/sessao/paginainicial.jsp");
            request.getRequestDispatcher("/sessao/paginainicial.jsp").forward(request, response);
        }else{
        	request.setAttribute("message", "Username esta vazio");
        	request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
        
	}

}
