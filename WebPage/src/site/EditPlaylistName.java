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
 * Servlet implementation class EditPlaylistName
 */
@WebServlet("/EditPlaylistName")
public class EditPlaylistName extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	ActionsBeanRemote action;  
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditPlaylistName() {
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
		String p_id = request.getParameter("playlist_id");
		String p_name = request.getParameter("playlist_name");
		if(action.editPlaylist(p_id, p_name)){
			request.setAttribute("error", "Salvo");
		}else{
			request.setAttribute("error", "Error: Nao foi salvo as alteracoes");
		};
		request.getRequestDispatcher("GoToPlaylist").forward(request, response);
	}

}
