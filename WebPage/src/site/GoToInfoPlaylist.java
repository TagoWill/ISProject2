package site;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import data.Playlist;
import ejblogin.ActionsBeanRemote;

/**
 * Servlet implementation class GoToInfoPlaylist
 */
@WebServlet("/GoToInfoPlaylist")
public class GoToInfoPlaylist extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	ActionsBeanRemote action;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoToInfoPlaylist() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session == null || session.getAttribute("user") == null){
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		}else{
			String p_id = request.getParameter("playlist_id").toString();
			Playlist p_name = action.getPlaylistName(p_id);
			if (p_id != null || p_name != null) {
				request.setAttribute("playlist_id", p_id);
				request.setAttribute("name", p_name.getPlaylistName());
				request.getRequestDispatcher("/sessao/infoplaylist.jsp").forward(request, response);
			} else {
				request.setAttribute("error", "error: This playlist does not exist");
				request.getRequestDispatcher("GoToPlaylist").forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
