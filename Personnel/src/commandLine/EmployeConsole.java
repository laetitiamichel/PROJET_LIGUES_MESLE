package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.util.ArrayList;

import commandLineMenus.List;
import commandLineMenus.ListOption;
import commandLineMenus.Menu;
import commandLineMenus.Option;
import personnel.Employe;
import personnel.Ligue;

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
		
	//SUPPRIMER EMPLOYE SELECTIONNE
		private List<Employe> supprimerEmploye(final Ligue ligue)
		{
			return new List<>("Supprimer un employé", "s", 
			() -> new ArrayList<>(ligue.getEmployes()),
			(index, element) -> {element.remove();}
							);
		}

}
