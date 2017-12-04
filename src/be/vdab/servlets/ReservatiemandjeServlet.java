package be.vdab.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import be.vdab.repositories.VoorstellingRepository;

@WebServlet("/reservatiemandje.htm")
public class ReservatiemandjeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/JSP/reservatiemandje.jsp";
    private final transient VoorstellingRepository voorstellingRepository = new VoorstellingRepository();
	
    @Resource(name = VoorstellingRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		voorstellingRepository.setDataSource(dataSource);
	}	
    
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	@SuppressWarnings("unchecked")
		Map<Long,Integer> reservatiemandje = (Map<Long,Integer>)session.getAttribute("reservatiemandje");
    	if(reservatiemandje != null && !reservatiemandje.isEmpty()) {
    		request.setAttribute("voorstellingen", 
				voorstellingRepository.selectByIds(reservatiemandje.keySet()));
    	}
    	request.getRequestDispatcher(VIEW).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameterValues("verwijderen") != null) {
			HttpSession session = request.getSession();
			@SuppressWarnings("unchecked")
			Map<Long,Integer> reservatiemandje = (Map<Long,Integer>)session.getAttribute("reservatiemandje");
			Arrays.stream(request.getParameterValues("verwijderen"))
				.map(stringId -> Long.parseLong(stringId))
				.forEach(id -> reservatiemandje.remove(id));
			if(!reservatiemandje.isEmpty()) {
				session.setAttribute("reservatiemandje", reservatiemandje);
			}
			else {
				session.removeAttribute("reservatiemandje");
			}
		}
		response.sendRedirect(request.getRequestURI());
	}

}
