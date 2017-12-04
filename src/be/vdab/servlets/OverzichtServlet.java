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
import javax.sql.DataSource;

import be.vdab.entities.Voorstelling;
import be.vdab.repositories.VoorstellingRepository;
import be.vdab.util.StringUtils;

@WebServlet("/overzicht.htm")
public class OverzichtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/overzicht.jsp";
	private final transient VoorstellingRepository voorstellingRepository = new VoorstellingRepository();
    
	@Resource(name = VoorstellingRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		voorstellingRepository.setDataSource(dataSource);
	}
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String [] geluktIds = request.getParameterValues("geluktId");
		String [] geluktPlaatsen = request.getParameterValues("geluktPlaats");
		String [] misluktIds = request.getParameterValues("misluktId");
		String [] misluktPlaatsen = request.getParameterValues("misluktPlaats");
		Map<Voorstelling, Integer> gelukteReserveringen = new LinkedHashMap<>();
		Map<Voorstelling, Integer> mislukteReserveringen = new LinkedHashMap<>();
		Map<Long,Integer> gelukt = new LinkedHashMap<>();
		Map<Long,Integer> mislukt = new LinkedHashMap<>();
		
		if (geluktIds != null && geluktPlaatsen != null) {
			for (int i=0; i<geluktIds.length; i++) {
				if(StringUtils.isLong(geluktIds[i]) && StringUtils.isInt(geluktPlaatsen[i])) {
					gelukt.put(Long.parseLong(geluktIds[i]), Integer.parseInt(geluktPlaatsen[i]));
				}
			}
			
			if(!gelukt.isEmpty()) {
				voorstellingRepository.selectByIds(gelukt.keySet()).stream().forEach(voorstelling ->
					gelukteReserveringen.put(voorstelling, gelukt.get(voorstelling.getId())));
			}
		}
		
		if (misluktIds != null && misluktPlaatsen != null) {
			for (int i=0; i<misluktIds.length; i++) {
				if(StringUtils.isLong(misluktIds[i]) && StringUtils.isInt(misluktPlaatsen[i])) {
					mislukt.put(Long.parseLong(misluktIds[i]), Integer.parseInt(misluktPlaatsen[i]));
				}
			}
			
			
			if(!mislukt.isEmpty()) {
				voorstellingRepository.selectByIds(mislukt.keySet()).stream().forEach(voorstelling ->
				mislukteReserveringen.put(voorstelling, mislukt.get(voorstelling.getId())));
			}
		}
		request.setAttribute("gelukteReserveringen", gelukteReserveringen);
		request.setAttribute("mislukteReserveringen", mislukteReserveringen);
		request.getRequestDispatcher(VIEW).forward(request, response);
	}


	
}
