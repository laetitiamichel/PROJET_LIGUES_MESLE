package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import personnel.*;

class testLigue 
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	
	@Test
	void createLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		assertEquals("Fléchettes", ligue.getNom());
	}

	@Test
	void addEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");

		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", null, null); 

		assertEquals(employe, ligue.getEmployes().first());
	}
	
	 @Test 
	 public void getNom() throws SauvegardeImpossible 
	 {
			Ligue ligue = gestionPersonnel.addLigue("Fléchettes");// création d'une ligue

	        // Créer une instance de la classe à tester
		 	Employe employe = ligue.addEmploye("Michel", null, null, null, null,null);

	        // Appeler le getter et vérifier si la valeur renvoyée est correcte
	        assertEquals("Michel", employe.getNom());
	   }
	 
	 @Test 
	 public void getPrenom() 
	 {
		 	Ligue ligue = gestionPersonnel.addLigue("Fléchettes");// création d'une ligue   
		 	
		 	// Créer une instance de la classe à tester
		 	Employe employe = ligue.addEmploye(null,"Laetitia", null, null, null,null);

	        // Appeler le getter et vérifier si la valeur renvoyée est correcte
	        assertEquals("Laetitia", employe.getPrenom());
	   }
	 
	 @Test 
	 public void getMail() 
	 {

		 	Ligue ligue = gestionPersonnel.addLigue("Fléchettes");// création d'une ligue   
		 	
		 	// Créer une instance de la classe à tester
		 	Employe employe = ligue.addEmploye(null, null, "lm@gmail.com", null, null,null);
	       
	        // Appeler le getter et vérifier si la valeur renvoyée est correcte
	        assertEquals("lm@gmail.com", employe.getMail());
	   }
	 
	 @Test 
	 public void getDateArrivee() 
	 {
		 	Ligue ligue = gestionPersonnel.addLigue("Fléchettes");// création d'une ligue   
		 	
		 	// Créer une instance de la classe à tester
		 	Employe employe = ligue.addEmploye(null, null,null, "01/01/2001", null);
		 	
	        // Appeler le getter et vérifier si la valeur renvoyée est correcte
	        assertEquals("01/01/2001", employe.getDateArrivee());
	   }
	 
	 @Test 
	 public void getDateArrivee() 
	 {
		 Ligue ligue = gestionPersonnel.addLigue("Fléchettes");// création d'une ligue   
		 	
		 	// Créer une instance de la classe à tester
		 	Employe employe = ligue.addEmploye(null, null,null, null, "01/01/2011");

	        // Appeler le getter et vérifier si la valeur renvoyée est correcte
	        assertEquals("01/01/2011", employe.getDateArrivee());
	   }
	
}
