import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.openjfx.spacedrepetition.Card;
import org.openjfx.spacedrepetition.Deck;
 
class deckTests {
	
	LocalDate todaysDate = LocalDate.now();
 
    @Test
    void getMethodsTest() {
    	Double testDeckId = 16.0;
    	String testDeckName = "Test Deck Name";
		String testDeckDescription = "Test Deck Description";
		java.sql.Date testDeckDate = java.sql.Date.valueOf(LocalDate.of(1950, 12, 25));
		int testDeckOwner = -1;
				
		Deck testDeck = new Deck(testDeckId, testDeckName, testDeckDescription, testDeckDate, testDeckOwner);
		
		assert(testDeck.getDeckName().contentEquals("Test Deck Name"));
		assert(testDeck.getDeckId() == 16.0);
		assert(testDeck.getDeckOwner() == -1);
		assert(testDeck.getDeckDescription() == "Test Deck Description");
		assert(testDeck.getDeckDeadline().toLocalDate().compareTo(LocalDate.of(1950, 12, 25)) == 0);
		
		System.out.println("ALL TESTS FOR getMethodsTest IN deckTests PASSED!");
    }
    
    @Test
    void testSetGetCards() {
    	Deck testingDeck = new Deck("Test Deck", 12.0, 5);
    	
    	ArrayList<Card> cards1 = new ArrayList<Card>();
    	
    	Card card1 = new Card(1, "Question number one", "Answer number one", 12, java.sql.Date.valueOf(todaysDate), 66);
    	Card card2 = new Card(2, "Question number two", "Answer number two", 13, java.sql.Date.valueOf(todaysDate), 67);
    	Card card3 = new Card(3, "Question number three", "Answer number three", 14, java.sql.Date.valueOf(todaysDate), 68);
    	
    	cards1.add(card1);
    	cards1.add(card2);
    	cards1.add(card3);
    	
    	testingDeck.setCards(cards1);
    	
    	ArrayList<Card> cards2 = testingDeck.getCards();
    	
    	assert(cards2.get(2).getQuestion() == "Question number three");
    	assert(cards2.get(0).getQuestion() == "Question number one");
    	assert(cards2.get(1).getQuestion() == "Question number two");	
    	
    	System.out.println("ALL TESTS FOR testSetCards IN deckTests PASSED!");
    }
    
    @Test
    void testSetDeckName() {
    	Deck testingDeck = new Deck("Test Deck", 12.0, 5);
    	
    	assert(testingDeck.getDeckName() == "Test Deck");
    	
    	testingDeck.setDeckName("This is the new Deck Name");
    	
    	assert(testingDeck.getDeckName() == "This is the new Deck Name");
    	
    	System.out.println("ALL TESTS FOR testSetDeckName IN deckTests PASSED!");
    }
    
    @Test
    void testSetDeckDescription() {
    	Deck testingDeck = new Deck(5.0, "Test deck name", "Test deck description", java.sql.Date.valueOf(todaysDate), 3);
    	
    	assert(testingDeck.getDeckDescription() == "Test deck description");
    	
    	testingDeck.setDeckDescription("This is a new deck description!");
    	
    	assert(testingDeck.getDeckDescription() == "This is a new deck description!");
    }
    
}