package testsUnitaires;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
//import org.junit.Before;
import org.junit.jupiter.api.Test;

import personnel.*;

class testLigue 
{

	GestionPersonnel gestionPersonnel;
	Ligue ligue;
	Employe employe;

	@BeforeEach
	void setUp() {
		//System.out.println("setUp");

		try {
			//variable d'instance:
			gestionPersonnel = GestionPersonnel.getGestionPersonnel();
			ligue = gestionPersonnel.addLigue("Fléchettes");
			employe = ligue.addEmploye("Bouchard", "Gérard", "g.bouchard@gmail.com", "azerty", LocalDate.of(2020, 5, 13),LocalDate.of(2024, 5, 13)); 
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// test ADD EMPLOYE avec JUNIT
	@Test
	void addEmploye() throws SauvegardeImpossible
	{
		//System.out.println("addE");
		assertEquals(employe, ligue.getEmployes().first());
	}
	
	// test SET EMPLOYE
	
	@Test 
	public void setEmploye() throws SauvegardeImpossible
	{
		
		employe.setNom("Franck"); // changement de nom avec méthode set
		assertEquals("Franck", employe.getNom()); // vérif si le nom est changé
	}
	
	// TEST SUPRESSION EMPLOYE
	@Test
	public void removeEmploye() throws SauvegardeImpossible 
	{
		
		employe.remove(); // supprime employé de la ligue
		ligue.getEmployes().isEmpty();//retourne vrai si employe supprimé
		assertTrue(ligue.getEmployes().isEmpty());// si méthode isempty est vrai donc test ok
			
	}
	
	//TEST CREATION LIGUE
		@Test
		void createLigue() throws SauvegardeImpossible
		{
			
			assertEquals("Fléchettes", ligue.getNom());
		}
		
	//test GETLIGUE
	@Test 
	 public void getLigues() throws SauvegardeImpossible 
	 {
 
	        // Appeler le getter et vérifier si la valeur renvoyée est correcte
	        assertTrue(gestionPersonnel.getLigues().contains(ligue));	//retourne vrai si ligue est récupérée   
	        }
	
	// TEST SET NOM LIGUE
	@Test
	public void setLigue() throws SauvegardeImpossible {
		
		ligue.setNom("Petanque"); // changement de nom avec méthode set
		assertEquals("Petanque", ligue.getNom()); // vérif si le nom est changé
	}

	
	// TEST SUPPRESSION LIGUE
	@Test
	public void removeLigues() throws SauvegardeImpossible 
	{	
		ligue.remove(); // supression ligue
		assertFalse(gestionPersonnel.getLigues().contains(ligue)); // vérif si la ligue est vide	
	}
	
	
	// TEST CHANGEMENT ADMIN
	@Test
	public void estAdmin() throws SauvegardeImpossible 
	{
		
		Employe Michel = ligue.addEmploye("Michel", "L", null, null, null,null);
		Employe tyty = ligue.addEmploye("tyty", "L", null, null, null,null);
		ligue.setAdministrateur(Michel);
		assertTrue(Michel.estAdmin(ligue)); // vérif si la ligue est vide
		assertEquals(Michel,ligue.getAdministrateur());
		ligue.setAdministrateur(tyty);
		//vérif que tyty est nouvel admin
		ligue.getAdministrateur();
		assertEquals(tyty,ligue.getAdministrateur());
		assertTrue(tyty.estAdmin(ligue)); // vérif si la ligue est vide
		//verif que michel n'est plus admin
		assertFalse(Michel.estAdmin(ligue));
	}
	
	// TEST SUPPRESSION ADMIN
	@Test
	public void removeAdmin() throws SauvegardeImpossible {
		
		Employe employe = ligue.addEmploye("Michel", "L", null, null, null,null);
		ligue.setAdministrateur(employe);
		ligue.getAdministrateur().remove();
		assertTrue(gestionPersonnel.getRoot().estAdmin(ligue)); // vérif si la ligue est vide
	}
	
	
	// TEST GET NOM
	 @Test 
	 public void getNom() throws SauvegardeImpossible 
	 {

	        // Appeler le getter et vérifier si la valeur renvoyée est correcte
	        assertEquals("Bouchard", employe.getNom());
	   }
	 //test SET NOM
	 @Test 
	 public void setNom() throws SauvegardeImpossible 
	 {
		 	//changement du prenom:
		 	employe.setNom("testNom");
	        // Appeler le getter et vérifier si la valeur renvoyée est correcte
	        assertEquals("testNom", employe.getNom());
	   }
	 
	 //TEST GET PRENOM
	 @Test 
	 public void getPrenom() throws SauvegardeImpossible 
	 {
		 	
		 	// Créer une instance de la classe à tester
		 	Employe employe = ligue.addEmploye("Michel","Laetitia", null, null, null,null);

	        // Appeler le getter et vérifier si la valeur renvoyée est correcte
	        assertEquals("Laetitia", employe.getPrenom());
	   }
	 //test SET PRENOM
	 @Test 
	 public void setPrenom() throws SauvegardeImpossible 
	 {
		 	//changement du prenom:
		 	employe.setPrenom("test");
	        // Appeler le getter et vérifier si la valeur renvoyée est correcte
	        assertEquals("test", employe.getPrenom());
	   }
	 
	 //TEST GET checkPassword
	 @Test
	 public void  checkPassword() throws SauvegardeImpossible 
	 {
	        // Appeler le getter et vérifier si la valeur renvoyée est correcte
	        assertTrue(employe.checkPassword("azerty"));
	   }
	 //TEST SET checkPassword
	 @Test
	 public void  setPassword() throws SauvegardeImpossible 
	 {
		 	employe.setPassword("testPassword");
	        // Appeler le getter et vérifier si la valeur renvoyée est correcte
	        assertTrue(employe.checkPassword("testPassword"));
	  }
	 
	 //TEST GET MAIL
	 @Test 
	 public void getMail() throws SauvegardeImpossible 
	 {
	        // Appeler le getter et vérifier si la valeur renvoyée est correcte
	        assertEquals("g.bouchard@gmail.com", employe.getMail());
	   }
	 
	 //test SET MAIL
	 @Test 
	 public void setMail() throws SauvegardeImpossible 
	 {
		 	//changement de l'email:
		 	employe.setMail("test@gmail.com");
	        // Appeler le getter et vérifier si la valeur renvoyée est correcte
	        assertEquals("test@gmail.com", employe.getMail());
	   }
 
	 // test GET DATEARRIVEE
	 @Test 
	 public void getDateArrivee() throws SauvegardeImpossible 
	 {
	        // Appeler le getter et vérifier si la valeur renvoyée est correcte
	        assertEquals(LocalDate.of(2020, 5, 13), employe.getDateArrivee());
	   }
	 
	 // test SET DATEARRIVEE
	 @Test 
	 public void setDateArrivee() throws SauvegardeImpossible 
	 {
		 //changement date arrivee:
		employe.setDateArrivee(LocalDate.of(2020, 6, 17));
		assertEquals(LocalDate.of(2020, 6, 17), employe.getDateArrivee());
		assertThrows(IllegalArgumentException.class, () -> {
			employe.setDateArrivee(LocalDate.of(2025, 6, 17));
		 });
		
	 }
	 
	 // TEST GET DATE DEPART
	 @Test 
	 public void getDateDepart() throws SauvegardeImpossible 
	 {
	        // Appeler le getter et vérifier si la valeur renvoyée est correcte
	        assertEquals(LocalDate.of(2024, 5, 13), employe.getDateDepart());
	   }
	 
	 // test SET DATEDEPART
	 @Test 
	 public void setDateDepart() throws SauvegardeImpossible 
	 {
		 //changement date Depart:
			employe.setDateDepart(LocalDate.of(2025, 6, 17));
			assertEquals(LocalDate.of(2025, 6, 17), employe.getDateDepart());
			assertThrows(IllegalArgumentException.class, () -> {
				//ici la date de départ est inférieure à la date d'arrivée:
				employe.setDateDepart(LocalDate.of(2017, 6, 17));
			 });
	 }
	
}
