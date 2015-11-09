package site;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.Playlist;
import ejblogin.ActionsBeanRemote;
import ejblogin.UserLoginRegisterRemote;

/**
 * Servlet implementation class GoToPlaylist
 */
@WebServlet("/GoToPlaylist")
public class GoToPlaylist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	@EJB
	ActionsBeanRemote action;
	@EJB
	UserLoginRegisterRemote conta;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoToPlaylist() {
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
		//String order = request.getAttribute("order").toString();
		String order;
		//request.setAttribute("order", "ASC");
		if(request == null || request.getAttribute("order")==null){
			request.setAttribute("order", "ASC");
			order = "ASC";
		}else{
			order = request.getAttribute("order").toString();
		}
		List<Playlist> lists = action.listMyPlaylists(conta.devolverPorId(request.getSession().getAttribute("user").toString()), order);

		if(lists!=null){
			request.setAttribute("lists", lists);
		}else{
			request.setAttribute("error", "Error: Cannot list playlist");
		}
		
		request.getRequestDispatcher("/sessao/menuplaylist.jsp").forward(request, response);
		
	}

}