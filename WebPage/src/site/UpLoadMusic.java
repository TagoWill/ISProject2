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
 * Servlet implementation class UpLoadMusic
 */
@WebServlet("/UpLoadMusic")
public class UpLoadMusic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	@EJB
	ActionsBeanRemote action;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpLoadMusic() {
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
			request.getRequestDispatcher("/sessao/infomusic.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String title = request.getParameter("title");
		String artist = request.getParameter("artist");
		String album = request.getParameter("album");
		String year = request.getParameter("year");
		/*faltam coisas*/
		if(action.addMusicFile(session.getAttribute("user").toString(), title, artist, album, year, "")){
			request.setAttribute("error", "Salvo");
		}else{
			request.setAttribute("error", "Error: Nao foi salvo as alteracoes");
		}
		
		request.getRequestDispatcher("GoToMusic").forward(request, response);
	}

}
