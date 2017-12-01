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

@WebServlet("/nieuweklant.htm")
public class NieuweklantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/nieuweklant.jsp";
	private static final String REDIRECT_URL = "/bevestigingreservatie.htm";
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
		Klant.KlantBuilder klantBuilder = new Klant.KlantBuilder();
		if(StringUtils.isNotEmpty(request.getParameter("voornaam"))) {
			klantBuilder.metVoornaam(request.getParameter("voornaam"));
		}
		else {
			fouten.put("voornaam", "Voornaam niet ingevuld.");
		}
		
		if(StringUtils.isNotEmpty(request.getParameter("familienaam"))) {
			klantBuilder.metFamilienaam(request.getParameter("familienaam"));
		}
		else {
			fouten.put("familienaam", "Familienaam niet ingevuld.");
		}
		
		if(StringUtils.isNotEmpty(request.getParameter("straat"))) {
			klantBuilder.metStraat(request.getParameter("straat"));
		}
		else {
			fouten.put("straat", "Straat niet ingevuld.");
		}
		
		if(StringUtils.isNotEmpty(request.getParameter("huisnr"))) {
			klantBuilder.metHuisnr(request.getParameter("huisnr"));
		}
		else {
			fouten.put("huisnr", "Huisnummer niet ingevuld.");
		}
		
		if(StringUtils.isNotEmpty(request.getParameter("postcode"))) {
			klantBuilder.metPostcode(request.getParameter("postcode"));
		}
		else {
			fouten.put("postcode", "Postcode niet ingevuld.");
		}
		
		if(StringUtils.isNotEmpty(request.getParameter("gemeente"))) {
			klantBuilder.metGemeente(request.getParameter("gemeente"));
		}
		else {
			fouten.put("gemeente", "Gemeente niet ingevuld.");
		}
		
		if(StringUtils.isNotEmpty(request.getParameter("gebruikersnaam"))) {
			klantBuilder.metGebruikersnaam(request.getParameter("gebruikersnaam"));
		}
		else {
			fouten.put("gebruikersnaam", "Gebruikersnaam niet ingevuld.");
		}
		
		if(StringUtils.isNotEmpty(request.getParameter("paswoord"))) {
			if(StringUtils.isNotEmpty(request.getParameter("herhaalpaswoord"))) {
				String paswoord = request.getParameter("paswoord");
				if(paswoord.equals(request.getAttribute("herhaalpaswoord"))) {
					klantBuilder.metPaswoord(paswoord);
				}
				else {
					fouten.put("herhaalpaswoord", "Herhaal paswoord is verschillend van paswoord.");
				}
			}
			else {
				fouten.put("herhaalpaswoord", "Herhaal paswoord niet ingevuld");
			}
		}
		else {
			fouten.put("paswoord", "Paswoord niet ingevuld.");
		}
		if(fouten.isEmpty()) {
			klantBuilder.metId(1L);
			Klant klant = klantBuilder.maakKlant();
			if(klantRepository.klantToevoegen(klant)) {
				HttpSession session = request.getSession();
				session.setAttribute("klantId", klant.getId());
			}
			else {
				fouten.put("gebruikersnaam", "Deze gebruikersnaam bestaat al.");
			}
		}
		if(fouten.isEmpty()) {
			response.sendRedirect(request.getContextPath() + REDIRECT_URL);
		}
		else {
			request.setAttribute("fouten", fouten);
			request.getRequestDispatcher(VIEW).forward(request, response);
		}		
	}
}
