package be.vdab.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import be.vdab.entities.Klant;
import be.vdab.entities.Reservatie;
import be.vdab.repositories.KlantRepository;
import be.vdab.repositories.ReservatieRepository;
import be.vdab.repositories.VoorstellingRepository;
import be.vdab.util.StringUtils;

@WebServlet("/bevestigingreservatie.htm")
public class BevestigingreservatieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String VIEW = "/WEB-INF/JSP/bevestigingreservatie.jsp";
    private static final String REDIRECT_URL_NIEUWE_KLANT = "/nieuweklant.htm";
    private static final String REDIRECT_URL_OVERZICHT = "/overzicht.htm";
    private final transient KlantRepository klantRepository = new KlantRepository();
	private final transient VoorstellingRepository voorstellingRepository = new VoorstellingRepository();
	private final transient ReservatieRepository reservatieRepository = new ReservatieRepository();
	    
    @Resource(name = KlantRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		klantRepository.setDataSource(dataSource);
		voorstellingRepository.setDataSource(dataSource);
		reservatieRepository.setDataSource(dataSource);
	}
    
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("klantId") != null) {
			Klant klant = klantRepository.getKlantById((long)session.getAttribute("klantId")).orElse(null);
			if(klant != null) {
				request.setAttribute("klant", klant);
			}
		}
    	request.getRequestDispatcher(VIEW).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("zoek") != null) {
			Map<String,String> fouten = new HashMap<>();
			if(StringUtils.isNotEmpty(request.getParameter("gebruikersnaam"))) {
				if(StringUtils.isNoScript(request.getParameter("gebruikersnaam"))) {
					if(StringUtils.isNotEmpty(request.getParameter("paswoord"))) {
						if(StringUtils.isNoScript(request.getParameter("paswoord"))) {
							Klant klant = klantRepository.getKlantByGebruikersnaam(request.getParameter("gebruikersnaam")).orElse(null);
							if (klant != null && klant.getPaswoord().equals(request.getParameter("paswoord"))) {
								HttpSession session = request.getSession();
								session.setAttribute("klantId", klant.getId());
								request.setAttribute("klant", klant);												
							}
							else {
								fouten.put("zoek", "Verkeerde gebruikersnaam of paswoord.");
							}
						}
						else {
							fouten.put("paswoord", "Je mag hier geen script invullen");
						}
					}
					else {
						fouten.put("paswoord", "Paswoord moet ingevuld zijn.");
					}
				}
				else {
					fouten.put("gebruikersnaam", "Je mag hier geen script invullen.");
				}
			}
			else {
				fouten.put("gebruikersnaam", "Gebruikersnaam moet ingevuld zijn.");
			}
			if (!fouten.isEmpty()) {
				request.setAttribute("fouten", fouten);
			}
			request.getRequestDispatcher(VIEW).forward(request,response);
		}
		
		if(request.getParameter("nieuw") != null) {
			response.sendRedirect(request.getContextPath() + REDIRECT_URL_NIEUWE_KLANT);
		}
		
		if(request.getParameter("bevestigen") != null) {
			StringBuilder parameterBuilder = new StringBuilder();
			parameterBuilder.append("?");
			HttpSession session = request.getSession();
			@SuppressWarnings("unchecked")
			Map<Long,Integer> reservatiemandje = (Map<Long,Integer>)session.getAttribute("reservatiemandje");
			if(!reservatiemandje.isEmpty() && session.getAttribute("klantId") != null) {
				long klantId = (long) session.getAttribute("klantId");
				Map<Long,Integer> gelukt = new LinkedHashMap<>();
				Map<Long,Integer> mislukt = new LinkedHashMap<>();
				Set<Reservatie> reservaties = new LinkedHashSet<>();
				reservatiemandje.entrySet().stream().forEach(entry -> 
					reservaties.add(new Reservatie(1L, klantId, entry.getKey(), entry.getValue())));
				Map<String,Map<Long,Integer>> geluktMislukt = reservatieRepository.reservatiesToevoegen(reservaties);
				gelukt = geluktMislukt.get("gelukt");
				mislukt = geluktMislukt.get("mislukt");
				
				if(!gelukt.isEmpty()) {
					gelukt.entrySet().stream().forEach(entry -> 
						parameterBuilder.append("geluktId="+entry.getKey()+"&"+"geluktPlaats="+entry.getValue()+"&"));
				}
				
				if(!mislukt.isEmpty()) {
					if(!mislukt.isEmpty()) {
						mislukt.entrySet().stream().forEach(entry ->
							parameterBuilder.append("misluktId="+entry.getKey()+"&"+"misluktPlaats="+entry.getValue()+"&"));
				}
				
//				for(Entry<Long,Integer> entry:reservatiemandje.entrySet()) {
//					if(voorstellingRepository.vrijePlaatsenVerminderen(entry.getKey(), entry.getValue())) {
//						gelukt.put(entry.getKey(), entry.getValue());
//					}
//					else {
//						mislukt.put(entry.getKey(), entry.getValue());
//					}
//				}
//				if(!gelukt.isEmpty()) {
//					Set<Reservatie> reservaties = new LinkedHashSet<>();					
//					gelukt.entrySet().stream().forEach(entry -> {
//						reservaties.add(new Reservatie(1L, klantId, entry.getKey(), entry.getValue()));
//						parameterBuilder.append("geluktId="+entry.getKey()+"&"+"geluktPlaats="+entry.getValue()+"&");
//					});
//					reservatieRepository.reservatiesToevoegen(reservaties);
////					voorstellingRepository.selectByIds(gelukt.keySet()).stream().forEach(voorstelling ->
////						gelukteReserveringen.put(voorstelling, gelukt.get(voorstelling.getId())));
//				}
//				if(!mislukt.isEmpty()) {
//					mislukt.entrySet().stream().forEach(entry ->
//						parameterBuilder.append("misluktId="+entry.getKey()+"&"+"misluktPlaats="+entry.getValue()+"&"));
////					voorstellingRepository.selectByIds(mislukt.keySet()).stream().forEach(voorstelling ->
////					mislukteReserveringen.put(voorstelling, mislukt.get(voorstelling.getId())));
//				}
				session.removeAttribute("reservatiemandje");
				session.removeAttribute("klantId");
			}
			parameterBuilder.deleteCharAt(parameterBuilder.length()-1);
			response.sendRedirect(request.getContextPath()+REDIRECT_URL_OVERZICHT+parameterBuilder.toString());
		}
	}
}

}