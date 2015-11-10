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
 * Servlet implementation class EditMusic
 */
@WebServlet("/EditMusic")
public class EditMusic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	ActionsBeanRemote action;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditMusic() {
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
		HttpSession session = request.getSession();
		String music_id = request.getParameter("getid");
		String title = request.getParameter("title");
		String artist = request.getParameter("artist");
		String album = request.getParameter("album");
		String year = request.getParameter("year");
		if(action.editMusicFile(music_id, title, artist, album, year)){
			request.setAttribute("error", "Salvo");
		}else{
			request.setAttribute("error", "Error: Nao foi salvo as alteracoes");
		}
		
		request.getRequestDispatcher("GoToMusic").forward(request, response);
	}

}
