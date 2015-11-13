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
 * Servlet implementation class SearchMusic
 */
@WebServlet("/SearchMusic")
public class SearchMusic extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	ActionsBeanRemote action; 
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchMusic() {
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
			request.getRequestDispatcher("GoToSearch").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int tipo = Integer.parseInt(request.getParameter("tipo"));
		String search = request.getParameter("search_input");
		List<Music> list = action.searchAndListMusic(tipo, search, search);
		List<Playlist> listplaylist = action.listMyPlaylists(request.getSession().getAttribute("user").toString(), "ASC");
		if(list!=null){
			request.setAttribute("lists", list);
			request.setAttribute("listplaylist", listplaylist);
		}else{
			request.setAttribute("error", "Error: Cannot display results");
		}
		HttpSession session = request.getSession();
		Users login = action.getUserByID(session.getAttribute("user").toString());
		request.setAttribute("nome", login.getNome());
		request.getRequestDispatcher("/sessao/searchmusic.jsp").forward(request, response);
	}
}
