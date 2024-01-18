package personnel;

import java.time.LocalDate;

/**
 * Exception pour les erreurs de dates.
 */

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

public class erreursDates extends Exception
{
	public boolean datesIncoherantes(LocalDate dateArrivee, LocalDate dateDepart) throws IllegalArgumentException {
	    if (dateArrivee.isAfter(dateDepart)) {
	        throw new IllegalArgumentException("La date d'arrivée doit être antérieure à la date de départ.");
	    }
	    return true;
	}
	public erreursDates(Exception exception)
	{
		this.erreursDates = exception;
	}
	
	@Override
	public static void printStackTrace() 
	{
			super.printStackTrace();
			System.err.println("Causé par : ");
			erreursDates.printStackTrace();			
	}
}
