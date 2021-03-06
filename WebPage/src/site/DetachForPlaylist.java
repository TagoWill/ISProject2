package site;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ejblogin.ActionsBeanRemote;

/**
 * Servlet implementation class DetachForPlaylist
 */
@WebServlet("/DetachForPlaylist")
public class DetachForPlaylist extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	ActionsBeanRemote action;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DetachForPlaylist() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session == null || session.getAttribute("user")==null){
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		}else{
			request.getRequestDispatcher("GoToPlaylist").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		String music_id = request.getParameter("getid");
		String playlist_id = request.getParameter("playlist_id");
		if(action.deleteMusicFileFromPlaylist(music_id, playlist_id)){
			request.getRequestDispatcher("GoToInfoPlaylist?playlist_id="+request.getParameter("playlist_id")).forward(request, response);
		}
		
	}

}
