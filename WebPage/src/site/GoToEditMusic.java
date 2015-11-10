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
import ejblogin.ActionsBeanRemote;
import ligacao.Users;

/**
 * Servlet implementation class GoToEditMusic
 */
@WebServlet("/GoToEditMusic")
public class GoToEditMusic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	ActionsBeanRemote action;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoToEditMusic() {
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
			String music_id = request.getParameter("music_id");
			Music infomusic = action.getInfoMusicFile(music_id);
			request.setAttribute("music_id", music_id);
			request.setAttribute("title", infomusic.getTitle());
			request.setAttribute("artist", infomusic.getArtist());
			request.setAttribute("album", infomusic.getAlbum());
			request.setAttribute("year", infomusic.getYear());
			request.setAttribute("path", infomusic.getPath());
			//response.sendRedirect(request.getContextPath() + "/sessao/prefil.jsp");
			request.getRequestDispatcher("/sessao/editmusic.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
