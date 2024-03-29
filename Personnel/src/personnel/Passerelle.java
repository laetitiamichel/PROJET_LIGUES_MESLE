package personnel;

public interface Passerelle 
{
	public GestionPersonnel getGestionPersonnel() throws SauvegardeImpossible;
	public void sauvegarderGestionPersonnel(GestionPersonnel gestionPersonnel)  throws SauvegardeImpossible;
	public int insert(Ligue ligue) throws SauvegardeImpossible;
	//itération 3:
	public int insert(Employe employe) throws SauvegardeImpossible;
	//procédure update:
	public void update(Ligue ligue) throws SauvegardeImpossible;
	public void update(Employe employe) throws SauvegardeImpossible;
	public void remove(Employe employe) throws SauvegardeImpossible;
	public void remove(Ligue ligue) throws SauvegardeImpossible;
	
}
