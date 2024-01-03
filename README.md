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


![Arbre Heuristique (2)]([https://github.com/laetitiamichel/PROJET_LIGUES_MESLE/blob/developpement/Arbre%20Heuristique.png](https://github.com/laetitiamichel/PROJET_LIGUES_MESLE/blob/developpement/MCD_projet_ligues.png))





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

![MCD_projet_ligues](https://hackmd.io/_uploads/HyAbRAfup.png)
