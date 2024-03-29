package jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	public GestionPersonnel getGestionPersonnel()
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
	        
	        
	        while() {
	        // on sélection l'employé avec l'ID de la ligue = id_ligue ( clé étrangère)
	        //permet de lire dans la BDD les employés de la ligue et récup les employés de cette ligue
			String requeteEmployes = ("select * from employe where ID_ligue =?");
			Statement selectInstructionEmployes = connection.prepareStatement(requeteEmployes);
			//selectInstructionEmployes.setInt(1, ligue.getInt(9));
			ResultSet resultEmployes = selectInstructionEmployes.executeQuery(requeteEmployes);
			
			
			String requeteLigues = "select * from ligue where ID_ligue = ?";
			Statement instructionLigues = connection.createStatement();
			ResultSet resultLigues = instructionLigues.executeQuery(requeteLigues);
			
			
			
			while (resultEmployes.next()){
				
				//pour charger une ligue: id et nom et ajouter chaque ligue à la gestion du personnel
				Ligue.addEmploye(
						
						resultEmployes.getString(2), //nom
						resultEmployes.getString(3), //prenom
						resultEmployes.getString(4), //mail
						resultEmployes.getString(5), //password
						resultEmployes.getDate(6), //datearrivee
						resultEmployes.getDate(7), //datedepart
						resultEmployes.getInt(1) //ID
						);
				int ID_employe = resultEmployes.getInt("ID_employe");
				//gestionPersonnel.employe = ligue.addEmploye("ID_employe", "nom", "prenom", "mail", "password", "dateArrive", "dateDepart");
				gestionPersonnel.addRoot(resultRecupRoot.getInt(1), resultRecupRoot.getString(2),resultRecupRoot.getString(5)); 
					
				if(resultRecupRoot = 2) {
						int isAdmin = resultRecupRoot.getInt(ID_employe);
							if( ID_employe = isAdmin){
								Ligue.setAdministrateur(employe);
								}
							}
					resultEmployes.close();
					selectInstructionEmployes.close();
					resultLigues.close();
					instructionLigues.close();
					
				}
	        }
	        }
			
					

				catch (SQLException| SauvegardeImpossible e)
				{
					System.out.println(e);
				}
		return gestionPersonnel;
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
				instruction = connection.prepareStatement("insert into employe (nom,statut) values(?,?)", Statement.RETURN_GENERATED_KEYS);
				instruction.setString(1, employe.getNom());		
				//root=2 admin =1 user simple=0
				//root n'a pas de ligue
				if ( employe.getLigue()== null)
					instruction.setInt(2,2);
				else if ( employe.estAdmin(employe.getLigue())) {
					instruction.setInt(2,1);
				}
				else {
					instruction.setInt(2,0);
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
				instruction = connection.prepareStatement("update ligue set nom=? where ID_ligue=?");
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
						instruction = connection.prepareStatement("update employe set nom=? where ID_employe=?");
						instruction.setString(1, employe.getNom());
						instruction.setInt(2, employe.getId());
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
