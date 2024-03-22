package personnel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.TreeSet;

/**
 * Employé d'une ligue hébergée par la M2L. Certains peuvent 
 * être administrateurs des employés de leur ligue.
 * Un seul employé, rattaché à aucune ligue, est le root.
 * Il est impossible d'instancier directement un employé, 
 * il faut passer la méthode {@link Ligue#addEmploye addEmploye}.
 * TEST
 */

public class Employe implements Serializable, Comparable<Employe>
{
	private static final long serialVersionUID = 4795721718037994734L;
	private String nom, prenom, password, mail;
	private Ligue ligue; 
	private GestionPersonnel gestionPersonnel;
	// insertion variable d'instance id
	private int idEmploye = -1;
	// insertion variables localDate:
	private LocalDate dateArrivee, dateDepart;
	
	
	/* ici rajout de public pour pouvoir faire le test dans testligue */
	//CONSTRUTEUR pour création EMPLOYE
	Employe(GestionPersonnel gestionPersonnel, Ligue ligue, String nom, String prenom, String mail, String password, LocalDate dateArrivee, LocalDate dateDepart)throws SauvegardeImpossible
	{
		this(gestionPersonnel, -1, ligue, nom, prenom, mail, password, dateArrivee, dateDepart);
		this.idEmploye = gestionPersonnel.insert(this);	
	}
	
	//CONSTRUTEUR 2 quand les données sont déjà présentes dans la BDD:
	Employe(GestionPersonnel gestionPersonnel,  int id, Ligue ligue, String nom, String prenom, String mail, String password, LocalDate dateArrivee, LocalDate dateDepart)
	{
		this.nom = nom;
		this.gestionPersonnel = gestionPersonnel;
		this.ligue = ligue;
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.password = password;
		this.dateArrivee = dateArrivee;
		this.dateDepart = dateDepart;
		this.idEmploye = id;
	}

	
	/**
	 * Retourne vrai si l'employé est administrateur de la ligue 
	 * passée en paramètre.
	 * @return vrai si l'employé est administrateur de la ligue 
	 * passée en paramètre.
	 * @param ligue la ligue pour laquelle on souhaite vérifier si this 
	 * est l'admininstrateur.
	 */
	
	/*GET ADMIN */
	public boolean estAdmin(Ligue ligue)
	{
		return ligue.getAdministrateur() == this;
	}


	/**
	 * Retourne vrai ssi l'employé est le root.
	 * @return vrai ssi l'employé est le root.
	 */
	
	/*GET ROOT*/
	public boolean estRoot()
	{
		return gestionPersonnel.getRoot() == this;
	}
	
	//récupérer l'ID de l'employé:
	public int getId()
	{
		return idEmploye;
	}
		//pas de setter car l'id reste fixe = LECTEUR SEULE
	
	/**
	 * Retourne le nom de l'employé.
	 * @return le nom de l'employé. 
	 */
	
	/* GET NOM*/
	public String getNom()
	{
		return nom;
	}

	/**
	 * Change le nom de l'employé.
	 * @param nom le nouveau nom.
	 */
	
	public void setNom(String nom)
	{
		this.nom = nom;
	}

	/**
	 * Retourne le prénom de l'employé.
	 * @return le prénom de l'employé.
	 */
	
	/* GET PRENOM */
	public String getPrenom()
	{
		return prenom;
	}
	
	/**
	 * Change le prénom de l'employé.
	 * @param prenom le nouveau prénom de l'employé. 
	 */

	public void setPrenom(String prenom)
	{
		this.prenom = prenom;
	}

	/**
	 * Retourne le mail de l'employé.
	 * @return le mail de l'employé.
	 */
	
	/* GET MAIL */
	public String getMail()
	{
		return mail;
	}
	
	/**
	 * Change le mail de l'employé.
	 * @param mail le nouveau mail de l'employé.
	 */

	public void setMail(String mail)
	{
		this.mail = mail;
	}

	/**
	 * Retourne vrai ssi le password passé en paramètre est bien celui
	 * de l'employé.
	 * @return vrai ssi le password passé en paramètre est bien celui
	 * de l'employé.
	 * @param password le password auquel comparer celui de l'employé.
	 */
	
	public boolean checkPassword(String password)
	{
		return this.password.equals(password);
	}

	/**
	 * Change le password de l'employé.
	 * @param password le nouveau password de l'employé. 
	 */
	
	public void setPassword(String password)
	{
		this.password= password;
	}

	/**
	 * Retourne la ligue à laquelle l'employé est affecté.
	 * @return la ligue à laquelle l'employé est affecté.
	 */
	
	/* GET LIGUE */
	public Ligue getLigue()
	{
		return ligue;
	}
	
	/* GET DATE ARRIVEE */
	/*retourne la date d'arrivée de l'employé dans la ligue */
	public LocalDate getDateArrivee()
	{
		return dateArrivee;
	}
	
    /* modifie la date d'arrivée de l'employé dans la ligue */
	public void setDateArrivee(LocalDate dateArrivee)
	{
		
		
		if (dateArrivee.isAfter(dateDepart)) 
		{
	        throw new IllegalArgumentException("La date d'arrivée doit être antérieure à la date de départ.");
	    }
		this.dateArrivee = dateArrivee;
	}


    
	/* GET DATE DEPART */
	/*retourne la date de départ de l'employé dans la ligue tesy */
      public LocalDate getDateDepart()
	{
		return dateDepart;
	}
      
     /* modifie la date de départ de l'employé dans la ligue */
      
	public void setDateDepart(LocalDate dateDepart)
	{
		this.dateDepart = dateDepart;
		if (dateDepart.isAfter(dateDepart)) 
		{
	        throw new IllegalArgumentException("La date de départ doit être postérieure à la date d'arrivée.");
	    }
	  
		
	}
    


	/* execeptions : */
	/* si l'user entre une date d'arrivée supérieure à une date de départ : */

	   /* public boolean datesIncoherantes( LocalDate dateArrivee, LocalDate DateDepart)
		{
			if ( this.dateArrivee>=this.dateDepart )
			{
				return false ;
			}
			else
				throw System.out.println(" La date d'arrivée doit être antérieure à la date de départ.");
		}*/
	
	
    /**
	 * Supprime l'employé. Si celui-ci est un administrateur, le root
	 * récupère les droits d'administration sur sa ligue.
	 */
	public void remove()
	{
		Employe root = gestionPersonnel.getRoot();
		if (this != root)
		{
			if (estAdmin(getLigue()))
				getLigue().setAdministrateur(root);
			getLigue().remove(this);
		}
		else
			throw new ImpossibleDeSupprimerRoot();
	}

	@Override
	public int compareTo(Employe autre)
	{
		int cmp = getNom().compareTo(autre.getNom());
		if (cmp != 0)
			return cmp;
		return getPrenom().compareTo(autre.getPrenom());
	}
	
	@Override
	public String toString()
	{
		String res = nom + " " + prenom + " " + mail + " (";
		if (estRoot())
			res += "super-utilisateur";
		// TODO if admin= affiche admin
		else
			res += ligue.toString();
		return res + ")";
	}
}
