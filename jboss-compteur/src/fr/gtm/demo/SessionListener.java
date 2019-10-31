package fr.gtm.demo;

import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


@WebListener
public class SessionListener implements HttpSessionListener {
	private static final Logger LOG = Logger.getLogger("compteur");
   
    public SessionListener() {
       
    }

	
    public void sessionCreated(HttpSessionEvent se)  { 
//    	HttpSessionEvent request;
    	
		HttpSession session = se.getSession();	//la session vient d'être crééé, l'EJB n'existe pas encore !
    	Compteur compteur = (Compteur) session.getAttribute("compteur");
    	session.setMaxInactiveInterval(1*10);
    	if(compteur == null) { //si mon compteur est null, c'est que je n'ai pas ouvert de session (y a personne!)
			try {
				//InitialContext => contexte JNDI du serveur
				InitialContext ctx = new InitialContext();
				// recherche de l'EJB par le nom donné par le serveur
				// => déclenche la construction de l'EJB
				compteur = (Compteur) ctx.lookup("java:app/jboss-compteur/Compteur"); //je vais chercher un EJB avec son nom, ce qui déclenche la création de l'EJB (grâce au lookup) ; je recupère le compteur (càd l'EJB) qui a été crée grâce au lookup.
				session.setAttribute("compteur", compteur);
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}  	
    	
    }

	
    public void sessionDestroyed(HttpSessionEvent se)  { //la session va ensuite être détruite par mon conteneur
    	HttpSession session = se.getSession(); 
    	LOG.info(">>>Compteur - remove() : "+this);
    	Compteur compteur = (Compteur) session.getAttribute("compteur");
    	
    	if(compteur != null) {
    		compteur.remove(); //le conteneur va supprimer l'EJB, tant que la session est utilisée durant la durée entrée dans la méthode sessionInitialized : ici, pendant 1*10sec càd pendant 10 d'inactivité, la session se fermera automatiquement.
    	}
    }
	
}
