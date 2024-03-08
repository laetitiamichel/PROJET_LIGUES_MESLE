package commandLine;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import java.util.ArrayList;

import commandLineMenus.List;
import commandLineMenus.Menu;
import commandLineMenus.Option;

import personnel.*;

public class LigueConsole 
{
	private GestionPersonnel gestionPersonnel;
	private EmployeConsole employeConsole;

	public LigueConsole(GestionPersonnel gestionPersonnel, EmployeConsole employeConsole)
	{
		this.gestionPersonnel = gestionPersonnel;
		this.employeConsole = employeConsole;
	}

	Menu menuLigues()
	{
		Menu menu = new Menu("Gérer les ligues", "l");
		menu.add(afficherLigues());
		menu.add(ajouterLigue());
		menu.add(selectionnerLigue());
		menu.addBack("q");
		return menu;
	}
	// AFFICHER LIGUES
	private Option afficherLigues()
	{
		return new Option("Afficher les ligues", "l", () -> {System.out.println(gestionPersonnel.getLigues());});
	}

	private Option afficher(final Ligue ligue)
	{
		return new Option("Afficher la ligue", "l", 
				() -> 
				{
					System.out.println(ligue);
					System.out.println("administrée par " + ligue.getAdministrateur());
				}
		);
	}
	
	

	//AJOUTER LIGUE
	private Option ajouterLigue()
	{
		return new Option("Ajouter une ligue", "a", () -> 
		{
			try
			{
				gestionPersonnel.addLigue(getString("nom : "));
			}
			catch(SauvegardeImpossible exception)
			{
				System.err.println("Impossible de sauvegarder cette ligue");
			}
		});
	}
	//SELECTIONNER LIGUE:
	private List<Ligue> selectionnerLigue()
	{
		return new List<Ligue>("Sélectionner une ligue", "e", 
				() -> new ArrayList<>(gestionPersonnel.getLigues()),
				(element) -> editerLigue(element)
				);
	}
	
	private Menu editerLigue(Ligue ligue)
	{
		Menu menu = new Menu("Editer " + ligue.getNom());
		menu.add(afficher(ligue));
		menu.add(gererEmployes(ligue));
		menu.add(changerNom(ligue));
		menu.add(supprimer(ligue));
		menu.addBack("q");
		return menu;
	}

	//CHANGER NOM LIGUE
	private Option changerNom(final Ligue ligue)
	{
		return new Option("Renommer", "r", 
				() -> {ligue.setNom(getString("Nouveau nom : "));});
	}

	//SUPPRIMER LIGUE
	private Option supprimer(Ligue ligue)
	{
		return new Option("Supprimer", "d", () -> {ligue.remove();});
	}
	
	
	
	private Menu gererEmployes(Ligue ligue)
	{
		Menu menu = new Menu("Gérer les employés de " + ligue.getNom(), "e");
		menu.add(selectionnerEmploye(ligue));
		menu.add(afficherEmployes(ligue));
		menu.add(ajouterEmploye(ligue));
		//menu.add(modifierEmploye(ligue));
		
		menu.addBack("q");
		return menu;
	}
	
	//SELECTIONNER UN EMPLOYE
	private List<Employe> selectionnerEmploye(final Ligue ligue)
	{
		return new List<>("Sélectionner un employé", "f", 
				() -> new ArrayList<>(ligue.getEmployes()),
				//affiche le sous menu - sélection un employé de la ligue pré sélectionnée
				(employe) -> employeConsole.editerEmploye(ligue,employe)
				//(element) -> selectionnerEmploye(element);
				);
	}
    //AFFICHER EMPLOYE SELECTIONNE	
	private Option afficherEmployes(final Ligue ligue)
	{
		return new Option("Afficher les employes", "l", () -> {System.out.println(ligue.getEmployes());});
	}
	
	
	// AJOUTER EMPLOYE A LA LIGUE
	private Option ajouterEmploye(final Ligue ligue)
	{
		return new Option("ajouter un employé", "a",
				() -> 
				{
					ligue.addEmploye(getString("nom : "), 
						getString("prenom : "), 
						getString("mail : "), 
						getString("password : "), 
						employeConsole.getDate("Date d'arrivée (format : YYYY-MM-DD) : "),
						employeConsole.getDate("Date de départ (format : YYYY-MM-DD) : "));
				}
		);
	}

	/*private List<Employe> modifierEmploye(final Ligue ligue)
	{
		return new List<>("Modifier un employé", "e", 
				() -> new ArrayList<>(ligue.getEmployes()),
				employeConsole.editerEmploye()
				);
	}*/
	

	

//	/*private Menu selectionnerUnEmploye(Ligue ligue)
//	{
//		Menu menu = new Menu("Sélectionner un employé " , "f");
//		menu.add(changerAdministrateur(ligue));
//		menu.add(supprimerEmploye(ligue));
//		menu.addBack("q");
//		return menu;
//	}*/
	
}

