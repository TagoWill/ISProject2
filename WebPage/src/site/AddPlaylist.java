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
 * Servlet implementation class AddPlaylist
 */
@WebServlet("/AddPlaylist")
public class AddPlaylist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	ActionsBeanRemote action;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddPlaylist() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("GoToMusic").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id_music = request.getParameter("getid");
		String id_playlist = request.getParameter("play");
		if(action.addMusicFileToPlaylist(id_music, id_playlist)){
			request.setAttribute("error", "Music added to playlist");
		}else{
			request.setAttribute("error", "Error: Cannot add music to playlist");
		}
		if(request.getParameter("goto")!=null){
			request.getRequestDispatcher("GoToSearch").forward(request, response);
		}else{
			request.getRequestDispatcher("GoToMusic").forward(request, response);
		}
	}
}
