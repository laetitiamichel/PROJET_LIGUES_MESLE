package personnel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Représente une ligue. Chaque ligue est reliée à une liste
 * d'employés dont un administrateur. Comme il n'est pas possible
 * de créer un employé sans l'affecter à une ligue, le root est 
 * l'administrateur de la ligue jusqu'à ce qu'un administrateur 
 * lui ait été affecté avec la fonction {@link #setAdministrateur}.
 */

public class Ligue implements Serializable, Comparable<Ligue>
{
	private static final long serialVersionUID = 1L;
	private int idLigue = -1;
	private String nomLigue;
	private SortedSet<Employe> employes;
	private Employe administrateur;
	private GestionPersonnel gestionPersonnel;
	
	/**
	 * Crée une ligue.
	 * @param nom le nom de la ligue.
	 */
	//création ligue sans connaître l'id car pas encore dans BDD
	//CONSTRUCTEUR 1 qui appelle CONSTRUCTEUR 2
	Ligue(GestionPersonnel gestionPersonnel, String nomLigue) throws SauvegardeImpossible
	{
		//constructeur avec this+()= SURCHARGE avec appel à JDBC de la BDD 
		//-1 = ID temporaire 
		this(gestionPersonnel, -1, nomLigue);
		this.idLigue = gestionPersonnel.insert(this);  
		//insert ligue dans BDD et valeur id récup par autoincrément
	}
	//charge ligue avec ID quand on lance l'appli donc 
	//on créait objet de type ligue car on connait id dans BDD
	//ici CONSTRUCTEUR 2:
	Ligue(GestionPersonnel gestionPersonnel, int id, String nomLigue)
	{
		this.nomLigue = nomLigue;
		employes = new TreeSet<>();
		this.gestionPersonnel = gestionPersonnel;
		administrateur = gestionPersonnel.getRoot();
		this.idLigue = id;
	}

	/**
	 * Retourne le nom de la ligue.
	 * @return le nom de la ligue.
	 */
	/* GET nom ligue */
	public String getNom()
	{
		return nomLigue;
	}

	/**
	 * Change le nom.
	 * @param nom le nouveau nom de la ligue.
	 */

	public void setNom(String nomLigue) throws SauvegardeImpossible
	{
		this.nomLigue = nomLigue;
		gestionPersonnel.update(this);
	}

	/**
	 * Retourne l'administrateur de la ligue.
	 * @return l'administrateur de la ligue.
	 **/
	
	//récupérer l'ID de la ligue:
	public int getId()
	{
		return idLigue;
	}
	//pas de setter car l'id reste fixe = LECTEUR SEULE
	
	public Employe getAdministrateur()
	{
		return administrateur;
	}

	/**
	 * Fait de administrateur l'administrateur de la ligue.
	 * Lève DroitsInsuffisants si l'administrateur n'est pas 
	 * un employé de la ligue ou le root. Révoque les droits de l'ancien 
	 * administrateur.
	 * @param administrateur le nouvel administrateur de la ligue.
	 */
	
	public void setAdministrateur(Employe administrateur)
	{
		Employe root = gestionPersonnel.getRoot();
		if (administrateur != root && administrateur.getLigue() != this)
			throw new DroitsInsuffisants();
		this.administrateur = administrateur;
	}

	/**
	 * Retourne les employés de la ligue.
	 * @return les employés de la ligue dans l'ordre alphabétique.
	 */
	
	public SortedSet<Employe> getEmployes()
	{
		return Collections.unmodifiableSortedSet(employes);
	}

	/**
	 * Ajoute un employé dans la ligue. Cette méthode 
	 * est le seul moyen de créer un employé.
	 * @param nom le nom de l'employé.
	 * @param prenom le prénom de l'employé.
	 * @param mail l'adresse mail de l'employé.
	 * @param password le password de l'employé.
	 * @return l'employé créé. 
	 */

	//fonction pour rajouter un employé dans la ligue
	public Employe addEmploye(String nom, String prenom, String mail, String password, LocalDate dateArrivee, LocalDate dateDepart, int statut)throws SauvegardeImpossible
	{
		Employe employe = new Employe(this.gestionPersonnel, this, nom, prenom, mail, password,dateArrivee,dateDepart, statut);
		employes.add(employe);
		return employe;
	}
	
	void remove(Employe employe)
	{
		employes.remove(employe);
	}
	
	/**
	 * Supprime la ligue, entraîne la suppression de tous les employés
	 * de la ligue.
	 */
	
	public void remove()
	{
		gestionPersonnel.remove(this);
	}
	

	@Override
	public int compareTo(Ligue autre)
	{
		return getNom().compareTo(autre.getNom());
	}
	
	@Override
	public String toString()
	{
		return nomLigue;
	}

}
