import org.junit.*;
import static org.junit.Assert.*;

/**
 *  Classe de test de la classe Joueur
 */
public class JoueurTest {
	
	private Joueur joueur1;
	private Joueur joueur2;
	
	
	@Before 
	public void setUp() {
		// Construire le joueur
		Joueur1 = new Joueur(1500, 1, "Lucas");
		Joueur2 = new Joueur(1500, 1, "Léa");
	}
	
	@Test
	public void testGetNom() throws Exception {
		// est-ce qu'on teste avec des try pour lever des exceptions?
		assertEquals(joueur1.getNom, "Lucas");
		assertEquals(joueur2.getNom, "Léa");
		
	}
	
	@Test
	public void testGetPos() throws Exception {
		assertEquals(joueur1.getPosition, 1);
		assertEquals(joueur2.getPosition, 1);
	}
	
	@Test
	public void testGetSolde() throws Exception {
		assertEquals(joueur1.getSolde, 1500);
		assertEquals(joueur2.getSolde, 1500);
	}
	
	@Test
	public void testLancerDe() throws Exception {
		nbAlea = joueur1.lancerDe;
		nbAlea2 = joueur2.lancerDe;
		assert(1 <= nbAlea && nbAlea <= 6);
		ssert(1 <= nbAlea2 && nbAlea2 <= 6);
	}
	
	@Test
	public void testAvancer() {
		assertEquals(joueur1.avancer(3), 4);
		assertEquals(joueur2.avancer(1), 2);
		
	}
	
	@Test
	public void testCrediter() {
		assertEquals(joueur1.crediter(200), 1700);
		assertEquals(joueur2.crediter(50), 1550);
		
	}
	
	@Test
	public void testDebiter() {
		assertEquals(joueur1.debiter(700), 1000);
		assertEquals(joueur2.debiter(150), 1400);
		
	}
}