package site;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import ejblogin.ActionsBeanRemote;

/**
 * Servlet implementation class UpLoadMusic
 */
@WebServlet("/UpLoadMusic")
@MultipartConfig
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
		Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
		String fileName = filePart.getSubmittedFileName();
		File savefile = new File(getServletContext().getInitParameter("file-upload"),fileName);
		if(!savefile.exists()) {
			try (InputStream fileContent = filePart.getInputStream()){
				Files.copy(fileContent, savefile.toPath());
			}

			if(action.addMusicFile(session.getAttribute("user").toString(), title, artist, album, year, getServletContext().getInitParameter("file-upload").toString()+"/" + fileName)){
				request.setAttribute("error", "Saved");
			}else{
				request.setAttribute("error", "Error: Cannot make changes");
			}

			request.getRequestDispatcher("GoToMusic").forward(request, response);
		}else{
			request.setAttribute("error", "Error: File already exists");
			request.getRequestDispatcher("GoToMusic").forward(request, response);
		}
	}

}
