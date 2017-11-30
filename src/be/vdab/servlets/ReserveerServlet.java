package be.vdab.servlets;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import be.vdab.util.StringUtils;
import vdab.be.repositories.VoorstellingRepository;

@WebServlet("/reserveren.htm")
public class ReserveerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/reserveren.jsp";
	private static final String REDIRECT_URL = "/reservatiemandje.htm";
    private final transient VoorstellingRepository voorstellingRepository = new VoorstellingRepository();
    
    
    @Resource(name = VoorstellingRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		voorstellingRepository.setDataSource(dataSource);
	}	
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(StringUtils.isNotEmpty(request.getParameter("voorstellingId")) && StringUtils.isLong(request.getParameter("voorstellingId"))) {
			request.setAttribute("voorstelling", voorstellingRepository.selectById(Long.parseLong(request.getParameter("voorstellingId"))).orElse(null));;
		}
		request.getRequestDispatcher(VIEW).forward(request,response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(StringUtils.isNotEmpty(request.getParameter("plaatsen")) && StringUtils.isInt(request.getParameter("plaatsen"))) {
			if(StringUtils.isNotEmpty(request.getParameter("vrijePlaatsen")) && StringUtils.isInt(request.getParameter("vrijePlaatsen"))) {
				if(Integer.parseInt(request.getParameter("plaatsen")) >= Integer.parseInt(request.getParameter("vrijePlaatsen"))) {
					HttpSession session = request.getSession();
					Map<Long,Integer> reservatiemandje = new LinkedHashMap<>();
					reservatiemandje.put(Long.parseLong(request.getParameter("voorstellingId")),Integer.parseInt(request.getParameter("plaatsen")));
					session.setAttribute("reservatiemandje", reservatiemandje);
					response.sendRedirect(request.getRequestURI() + REDIRECT_URL);
				}
				else {
					String fout = "Tik een getal tussen 1 en " + request.getParameter("vrijePlaatsen");
					request.setAttribute("fout", fout);
					request.getRequestDispatcher(VIEW).forward(request,response);
				}
			}
		}
		else {
			request.setAttribute("fout", "Plaatsen moet correct ingevuld zijn!");
			request.getRequestDispatcher(VIEW).forward(request,response);
		}
	}
}
