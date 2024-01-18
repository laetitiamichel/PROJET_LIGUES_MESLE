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

		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", null, null,null); 

		assertEquals(employe, ligue.getEmployes().first());
	}
	
	 @Test 
	 public void getNom() 
	 {
	        // Créer une instance de la classe à tester
		 	Employe employe = new Employe(gestionPersonnel,"Fléchettes", "Michel", null, null, null, null,null);

	        // Appeler le getter et vérifier si la valeur renvoyée est correcte
	        assertEquals("Michel", employe.getNom());
	   }
	 
	 @Test 
	 public void getPrenom() 
	 {
	        // Créer une instance de la classe à tester
		 	Employe employe = new Employe(gestionPersonnel,"Fléchettes", "Michel", "Laetitia", null, null, null,null);

	        // Appeler le getter et vérifier si la valeur renvoyée est correcte
	        assertEquals("Laetitia", employe.getPrenom());
	   }
	 
	 @Test 
	 public void getMail() 
	 {
	        // Créer une instance de la classe à tester
		 	Employe employe = new Employe(gestionPersonnel,"Fléchettes", "Michel", "Laetitia", "lm@gmail.com", null, null, null);

	        // Appeler le getter et vérifier si la valeur renvoyée est correcte
	        assertEquals("lm@gmail.com", employe.getMail());
	   }
	 
	 @Test 
	 public void getDateArrivee() 
	 {
	        // Créer une instance de la classe à tester
		 	Employe employe = new Employe(gestionPersonnel,"Fléchettes", "Michel", "Laetitia", "lm@gmail.com", "01/01/2001", null, null);

	        // Appeler le getter et vérifier si la valeur renvoyée est correcte
	        assertEquals("01/01/2001", employe.getDateArrivee());
	   }
	 
	 @Test 
	 public void getDateArrivee() 
	 {
	        // Créer une instance de la classe à tester
		 	Employe employe = new Employe(gestionPersonnel,"Fléchettes", "Michel", "Laetitia", "lm@gmail.com", "01/01/2001","01/01/2011");

	        // Appeler le getter et vérifier si la valeur renvoyée est correcte
	        assertEquals("01/01/2011", employe.getDateArrivee());
	   }
	
}
