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
import ejblogin.ActionsBeanRemote;

/**
 * Servlet implementation class GoToSearch
 */
@WebServlet("/GoToSearch")
public class GoToSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	ActionsBeanRemote action;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoToSearch() {
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
			List<Music> lists = action.listAllMusic();
			List<Playlist> listplaylist = action.listMyPlaylists(request.getSession().getAttribute("user").toString(), "ASC");
			if(lists!=null){
				request.setAttribute("lists", lists);
				request.setAttribute("listplaylist", listplaylist);
			}else{
				request.setAttribute("error", "Error: Cannot list playlist");
			}
			request.getRequestDispatcher("/sessao/searchmusic.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
