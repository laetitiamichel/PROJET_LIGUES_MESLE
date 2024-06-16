package jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.Date;
import java.util.HashMap;

import personnel.*;

public class JDBC implements Passerelle 
{
	//variable qui permet de rester connecté à la base de données (comme PDO)getconnexion
	Connection connection;

	public JDBC()
	{
		try
		{
			Class.forName(Credentials.getDriverClassName());
			connection = DriverManager.getConnection(Credentials.getUrl(), Credentials.getUser(), Credentials.getPassword());
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Pilote JDBC non installé.");
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
	}

	@Override
	public GestionPersonnel getGestionPersonnel() throws SauvegardeImpossible
	{
		GestionPersonnel gestionPersonnel = new GestionPersonnel();
		try 
		{   
			//requête SQL pour récupérer l'employé qui est ROOT / statut=2:
			String requeteRecupRoot = ("select * from employe where statut=2");
			PreparedStatement selectInstructionRecupRoot = connection.prepareStatement(requeteRecupRoot);		
			ResultSet resultRecupRoot = selectInstructionRecupRoot.executeQuery();

			// Vérifier si le ROOT statut=2 existe dans la base de données
			if (resultRecupRoot.next()) {
				// Comparer le mot de passe récupéré avec celui fourni
				// Si le mot de passe est correct, ajouter l'employé au gestionnaire du personnel
				// Ajouter le ROOT avec son ID=getInt1,nom getString2 et son mot de passe getString(5)
				gestionPersonnel.addRoot(resultRecupRoot.getInt(1), resultRecupRoot.getString(2),resultRecupRoot.getString(5)); 


			} else {
				gestionPersonnel.addRoot("root","toor");
			}

			//chercher toutes les ligues:
			String requeteLigues = ("select * from ligue");
			PreparedStatement selectInstructionLigues = connection.prepareStatement(requeteLigues);		
			ResultSet resultLigues = selectInstructionLigues.executeQuery();
			while (resultLigues.next()) {
				Ligue ligue = gestionPersonnel.addLigue(resultLigues.getInt(1), resultLigues.getString(2));
				String requeteEmployes = ("select * from employe where ID_ligue =?");
				PreparedStatement selectInstructionEmployes = connection.prepareStatement(requeteEmployes);
				selectInstructionEmployes.setInt(1, ligue.getId());
				ResultSet resultEmployes = selectInstructionEmployes.executeQuery();

				while (resultEmployes.next()){
					// on sélection l'employé avec l'ID de la ligue = id_ligue ( clé étrangère)
					//permet de lire dans la BDD les employés de la ligue et récup les employés de cette ligue


					Employe employe = ligue.addEmploye(resultEmployes.getInt("ID_employe"),resultEmployes.getString("nom"),resultEmployes.getString("prenom"),resultEmployes.getString("mail"), resultEmployes.getString("password"),LocalDate.parse(resultEmployes.getString("dateArrivee")), LocalDate.parse(resultEmployes.getString("dateDepart")));

					// Vérifier si l'employé est un administrateur
					if (resultEmployes.getInt("statut") == 1) {
						ligue.setAdministrateur(employe); // Définir l'employé comme administrateur
					}

				}
				// Fermer les ressources pour les employés de cette ligue
				resultEmployes.close();
				selectInstructionEmployes.close();		           
			}

			// Fermer les ressources pour les ligues
			resultLigues.close();
			selectInstructionLigues.close();

	
	return gestionPersonnel;
	
	}


		catch (SQLException e)
		{
			System.out.println(e);
			throw new SauvegardeImpossible(e);
		}
	}

	@Override
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel) throws SauvegardeImpossible 
	{
		close();
	}

	public void close() throws SauvegardeImpossible
	{
		try
		{
			if (connection != null)
				connection.close();
		}
		catch (SQLException e)
		{
			throw new SauvegardeImpossible(e);
		}
	}


	//itération 3:
	@Override
	public int insert(Ligue ligue) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("insert into ligue (nomLigue) values(?)", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, ligue.getNom());		
			instruction.executeUpdate();
			ResultSet id = instruction.getGeneratedKeys();
			id.next();
			return id.getInt(1);
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}

	}
	@Override
	public int insert(Employe employe) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("insert into employe (nom,prenom,mail,password,dateArrivee,dateDepart,statut) values(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			instruction.setString(1, employe.getNom());
			instruction.setString(2, employe.getPrenom());
			instruction.setString(3, employe.getMail());
			instruction.setString(4, employe.getPassword());
			//instruction.setDate(5, Date.valueOf(employe.getDateArrivee()));
			//instruction.setDate(6, Date.valueOf(employe.getDateDepart()));
			instruction.setDate(5, Date.valueOf(LocalDate.now()));
			instruction.setDate(6, Date.valueOf(LocalDate.now()));
			//root=2 admin =1 user simple=0
			//root n'a pas de ligue
			if ( employe.getLigue()== null)
				instruction.setInt(7,2);
			else if ( employe.estAdmin(employe.getLigue())) {
				instruction.setInt(7,1);
			}
			else {
				instruction.setInt(7,0);
			}
			instruction.executeUpdate();
			ResultSet id = instruction.getGeneratedKeys();
			id.next();
			return id.getInt(1);
		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}		
	}

	//rappel procédure de la classe passerelle:
	@Override
	public void update(Ligue ligue) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("update ligue set nomLigue=? where ID_ligue=?");
			instruction.setString(1, ligue.getNom());
			instruction.setInt(2, ligue.getId());
			instruction.executeUpdate();

		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}

	}
	//rappel procédure de la classe passerelle:
	@Override
	public void update(Employe employe) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("update employe set nom=?,prenom=?,mail=?,password=?,dateArrivee=?,dateDepart=?,statut=? where ID_employe=?");
			instruction.setString(1, employe.getNom());
			instruction.setString(2, employe.getPrenom());
			instruction.setString(3, employe.getMail());
			instruction.setInt(4, employe.hashCode());
			instruction.setDate(5, Date.valueOf(employe.getDateArrivee()));
			instruction.setDate(6, Date.valueOf(employe.getDateDepart()));
				//on récup la ligue de l'employé et on vérif si il est admin de sa ligue
				// Définir l'employé comme administrateur=> retourne un booleen
				if(employe.estAdmin(employe.getLigue())==true){
					instruction.setInt(2,1); //1=admin de sa ligue=> update bDD	
				}
				else {
					instruction.setInt(2,0);//0=employe simple de sa ligue=> update BDD	= écrite BDD
				}
			//rajouter le statut i=si set admin = alors admin
			//si employe admin=1 => statut =1
			instruction.executeUpdate();

		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}

	}



	@Override
	public void remove(Employe employe) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("delete from employe where ID_employe=?");
			instruction.setString(1, employe.getNom());
			instruction.setInt(2, employe.getId());
			instruction.execute();

		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}

	}
	@Override
	public void remove(Ligue ligue) throws SauvegardeImpossible 
	{
		try 
		{
			PreparedStatement instruction;
			instruction = connection.prepareStatement("delete from employe where ID_ligue=?");
			instruction = connection.prepareStatement("delete from ligue where ID_ligue=?");

			instruction.setString(1, ligue.getNom());
			instruction.setInt(2, ligue.getId());
			instruction.execute();

		} 
		catch (SQLException exception) 
		{
			exception.printStackTrace();
			throw new SauvegardeImpossible(exception);
		}

	}



}
