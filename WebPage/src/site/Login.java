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
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	UserLoginRegisterRemote conta;
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
        
        /* ainda posso melhorar*/
        if(!username.equals("")){
        	Users login = conta.loginUser(username, password);
        	if(login != null){
        		HttpSession session = request.getSession();
                session.setAttribute("user", login.getId());
                //request.setAttribute("user", login.getUser()); /*Sem sentido*/
                //request.setAttribute("nome", login.getNome()); /*Sem sentido*/
                //response.sendRedirect(request.getContextPath() + "/sessao/paginainicial.jsp");
                /*TODO Abrir servlet em vez de pagina para it para a pagina inicial*/
                request.getRequestDispatcher("BackToMenuServlet").forward(request, response);
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
