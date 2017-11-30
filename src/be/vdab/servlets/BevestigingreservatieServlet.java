package be.vdab.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import be.vdab.entities.Klant;
import be.vdab.util.StringUtils;
import vdab.be.repositories.KlantRepository;

@WebServlet("/bevestigingreservatie.htm")
public class BevestigingreservatieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/JSP/bevestigingreservatie.jsp";
    private final transient KlantRepository klantRepository = new KlantRepository();
	
    @Resource(name = KlantRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		klantRepository.setDataSource(dataSource);
	}
    
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,String> fouten = new HashMap<>();
		if(StringUtils.isNotEmpty(request.getParameter("gebruikersnaam"))) {
			if(StringUtils.isNotEmpty(request.getParameter("paswoord"))) {
				Klant klant = klantRepository.getKlantByGebruikersnaam(request.getParameter("gebruikersnaam")).orElse(null);
				if (klant != null) {
					if (klant.getPaswoord().equals(request.getParameter("paswoord"))) {
						HttpSession session = request.getSession();
						session.setAttribute("klantId", klant.getId());
						request.setAttribute("klant", klant);						
					}
					else {
						fouten.put("paswoord", "Dit paswoord is niet correct!");
					}
				}
				else {
					fouten.put("gebruikersnaam", "Deze gebruikersnaam is niet correct!");
				}
			}
			else {
				fouten.put("paswoord", "Paswoord moet ingevuld zijn!");
			}
		}
		else {
			fouten.put("gebruikersnaam", "Gebruikersnaam moet ingevuld zijn!");
		}
		response.sendRedirect(request.getRequestURI());
	}

}
// checken voor redirects. Disabelen knoppen enz via JSP, checken op session of klant
// knop bevestigen doorlinken naar juiste pagina via servlet