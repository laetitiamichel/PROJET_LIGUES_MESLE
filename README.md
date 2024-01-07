# PROJET APPLICATION:

Un des responsables de la M2L, utilise une application pour gérer les employés des ligues. 

Cette application, très simple, n’existe qu’en ligne de commande et est mono-utilisateur. 

L'application  est conçue selon une architecture 3-tiers et vous devez conserver son architecture applicative.

Les niveaux d’habilitation des utilisateurs sont les suivants :

* Un simple employé de ligue peut ouvrir l’application et s’en servir comme un annuaire, mais il ne dispose d’aucun droit d’écriture.
* Un employé par ligue est admininstrateur et dispose de droits d’écriture peut gérer la liste des emloyés de sa propre ligue avec une application bureau.
* Le super-admininstrateur a accès en écriture à tous les employés des ligues. Il peut aussi gérer les comptes des administrateurs des ligues avec une application accessible en ligne de commande.
L’application doit être rendue multi-utilisateurs grace à l’utilisation d’une base de données.

Les trois niveaux d’habilitation ci-dessus doivent être mis en place.
# ARBRE HEURISTIQUE:

L'objectif de cet arbre est de représenter chaque menu et chaque option par un noeud.
Cet arbre a été réalisé sur le site FIGMA:

[Lien de l'arbre](https://www.figma.com/file/nKBFi9b7gsDLVyzOhAWF3z/Arbre-Heuristique?type=whiteboard&node-id=0%3A1&t=NrrkjkS34Nig52fh-1) 


![Arbre Heuristique (3)](https://hackmd.io/_uploads/SyE2VJmup.png)





# MCD:

En utilisant les données s'affichant en ligne de commande ainsi que le code de la couche métier de l'application, proposer un MCD permettant de représenter les données de l'application avec une base de données relationnelles.

Ici le MCD a été réalisé sur le logiciel MOKODO:

```
COMPOSER, 11 LIGUE, 0N  EMPLOYÉ
LIGUE: id_ligue, nom 
ADMINISTRER, 11 GESTION PERSONNEL, 11 LIGUE

EMPLOYE: id_employe, nom, prenom, mail, password
POSSEDER, 11 EMPLOYÉ , 11 GESTION PERSONNEL
GESTION PERSONNEL: id_employe, root
```

![MCD_projet_ligues](https://hackmd.io/_uploads/B1T2Ny7d6.png)
# MCD DATES :

![MCD_dates](https://hackmd.io/_uploads/SyKa2lEda.png)

```
COMPOSER, 11 LIGUE, 0N  EMPLOYÉ
LIGUE: id_ligue, nom 
ADMINISTRER, 11 GESTION PERSONNEL, 11 LIGUE

EMPLOYE: id_employe, nom, prenom, mail, password, dateArrivee, dateDepart
POSSEDER, 11 EMPLOYÉ , 11 GESTION PERSONNEL
GESTION PERSONNEL: id_employe, root
```


---
# AJOUT DES VARIABLES LOCALDATES:
```
private static final long serialVersionUID = 4795721718037994734L;
	private String nom, prenom, password, mail;
	private Ligue ligue;
	private GestionPersonnel gestionPersonnel;
	private LocalDate dateArrivee, dateDepart;
	
	Employe(GestionPersonnel gestionPersonnel, Ligue ligue, String nom, String prenom, String mail, String password, LocalDate dateArrivee, LocalDate dateDepart)
	{
		this.gestionPersonnel = gestionPersonnel;
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.mail = mail;
		this.ligue = ligue;
        	this.dateArrivee = dateArrivee;
        	this.dateDepart = dateDepart;
	}
    
    public LocalDate getDateArrivee()
	{
		return dateArrivee;
	}
    
	public void setDateArrivee()
	{
		this.dateArrivee = dateArrivee;
	}
    
    
      public LocalDate getDateDepart()
	{
		return dateDepart;
	}
	

	public void setDateDepart()
	{
    
		this.dateDepart = dateDepart;
	}
    

/* execptions : */
/* si l'user entre une date d'arrivée supérieure à une date de départ : */

    public boolean datesIncoherantes( LocalDate dateArrivee, LocalDate DateDepart)
	{
		if ( this.dateArrivee >= this.dateDepart )
		{
			return false ;
		}
		else
			throw System.out.println(" La date d'arrivée doit être antérieure à la date de départ.");
	}
```
