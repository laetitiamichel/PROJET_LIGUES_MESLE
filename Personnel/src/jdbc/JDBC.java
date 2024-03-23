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
			//requête SQL pour récupérer le password de l'employé:
			String requete = ("select * from employe where statut=2");
			PreparedStatement selectInstruction = connection.prepareStatement(requete);
			
	        ResultSet result = selectInstruction.executeQuery();
	        
	        // Vérifier si l'employé existe dans la base de données
	        if (result.next()) {
	            // Comparer le mot de passe récupéré avec celui fourni
	               // Si le mot de passe est correct, ajouter l'employé au gestionnaire du personnel
	                gestionPersonnel.addRoot(result.getInt(1), result.getString(2),result.getString(5)); 
	                // Ajouter l'employé avec son ID,nom et son mot de passe
	           
	        } else {
	        	gestionPersonnel.addRoot("root","toor");
	        }		
			String requete2 = "select * from ligue";
			Statement instruction = connection.createStatement();
			ResultSet ligues = instruction.executeQuery(requete2);
			while (ligues.next())
				//pour charger une ligue: id et nom et ajouter chaque ligue à la gestion du personnel
				gestionPersonnel.addLigue(ligues.getInt(1), ligues.getString(2));
			
			//permet de lire dans la BDD les employés de la ligue et récup les employés de cette ligue
			String requete3 = ("select * from employe where ID_ligue =?");
			PreparedStatement selectInstruction3 = connection.prepareStatement(requete3);
			selectInstruction3.setInt(1,ligues.getInt(1));
			ResultSet result3 = selectInstruction3.executeQuery();
			
					// Parcourir les ligues
			        while (result3.next())
			            // Ajouter chaque ligue à la gestion du personnel
			            gestionPersonnel.addLigue(ligues.getInt("ID_ligue"), ligues.getString("nomLigue"));

			            // Requête SQL pour récupérer les employés de cette ligue
			            String requeteEmployes = "SELECT * FROM employe WHERE ID_ligue = ?";
			            PreparedStatement selectInstructionEmployes = connection.prepareStatement(requeteEmployes);
			            selectInstructionEmployes.setInt(1, ligues.getInt("ID_ligue"));
			            ResultSet employes = selectInstructionEmployes.executeQuery();
			            Employe employe = new employe.gestionPersonnel()
			           
			              
			         while (employes.next());
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
				instruction = connection.prepareStatement("update ligue set nom=? where id=?");
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
						instruction = connection.prepareStatement("update employe set nom=? where id=?");
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
						instruction = connection.prepareStatement("delete from employe where id=?");
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
						instruction = connection.prepareStatement("delete from ligue where id=?");
						instruction = connection.prepareStatement("delete from employe where id=?");
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
