package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import commandLineMenus.List;
import commandLineMenus.ListOption;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import personnel.Employe;
import personnel.Ligue;
import personnel.SauvegardeImpossible;

public class EmployeConsole 
{
	private Option afficher(final Employe employe)
	{
		return new Option("Afficher l'employé", "l", () -> {System.out.println(employe);});
	}

//	ListOption<Employe> editerEmploye()
//	{
//		return (employe) -> editerEmploye(employe);		
//	}

	Option editerEmploye(Ligue ligue,Employe employe)
	{
			Menu menu = new Menu("Gérer le compte " + employe.getNom(), "c");
			menu.add(afficher(employe));
			menu.add(changerNom(employe));
			menu.add(changerPrenom(employe));
			menu.add(changerMail(employe));
			menu.add(changerPassword(employe));
			menu.add(nommerAdministrateur(ligue,employe));
			menu.add(ajouterDateArrivee(employe));
			menu.add(ajouterDateDepart(employe));
			menu.add(supprimerEmploye(ligue));
			menu.addBack("q");
			return menu;
	}

	//CHANGER NOM EMPLOYE SELECTIONNE
	private Option changerNom(final Employe employe)
	{
		return new Option("Changer le nom", "n", 
				() -> {try {
					employe.setNom(getString("Nouveau nom : "));
				} catch (SauvegardeImpossible e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
			);
	}
	
	//CHANGER PRENOM EMPLOYE
	private Option changerPrenom(final Employe employe)
	{
		return new Option("Changer le prénom", "p", () -> {try {
			employe.setPrenom(getString("Nouveau prénom : "));
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
	}
	
	//CHANGER MAIL EMPLOYE
	private Option changerMail(final Employe employe)
	{
		return new Option("Changer le mail", "e", () -> {try {
			employe.setMail(getString("Nouveau mail : "));
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
	}
	
	//CHANGER PASSWORD EMPLOYE
	private Option changerPassword(final Employe employe)
	{
		return new Option("Changer le password", "x", () -> {try {
			employe.setPassword(getString("Nouveau password : "));
		} catch (SauvegardeImpossible e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
	}
	
	// permet de changer/nommer l'admin d'une ligue
		private Option nommerAdministrateur(final Ligue ligue,final Employe employe)
			{
					return new Option("Nommer Administrateur","a", () -> {ligue.setAdministrateur(employe);});
						
			}
		
		
		LocalDate getDate(String prompt)
		{
			String dateStr = getString(prompt);
			if (dateStr.isEmpty()){
				return null;
			}else {
				try {
					return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					}catch (DateTimeParseException e) {
						System.out.println("format de date invalide");
						return getDate(prompt);
					}
			}
			
		}
	
		//AJOUTER UNE DATE ARRIVEE DE L'EMPLOYE:
		private Option ajouterDateArrivee(final Employe employe)
	{
			return new Option("Ajouter une date d'arrivée", "da", () -> { 
						//instancier la variable date:
						//LocalDate localDate = LocalDate.parse("YYYY-MM-DD");  
						//employe.setDateArrivee(dateArrivee);
				try
				{ 
				LocalDate newDate = LocalDate.parse(getString("entrer la date:"));
				employe.setDateArrivee(newDate);
				}
			   
				catch(IllegalArgumentException exception)
				{
					System.err.println("La date d'arrivée doit être antérieure à la date de départ");
				}
				catch(SauvegardeImpossible exception)
				{
					System.err.println("problème dans la BDD");
				}
			
			});
			
	}
			
		//AJOUTER UNE DATE DE DEPART DE L'EMPLOYE:
			private Option ajouterDateDepart(final Employe employe)
			{
				return new Option("Ajouter une date de départ", "dd", () -> {
					
				try
				{ 
				LocalDate newDate = LocalDate.parse(getString("entrer la date:"));
				employe.setDateDepart(newDate);
				}
			   
				catch(IllegalArgumentException exception)
				{
					System.err.println("La date d'arrivée doit être antérieure à la date de départ");
				}
				catch(SauvegardeImpossible exception)
				{
					System.err.println("problème dans la BDD");
				}
			});
		}
	//SUPPRIMER EMPLOYE SELECTIONNE
		private List<Employe> supprimerEmploye(final Ligue ligue)
		{
			return new List<>("Supprimer un employé", "s", 
			() -> new ArrayList<>(ligue.getEmployes()),
			(index, element) -> {element.remove();}
							);
		}

}
