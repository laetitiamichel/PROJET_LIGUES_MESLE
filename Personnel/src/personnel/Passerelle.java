package personnel;

import java.sql.Connection;

public interface Passerelle 
{
	public GestionPersonnel getGestionPersonnel();
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel)  throws SauvegardeImpossible;
	public int insert(Ligue ligue) throws SauvegardeImpossible;
	//itération 3:
	public int insert(Employe employe) throws SauvegardeImpossible;
	
}
