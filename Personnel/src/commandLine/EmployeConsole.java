package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.time.LocalDate;
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
			menu.add(ajouterDateArrivee(employe, null));
			menu.add(ajouterDateDepart(employe, null));
			menu.add(supprimerEmploye(ligue));
			menu.addBack("q");
			return menu;
	}

	//CHANGER NOM EMPLOYE SELECTIONNE
	private Option changerNom(final Employe employe)
	{
		return new Option("Changer le nom", "n", 
				() -> {employe.setNom(getString("Nouveau nom : "));}
			);
	}
	
	//CHANGER PRENOM EMPLOYE
	private Option changerPrenom(final Employe employe)
	{
		return new Option("Changer le prénom", "p", () -> {employe.setPrenom(getString("Nouveau prénom : "));});
	}
	
	//CHANGER MAIL EMPLOYE
	private Option changerMail(final Employe employe)
	{
		return new Option("Changer le mail", "e", () -> {employe.setMail(getString("Nouveau mail : "));});
	}
	
	//CHANGER PASSWORD EMPLOYE
	private Option changerPassword(final Employe employe)
	{
		return new Option("Changer le password", "x", () -> {employe.setPassword(getString("Nouveau password : "));});
	}
	
	// permet de changer/nommer l'admin d'une ligue
		private Option nommerAdministrateur(final Ligue ligue,final Employe employe)
			{
					return new Option("Nommer Administrateur","a", () -> {ligue.setAdministrateur(employe);});
						
			}
		
		
		LocalDate getDate(String message)
		{
			return LocalDate.parse(getString(message));
		}
	
		//AJOUTER UNE DATE ARRIVEE DE L'EMPLOYE:
		private Option ajouterDateArrivee(final Employe employe, final LocalDate dateArrivee)
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
					System.err.println("LA date d'arrivée doit être antérieure à la date de départ");
				}
			});
			
	}
			
		//AJOUTER UNE DATE DE DEPART DE L'EMPLOYE:
			private Option ajouterDateDepart(final Employe employe, final LocalDate dateArrivee)
			{
				return new Option("Ajouter une date de départ", "dd", () -> {employe.setDateDepart();});
				
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
