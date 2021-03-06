package site;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.Playlist;
import data.Users;
import ejblogin.ActionsBeanRemote;

/**
 * Servlet implementation class GoToPlaylist
 */
@WebServlet("/GoToPlaylist")
public class GoToPlaylist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	@EJB
	ActionsBeanRemote action;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoToPlaylist() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		if(session == null || session.getAttribute("user") == null){
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		}else{
			Users login = action.getUserByID(session.getAttribute("user").toString());
			request.setAttribute("nome", login.getNome());
			String order;
			if(request == null || request.getAttribute("order")==null){
				request.setAttribute("order", "ASC");
				order = "ASC";
			}else{
				order = request.getAttribute("order").toString();
			}
			List<Playlist> lists = action.listMyPlaylists(request.getSession().getAttribute("user").toString(), order);

			if(lists!=null){
				request.setAttribute("lists", lists);
			}else{
				request.setAttribute("error", "Error: Cannot list playlist");
			}
			
			request.getRequestDispatcher("/sessao/menuplaylist.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
				
	}

}
