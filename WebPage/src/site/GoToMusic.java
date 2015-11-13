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

import data.Music;
import data.Playlist;
import data.Users;
import ejblogin.ActionsBeanRemote;

/**
 * Servlet implementation class GoToMusic
 */
@WebServlet("/GoToMusic")
public class GoToMusic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	@EJB
	ActionsBeanRemote action;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoToMusic() {
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
			String order = "ASC";
			List<Music> lists = action.listMyMusicFiles(request.getSession().getAttribute("user").toString(), order);
			List<Playlist> listplaylist = action.listMyPlaylists(request.getSession().getAttribute("user").toString(), order);
			if(lists!=null){
				request.setAttribute("lists", lists);
				request.setAttribute("listplaylist", listplaylist);
			}else{
				request.setAttribute("error", "Error: Cannot list playlist");
			}
			request.getRequestDispatcher("/sessao/menumusic.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
