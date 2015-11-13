package site;

import java.io.IOException;

import javax.ejb.EJB;
import ejblogin.ActionsBeanRemote;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreatePlaylist
 */
@WebServlet("/CreatePlaylist")
public class CreatePlaylist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	@EJB
	ActionsBeanRemote action;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreatePlaylist() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("GoToPlaylist").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String playlist_name = request.getParameter("listname");
		if(!playlist_name.isEmpty())
		{
			if(action.addPlaylist(request.getSession().getAttribute("user").toString()  , playlist_name)){
				request.setAttribute("error", "Playlist created");
			}else{
				request.setAttribute("error", "Error: Cannot create playlist");
			}
		} else {
			request.setAttribute("error", "Error: Playlist name is empty");
		}	
		request.getRequestDispatcher("GoToPlaylist").forward(request, response);
	}

}
