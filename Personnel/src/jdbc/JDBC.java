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
			// Requête SQL pour récupérer les employés avec le statut d'administrateur
	        String requete = "SELECT * FROM employe WHERE statut = 1";
	        PreparedStatement selectInstruction = connection.prepareStatement(requete);
	        ResultSet result = selectInstruction.executeQuery();

	        // Vérifier si des employés administrateurs existent dans la base de données
	        if (result.next()) {
	            // Ajouter chaque employé administrateur au gestionnaire du personnel
	            do {
	                gestionPersonnel.addRoot(result.getInt("ID_employe"), result.getString("nom"), result.getString("password"));
	            } while (result.next());
	        } else {
	            // Si aucun employé administrateur n'est trouvé, ajouter un administrateur par défaut
	            gestionPersonnel.addRoot("root", "toor");
	        }
	        
			//requête SQL pour récupérer le password de l'employé:
			String requeteStatut = ("select * from employe where statut=2");
			PreparedStatement selectInstructionStatut = connection.prepareStatement(requeteStatut);
			
	        ResultSet resultStatut = selectInstructionStatut.executeQuery();
	        
	        // Vérifier si l'employé existe dans la base de données
	        if (resultStatut.next()) {
	            // Comparer le mot de passe récupéré avec celui fourni
	               // Si le mot de passe est correct, ajouter l'employé au gestionnaire du personnel
	                gestionPersonnel.addRoot(resultStatut.getInt(1), resultStatut.getString(2),resultStatut.getString(5)); 
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
			            //Employe employe= new employe.gestionPersonnel()
			           
			              
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
				public void updateStatut(Employe employe) {
				    try {
				        // Début de la transaction
				        connection.setAutoCommit(false);
				        
				        // Mettre à jour le statut de l'ancien administrateur
				        String requeteAncienAdmin = "UPDATE employe SET statut = 1 WHERE ID_employe = ?";
				        PreparedStatement updateAncienAdmin = connection.prepareStatement(requeteAncienAdmin);
				        updateAncienAdmin.setInt(1, employe.getStatut()); 
				        updateAncienAdmin.setInt(2, employe.getId());
				        updateAncienAdmin.executeUpdate();
				        
				        // Mettre à jour le statut du nouveau administrateur
				        String requeteNouveauAdmin = "UPDATE employe SET statut = ? WHERE ID_employe = ?";
				        PreparedStatement updateNouveauAdmin = connection.prepareStatement(requeteNouveauAdmin);
				        updateNouveauAdmin.setInt(1, employe.getStatut()); 
				        updateNouveauAdmin.setInt(2, employe.getId());
				        updateNouveauAdmin.executeUpdate();
				        
				        // Valider la transaction
				        connection.commit();
				        
				        // Fin de la transaction
				        connection.setAutoCommit(true);
				        
				        System.out.println("Changement d'administrateur enregistré avec succès.");
				    } catch (SQLException e) {
				        try {
				            // Annuler la transaction en cas d'erreur
				            connection.rollback();
				        } catch (SQLException rollbackError) {
				            rollbackError.printStackTrace();
				        }
				        e.printStackTrace();
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
