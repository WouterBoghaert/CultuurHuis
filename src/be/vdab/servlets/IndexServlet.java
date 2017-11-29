package be.vdab.servlets;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import vdab.be.repositories.GenreRepository;
import vdab.be.repositories.VoorstellingRepository;

@WebServlet("/index.htm")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/index.jsp";
	private final transient GenreRepository genreRepository = new GenreRepository();
	private final transient VoorstellingRepository voorstellingRepository = new VoorstellingRepository();
		
	@Resource(name = GenreRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		genreRepository.setDataSource(dataSource);
		voorstellingRepository.setDataSource(dataSource);
	}	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("genres", genreRepository.toonAlleGenres());
		if(request.getParameter("genreId") != null) {
			request.setAttribute("voorstellingen", 
				voorstellingRepository.toonAlleVoorGenre(Long.parseLong(request.getParameter("genreId"))));
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
}
