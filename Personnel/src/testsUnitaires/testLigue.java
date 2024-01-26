package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import personnel.*;

class testLigue 
{
	GestionPersonnel gestionPersonnel = GestionPersonnel.getGestionPersonnel();
	
	//TEST CREATION LIGUE
	@Test
	void createLigue() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		assertEquals("Fléchettes", ligue.getNom());
	}

	// test GET EMPLOYE
	@Test
	void addEmploye() throws SauvegardeImpossible
	{
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");

		Employe employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", null, null); 

		assertEquals(employe, ligue.getEmployes().first());
	}
	// test SET EMPLOYE
	// A FAIRE THEO
	
	//test GETLIGUE
	@Test 
	 public void getLigues() throws SauvegardeImpossible 
	 {

		 	Ligue ligue = gestionPersonnel.addLigue("Fléchettes");// création d'une ligue        
	        // Appeler le getter et vérifier si la valeur renvoyée est correcte
	        assertEquals("Fléchettes", ligue.getLigue());
	   }
	
	// TEST SET LIGUE
	@Test
	public void setLigue() throws SauvegardeImpossible {
		
		Ligue ligue = gestionPersonnel.addLigue("Fléchettes");
		//ligue.administrateur.getLigue() = "nouveauNom";
		assertEquals("nouveauNom", ligue.administrateur.getLigue());
	}
	// TEST SUPRESSION EMPLOYE
	
	// TEST SUPPRESSION LIGUE
	
	// TEST CHANGEMENT ADMIN
	
	// TEST SUPPRESSION ADMIN
	
	
	// TEST GET NOM
	 @Test 
	 public void getNom() throws SauvegardeImpossible 
	 {
			Ligue ligue = gestionPersonnel.addLigue("Fléchettes");// création d'une ligue

	        // Créer une instance de la classe à tester
		 	Employe employe = ligue.addEmploye("Michel", "L", null, null, null,null);

	        // Appeler le getter et vérifier si la valeur renvoyée est correcte
	        assertEquals("Michel", employe.getNom());
	   }
	 
	 //TEST GET PRENOM
	 @Test 
	 public void getPrenom() throws SauvegardeImpossible 
	 {
		 	Ligue ligue = gestionPersonnel.addLigue("Fléchettes");// création d'une ligue   
		 	
		 	// Créer une instance de la classe à tester
		 	Employe employe = ligue.addEmploye("Michel","Laetitia", null, null, null,null);

	        // Appeler le getter et vérifier si la valeur renvoyée est correcte
	        assertEquals("Laetitia", employe.getPrenom());
	   }
	 
	 //TEST GET MAIL
	 @Test 
	 public void getMail() throws SauvegardeImpossible 
	 {

		 	Ligue ligue = gestionPersonnel.addLigue("Fléchettes");// création d'une ligue   
		 	
		 	// Créer une instance de la classe à tester
		 	Employe employe = ligue.addEmploye(null, null, "lm@gmail.com", null, null,null);
	       
	        // Appeler le getter et vérifier si la valeur renvoyée est correcte
	        assertEquals("lm@gmail.com", employe.getMail());
	   }
	 
	 // TEST GET DATE DEPART
	 @Test 
	 public void getDateDepart() throws SauvegardeImpossible 
	 {
		 	Ligue ligue = gestionPersonnel.addLigue("Fléchettes");// création d'une ligue   
		 	LocalDate DateDepart = LocalDate.parse("1/01/2001");
		 	// Créer une instance de la classe à tester
		 	Employe employe = ligue.addEmploye(null, null,null, null, null,DateDepart);
		 	
	        // Appeler le getter et vérifier si la valeur renvoyée est correcte
	        assertEquals("1/01/2001", employe.getDateDepart());
	   }
	 
	 // test GET DATEARRIVEE
	 @Test 
	 public void getDateArrivee() throws SauvegardeImpossible 
	 {
		 Ligue ligue = gestionPersonnel.addLigue("Fléchettes");// création d'une ligue   
		 	LocalDate dateArrivee = LocalDate.parse("1/01/2001");
		 	// Créer une instance de la classe à tester
		 	Employe employe = ligue.addEmploye(null, null,null, null,dateArrivee, null);
		 	
	        // Appeler le getter et vérifier si la valeur renvoyée est correcte
	        assertEquals("1/01/2001", employe.getDateArrivee());
	   }
	
}
