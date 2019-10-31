package fr.gtm.demo;

import java.io.IOException;

import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/CompteurServlet")
public class CompteurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Inject																	//AVEC CDI	
	private Compteur compteur;												//AVEC CDI
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		HttpSession session = request.getSession();
//		Compteur compteur = (Compteur) session.getAttribute("compteur");
		compteur.incrementer();
		request.setAttribute("compteur", compteur); 						//AVEC CDI
		
		
//		if(compteur == null) { //si mon compteur est null, c'est que je n'ai pas ouvert de session (y a personne!)
//			try {
//				//InitialContext => contexte JNDI du serveur
//				InitialContext ctx = new InitialContext();																//ON MET CE BLOC DANS SESSIONLISTENER, EN MODIFIANT LE SERVLETCONTEXT !!!!
//				// recherche de l'EJB par le nom donné par le serveur
//				// => déclenche la construction de l'EJB
//				compteur = (Compteur) ctx.lookup("java:app/jboss-compteur/Compteur"); //je récupère mon compteur
//				session.setAttribute("compteur", compteur);
//			} catch (NamingException e) {
//				e.printStackTrace();
//			}
//		}
//		compteur.incrementer();
		
		
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/compteur.jsp");
		rd.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
