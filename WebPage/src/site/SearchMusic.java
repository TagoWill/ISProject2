package site;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.Music;
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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int tipo = Integer.parseInt(request.getParameter("tipo"));
		String search = request.getParameter("search_input");
		List<Music> list = action.searchAndListMusic(tipo, search, search);
		if(list!=null){
			request.setAttribute("lists", list);
		}else{
			request.setAttribute("error", "Error: Cannot display results");
		}
		request.getRequestDispatcher("/sessao/searchmusic.jsp").forward(request, response);
	}
}
