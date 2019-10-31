package fr.gtm.demo;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.enterprise.context.SessionScoped;

//@Stateful //pour déclarer l'EJB : les annotations @Singleton, @Stateful, @Stateless permettent au conteneur de créer l'EJB, et c'est le conteneur qui gère le cycle de vie.
//@StatefulTimeout(unit = TimeUnit.MINUTES, value = 10)
@SessionScoped //en mettant l'annotation @SessionScoped, on a plus besoin du SessionListener : on simplifie donc grandement le code !	//AVEC CDI
public class Compteur implements Serializable{					//AVEC CDI
	private static final Logger LOG = Logger.getLogger("DEMO");	//s'il y a plusieurs objets à instancier, je partage le meme logger à chaque fois ; et final c'est qu'une fois créer je peux pas le changer
	private int value;
	
	public Compteur() {
		LOG.info("Compteur - Constructeur");
	}

	@PostConstruct
	public void postConstruct() {
		LOG.info("Compteur - @PostConstruct : "+this);
	}
	
	@PreDestroy
	public void preDestroy() {
		LOG.info("Compteur - @PreDestroy : "+this);
	}
	
	public int getValue() {
		return value;
	}
	
	public void incrementer() {
		value++;
	}
	
	public void remove() {
		LOG.info(">>>Compteur - remove : "+this);
	}
}
