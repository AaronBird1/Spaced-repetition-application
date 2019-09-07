package org.openjfx.spacedrepetition;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.mindrot.jbcrypt.BCrypt;

public class Db {
	private Scanner scan = new Scanner(System.in);
	private String url = "jdbc:mysql://localhost:3306/spacedrep";
	private String username = "student";
	private String password = "pass123";
    
	public void startDb() {
		System.out.println("startDb method in Db class has been called!");
		
		try (Connection c = DriverManager.getConnection(url, username, password)) {
		    System.out.println("Database connected!");
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
    
	public void pickDeck(Connection conn) {
		System.out.println("Do we reach this method???");
		final String sql = "SELECT id FROM Deck WHERE name = ?";
		
		System.out.println("Name the deck you want to study: ");
		String answer = scan.nextLine();
		
		try(PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setString(1, answer);
			ResultSet rs = ps.executeQuery();
		    if(rs.next()){
		    	System.out.println("The id of the deck is: " + rs.getString("id"));
		    }
		    else {
		    	System.out.println("There exists no deck with that name.");
		    }
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//returns a deck object retrieving the correct information from the database for the deckId inputed
	public Deck returnDeck(int id) {
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String username = "student";
		String password = "pass123";
		
		Deck newDeck = new Deck("deckName", -1.0, -1);
		
		System.out.println("returnDeckList method activated...");
		
		final String sql = "SELECT Deck.name, Deck.owner FROM Deck WHERE Deck.id";
		
		try (Connection c = DriverManager.getConnection(url, username, password)) {
		    System.out.println("Database connected!");
		    
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				System.out.println("test point 1");
			    if(rs.next()){
			    	System.out.println("test point 2");
			    	newDeck = new Deck(rs.getString("name"), rs.getDouble("id"), rs.getInt("owner"));
			    	return newDeck;
			    }
			}catch(SQLException e){
				e.printStackTrace();
			}
		    
		    //c.close();
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
		
		return newDeck;
	}
	
	//DO I NEED THIS ANYMORE?? WHAT IS IT FOR??
	public void returnDecks(Connection conn) {
		List<String> deckNames = new ArrayList<String>();
		
		System.out.println("returnDecks method activated...");
		
		System.out.println("The size of the deck is " + deckNames.size());
		deckNames.add("Aaron's Deck");
		System.out.println("The size of the deck is " + deckNames.size());
    	System.out.println(deckNames.get(deckNames.size() - 1));
		
		final String sql = "SELECT name FROM Deck";
		
		try(PreparedStatement ps = conn.prepareStatement(sql)){
			ResultSet rs = ps.executeQuery();
			System.out.println("test point 1");
		    while(rs.next()){
		    	System.out.println("test point 2");
		    	deckNames.add(rs.getString("name"));
		    	System.out.println(deckNames.get(deckNames.size() - 1));
		    }
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//returns an arrayList of deck objects from the database that are owned by the user with the userId inputed
	public ArrayList<Deck> returnDeckList(int userId) {
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String username = "student";
		String password = "pass123";
		
		ArrayList<Deck> deckList = new ArrayList<Deck>();
		
		System.out.println("returnDeckList method activated...");
		
		final String sql = "SELECT Deck.id, Deck.name, Deck.description, Deck.deadline FROM Deck WHERE Deck.owner = ?";
		
		try (Connection c = DriverManager.getConnection(url, username, password)) {
		    System.out.println("Database connected!");
		    
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	ps.setInt(1, userId);
				ResultSet rs = ps.executeQuery();
				System.out.println("test point 1");
			    while(rs.next()){
			    	System.out.println("test point 2");
			    	Deck newDeck = new Deck(rs.getDouble("id"), rs.getString("name"), rs.getString("description"), rs.getDate("deadline"), userId);
			    	deckList.add(newDeck);
			    }
			}catch(SQLException e){
				e.printStackTrace();
			}
		    
		    //c.close();
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
		
		return deckList;
	}
	
	//returns the description of the deck from the database with the inputted id
	public String returnDeckInfo(int deckId) {
		System.out.println("returnDeckInfo method in Db class has been activated...");
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String username = "student";
		String password = "pass123";
		
		final String sql = "SELECT Deck.description FROM Deck WHERE Deck.id = ?";
		
		try (Connection c = DriverManager.getConnection(url, username, password)) {
		    System.out.println("Database connected!");
		    
		    System.out.println("Trying to find info for deck with id " + deckId);
		    
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	System.out.println("test point 0");
		    	ps.setInt(1, deckId);
		    	System.out.println("test point 0.5");
				ResultSet rs = ps.executeQuery();
				System.out.println("test point 1");
			    if(rs.next()){
			    	System.out.println("Deck with id " + deckId + " has been found!");
			    	System.out.println("Description: " + rs.getString("description"));
			    	return rs.getString("description");
			    }
			}catch(SQLException e){
				e.printStackTrace();
			}
		    
		    return "This deck does not exist";
		    //c.close();
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	//returns the name of the deck from the database with the inputted id
	public String returnDeckName(int deckId) {
		System.out.println("returnDeckName method in Db class has been activated...");
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String username = "student";
		String password = "pass123";
		
		final String sql = "SELECT Deck.name FROM Deck WHERE Deck.id = ?";
		
		try (Connection c = DriverManager.getConnection(url, username, password)) {
		    System.out.println("Database connected!");
		    
		    System.out.println("Trying to find info for deck with id " + deckId);
		    
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	System.out.println("test point 0");
		    	ps.setInt(1, deckId);
		    	System.out.println("test point 0.5");
				ResultSet rs = ps.executeQuery();
				System.out.println("test point 1");
			    if(rs.next()){
			    	System.out.println("Deck with id " + deckId + " has been found!");
			    	return rs.getString("name");
			    }
			}catch(SQLException e){
				e.printStackTrace();
			}
		    
		    return "This deck does not exist";
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	//returns an arrayList of card objects from the database that are from the deck with the inputted id
	public ArrayList<Card> returnCards(int deckId) {
		System.out.println("returnCards method in Db class has been activated...");
		
		ArrayList<Card> cardList = new ArrayList<Card>();
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String username = "student";
		String password = "pass123";
		
		final String sql = "SELECT Card.id, Card.question, Card.answer, Card.deckId, Card.revisionDate, Card.level, Card.numTimesStudied, numTimesRemembered, daysAdjustment FROM Card WHERE Card.deckId = ?";
		
		try (Connection c = DriverManager.getConnection(url, username, password)) {
		    System.out.println("Database connected!");
		    
		    System.out.println("Trying to find cards for deck with id " + deckId);
		    
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	System.out.println("test point 0");
		    	ps.setInt(1, deckId);
		    	System.out.println("test point 0.5");
				ResultSet rs = ps.executeQuery();
				System.out.println("test point 1");
			    while(rs.next()){
			    	Card newCard = new Card(rs.getInt("id"), rs.getString("question"), rs.getString("answer"), rs.getInt("deckId"), rs.getDate("revisionDate"), rs.getInt("level"), rs.getInt("numTimesStudied"), rs.getInt("numTimesRemembered"), rs.getInt("daysAdjustment"));
			    	cardList.add(newCard);
			    }
			    return cardList;
			}catch(SQLException e){
				e.printStackTrace();
			}
		    return cardList;
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	//updates the row of card table in the database which has the inputted id with the new inputted deadline date
	public void updateCardDate(int cardId, java.sql.Date newDate) {
		System.out.println("updateCardDate method in Db class has been activated...");
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String username = "student";
		String password = "pass123";
		
		final String sql = "UPDATE Card SET revisionDate = ? WHERE Card.id = ?";
		
		try (Connection c = DriverManager.getConnection(url, username, password)) {
		    System.out.println("Database connected!");
		 
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	System.out.println("test point 0");
		    	ps.setDate(1, newDate);
		    	ps.setInt(2, cardId);
		    	System.out.println("test point 1");
		    	ps.executeUpdate();
			}catch(SQLException e){
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	//updates the row of card table in the database which has the inputted id with the new inputted card level
	public void updateCardLevel(int cardId, int newLevel) {
		System.out.println("updateCardLevel method in Db class has been activated...");
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String username = "student";
		String password = "pass123";
		
		final String sql = "UPDATE Card SET level = ? WHERE Card.id = ?";
		
		try (Connection c = DriverManager.getConnection(url, username, password)) {
		    System.out.println("Database connected!");
		 
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	System.out.println("test point 0");
		    	ps.setInt(1, newLevel);
		    	ps.setInt(2, cardId);
		    	System.out.println("test point 1");
		    	ps.executeUpdate();
			}catch(SQLException e){
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	//creates a new card in the database with the inputted information
	public void createNewCard(String newQuestion, String newAnswer, int newDeckId) {
		System.out.println("createNewCard method in Db class has been activated...");
		
		LocalDate todaysDate = LocalDate.now();
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String username = "student";
		String password = "pass123";
		
		final String sql = "INSERT INTO Card (question, answer, revisionDate, deckId, level, numTimesStudied, numTimesRemembered, daysAdjustment) VALUES (?, ?, ?, ?, 0, 0, 0, 0)";
		
		try (Connection c = DriverManager.getConnection(url, username, password)) {
		    System.out.println("Database connected!");
		 
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	System.out.println("test point 0");
		    	ps.setString(1, newQuestion);
		    	ps.setString(2, newAnswer);
		    	ps.setDate(3, java.sql.Date.valueOf(todaysDate));
		    	ps.setInt(4, newDeckId);
		    	System.out.println("test point 1");
		    	ps.executeUpdate();
			}catch(SQLException e){
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	//creates a new card in the database that is used for some of the unit testing
	public void createNewTestCard(String newQuestion, String newAnswer, int newDeckId, int newCardId) {
		System.out.println("createNewCard method in Db class has been activated...");
		
		LocalDate todaysDate = LocalDate.now();
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String username = "student";
		String password = "pass123";
		
		final String sql = "INSERT INTO Card (question, answer, revisionDate, deckId, level, numTimesStudied, numTimesRemembered, daysAdjustment, id) VALUES (?, ?, ?, ?, 0, 0, 0, 0, ?)";
		
		try (Connection c = DriverManager.getConnection(url, username, password)) {
		    System.out.println("Database connected!");
		 
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	System.out.println("test point 0");
		    	ps.setString(1, newQuestion);
		    	ps.setString(2, newAnswer);
		    	ps.setDate(3, java.sql.Date.valueOf(todaysDate));
		    	ps.setInt(4, newDeckId);
		    	ps.setInt(5, newCardId);
		    	System.out.println("test point 1");
		    	ps.executeUpdate();
			}catch(SQLException e){
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	//creates a new deck in the database
	public void createNewDeck(String newDeckName, String newDeckDescription, int userId, LocalDate deadline) {
		System.out.println("createNewDeck method id Db class has been activated...");
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String username = "student";
		String password = "pass123";
		
		final String sql = "INSERT INTO Deck (name, description, owner, deadline) VALUES (?, ?, ?, ?)";
		
		try (Connection c = DriverManager.getConnection(url, username, password)) {
		    System.out.println("Database connected!");
		 
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	System.out.println("test point 0");
		    	ps.setString(1, newDeckName);
		    	ps.setString(2, newDeckDescription);
		    	ps.setInt(3, userId);
		    	if(deadline != null) {
		    		ps.setDate(4, java.sql.Date.valueOf(deadline));
		    	}
		    	else {
		    		ps.setDate(4, null);
		    	}
		    	System.out.println("test point 1");
		    	ps.executeUpdate();
			}catch(SQLException e){
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	//creates a new deck in the database that is used for some of the unit testing
	public void createNewTestDeck(String newDeckName, String newDeckDescription, int userId, LocalDate deadline, int newDeckId) {
		System.out.println("createNewTestDeck method id Db class has been activated...");
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String username = "student";
		String password = "pass123";
		
		final String sql = "INSERT INTO Deck (name, description, owner, deadline, id) VALUES (?, ?, ?, ?, ?)";
		
		try (Connection c = DriverManager.getConnection(url, username, password)) {
		    System.out.println("Database connected!");
		 
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	System.out.println("test point 0");
		    	ps.setString(1, newDeckName);
		    	ps.setString(2, newDeckDescription);
		    	ps.setInt(3, userId);
		    	ps.setInt(5, newDeckId);
		    	if(deadline != null) {
		    		ps.setDate(4, java.sql.Date.valueOf(deadline));
		    	}
		    	else {
		    		ps.setDate(4, null);
		    	}
		    	System.out.println("test point 1");
		    	ps.executeUpdate();
			}catch(SQLException e){
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	//updates the row of the deck table in the database that has the inputted id with the new name inputted
	public void editDeckName(String newDeckName, int deckId) {
		System.out.println("editDeckName method in Db class has been activated...");
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String username = "student";
		String password = "pass123";
		
		final String sql = "UPDATE Deck SET name = ? WHERE Deck.id = ?";
		
		try (Connection c = DriverManager.getConnection(url, username, password)) {
		    System.out.println("Database connected!");
		 
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	System.out.println("test point 0");
		    	ps.setString(1, newDeckName);
		    	ps.setInt(2, deckId);
		    	System.out.println("test point 1");
		    	ps.executeUpdate();
			}catch(SQLException e){
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	//updates the row of the deck table in the database that has the inputted id with the new description inputted
	public void editDeckDescription(String newDeckDescription, int deckId) {
		System.out.println("editDeckDescription method in Db class has been activated...");
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String username = "student";
		String password = "pass123";
		
		final String sql = "UPDATE Deck SET description = ? WHERE Deck.id = ?";
		
		try (Connection c = DriverManager.getConnection(url, username, password)) {
		    System.out.println("Database connected!");
		 
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	System.out.println("test point 0");
		    	ps.setString(1, newDeckDescription);
		    	ps.setInt(2, deckId);
		    	System.out.println("test point 1");
		    	ps.executeUpdate();
			}catch(SQLException e){
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	//returns the number of cards that have a study date that matches todays date
	public int getNumCardsToStudyInDeck(Double deckId) {
		System.out.println("getNumCardsToStudyInDeck method in Db class has been activated...");
		
		int count = 0;
		LocalDate todaysDate = LocalDate.now();
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String username = "student";
		String password = "pass123";
		
		final String sql = "SELECT Card.id, Card.revisionDate FROM Card WHERE Card.deckId = ?";
		
		try (Connection c = DriverManager.getConnection(url, username, password)) {
		    System.out.println("Database connected!");
		 
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	System.out.println("test point 0");
		    	ps.setDouble(1, deckId);
		    	ResultSet rs = ps.executeQuery();
		    	System.out.println("test point 1");
		    	while(rs.next()) {
		    		if(rs.getDate("revisionDate").toLocalDate().isBefore(todaysDate)) {
		    			updateCardDate(rs.getInt("id"), java.sql.Date.valueOf(todaysDate));
		    			count++;
		    		}
		    		else if(rs.getDate("revisionDate").toLocalDate().equals(todaysDate)) {
		    			count++;
		    		}
		    	}
		    	return count;
			}catch(SQLException e){
				e.printStackTrace();
			}
		    return count;
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	//checks whether the inputted username and password match any username and password combinations in the database
	public Boolean checkLoginInfo(String username, String password) {
		System.out.println("checkLoginInfo method in Db class has been activated...");
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String uname = "student";
		String pword = "pass123";
		
		final String sql = "SELECT User.password FROM User WHERE User.username = ?";
		
		try (Connection c = DriverManager.getConnection(url, uname, pword)) {
		    System.out.println("Database connected!");
		 
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	System.out.println("test point 0");
		    	ps.setString(1, username);
		    	ResultSet rs = ps.executeQuery();
		    	System.out.println("test point 1");
		    	if(rs.next()) {
		    		if(BCrypt.checkpw(password, rs.getString("password"))) {
		    			return true;
		    		}
		    		else {
		    			return false;
		    		}
		    	}
		    	else {
		    		return false;
		    	}
			}catch(SQLException e){
				e.printStackTrace();
			}
		    return false;
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	//creates a new row in the user table with the username and password inputted here, note encryption is not done for the password here
	public void createNewUser(String newUsername, String newPassword) {
		System.out.println("createNewUser method in Db class has been activated...");
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String uname = "student";
		String pword = "pass123";
		
		final String sql = "INSERT INTO User (username, password) VALUES (?, ?)";
		
		if(!newUsername.isBlank() && !newPassword.isBlank()) {
			try (Connection c = DriverManager.getConnection(url, uname, pword)) {
			    System.out.println("Database connected!");
			 
			    try(PreparedStatement ps = c.prepareStatement(sql)){
			    	System.out.println("test point 0");
			    	ps.setString(1, newUsername);
			    	ps.setString(2, newPassword);
			    	System.out.println("test point 1");
			    	ps.executeUpdate();
				}catch(SQLException e){
					e.printStackTrace();
				}
			} catch (SQLException e) {
				System.out.println("SQLException has occured!");
			    throw new IllegalStateException("Cannot connect the database!", e);
			}
		}
		else {
			System.out.println("New user cannot be created in db table, either username or password is blank");
		}
	}
	
	//returns the user id from the database user table that has the inputted username
	public int returnUserId(String username) {
		System.out.println("returnUserId method in Db class has been activated...");
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String uname = "student";
		String pword = "pass123";
		
		final String sql = "SELECT User.id FROM User WHERE User.username = ?";
		
		try (Connection c = DriverManager.getConnection(url, uname, pword)) {
		    System.out.println("Database connected!");
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	System.out.println("test point 0");
		    	ps.setString(1, username);
		    	ResultSet rs = ps.executeQuery();
		    	System.out.println("test point 1");
		    	if(rs.next()) {
		    		return rs.getInt("id");
		    	}
			}catch(SQLException e){
				e.printStackTrace();
			}
		    return -1;
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}	    
	}
	
	//returns the username from the user table of the database that has an id that matches the one inputted
	public String returnUsername(int userId) {
		System.out.println("returnUsername method in Db class has been activated...");
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String uname = "student";
		String pword = "pass123";
		
		final String sql = "SELECT User.username FROM User WHERE User.id = ?";
		
		try (Connection c = DriverManager.getConnection(url, uname, pword)) {
		    System.out.println("Database connected!");
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	System.out.println("test point 0");
		    	ps.setInt(1, userId);
		    	ResultSet rs = ps.executeQuery();
		    	System.out.println("test point 1");
		    	if(rs.next()) {
		    		return rs.getString("username");
		    	}
			}catch(SQLException e){
				e.printStackTrace();
			}
		    return "username not found";
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	//deletes the row from the card table that has the inputted id
	public void deleteCard(int cardId) {
		System.out.println("deleteCard method in Db class has been activated...");
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String uname = "student";
		String pword = "pass123";
		
		final String sql = "DELETE FROM Card WHERE Card.id = ?";
		
		try (Connection c = DriverManager.getConnection(url, uname, pword)) {
		    System.out.println("Database connected!");
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	System.out.println("test point 0");
		    	ps.setInt(1, cardId);
		    	ps.executeUpdate();
		    	System.out.println("test point 1");
			}catch(SQLException e){
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	//deletes the row from the deck table that has the inputted id
	public void deleteDeck(int deckId) {
		System.out.println("deleteDeck method in Db class has been activated...");
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String uname = "student";
		String pword = "pass123";
		
		final String sql = "DELETE FROM Deck WHERE Deck.id = ?";
		final String sql2 = "DELETE FROM Card WHERE Card.deckId = ?";
		
		try (Connection c = DriverManager.getConnection(url, uname, pword)) {
		    System.out.println("Database connected!");
		    try(PreparedStatement ps = c.prepareStatement(sql2)){
		    	System.out.println("test point 0");
		    	ps.setInt(1, deckId);
		    	ps.executeUpdate();
		    	System.out.println("test point 1");
			}catch(SQLException e){
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
		
		try (Connection c = DriverManager.getConnection(url, uname, pword)) {
		    System.out.println("Database connected!");
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	System.out.println("test point 0");
		    	ps.setInt(1, deckId);
		    	ps.executeUpdate();
		    	System.out.println("test point 1");
			}catch(SQLException e){
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	//returns the deadline Date from the row of the deck table that has the deck id inputted
	public Date returnDeckDeadline(int deckId) {
		System.out.println("checkDeckDeadline method in Db class has been activated...");
		
		Date deckDate = null;
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String uname = "student";
		String pword = "pass123";
		
		final String sql = "SELECT Deck.deadline FROM Deck WHERE Deck.id = ?";
		
		try (Connection c = DriverManager.getConnection(url, uname, pword)) {
		    System.out.println("Database connected!");
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	System.out.println("test point 0");
		    	ps.setInt(1, deckId);
		    	ResultSet rs = ps.executeQuery();
		    	System.out.println("test point 1");
		    	if(rs.next()) {
		    		deckDate = rs.getDate("deadline");
		    		return deckDate;
		    	}
			}catch(SQLException e){
				e.printStackTrace();
			}
		    
		    return deckDate;
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	//sets the deadline to NULL of the row of the deck table that has the deck id inputted
	public void removeDeckDeadline(int deckId) {
		System.out.println("removeDeckDeadline method in Db class has been activated...");
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String uname = "student";
		String pword = "pass123";
		
		final String sql = "UPDATE Deck SET deadline = NULL WHERE Deck.id = ?";
		
		try (Connection c = DriverManager.getConnection(url, uname, pword)) {
		    System.out.println("Database connected!");
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	System.out.println("test point 0");
		    	ps.setInt(1, deckId);
		    	ps.executeUpdate();
			}catch(SQLException e){
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	//increases the numTimesCardStudied value by 1 of the row of the card table that has the card id inputted
	public void increaseNumTimesCardStudied(int cardId) {
		System.out.println("increaseNumTimesCardStudied method in Db class has been activated...");
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String uname = "student";
		String pword = "pass123";
		
		final String sql = "UPDATE Card SET numTimesStudied = numTimesStudied + 1 WHERE Card.id = ?";
		
		try (Connection c = DriverManager.getConnection(url, uname, pword)) {
		    System.out.println("Database connected!");
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	System.out.println("test point 0");
		    	ps.setInt(1, cardId);
		    	ps.executeUpdate();
			}catch(SQLException e){
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	//increases the numTimesRemembered value by 1 of the row of the card table that has the card id inputted
	public void increaseNumTimesCardRemembered(int cardId) {
		System.out.println("increaseNumTimesCardRemembered method in Db class has been activated...");
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String uname = "student";
		String pword = "pass123";
		
		final String sql = "UPDATE Card SET numTimesRemembered = numTimesRemembered + 1 WHERE Card.id = ?";
		
		try (Connection c = DriverManager.getConnection(url, uname, pword)) {
		    System.out.println("Database connected!");
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	System.out.println("test point 0");
		    	ps.setInt(1, cardId);
		    	ps.executeUpdate();
			}catch(SQLException e){
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	//increases the daysAdjustment value by 1 of the row of the card table that has the card id inputted
	public void increaseCardDaysAdjustment(int cardId, boolean status) {
		System.out.println("increaseCardDaysAdjustment method in Db class has been activated...");
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String uname = "student";
		String pword = "pass123";
		
		if(status == true) {
			final String sql = "UPDATE Card SET daysAdjustment = daysAdjustment + 1 WHERE Card.id = ?";
			
			try (Connection c = DriverManager.getConnection(url, uname, pword)) {
			    System.out.println("Database connected!");
			    try(PreparedStatement ps = c.prepareStatement(sql)){
			    	System.out.println("test point 0");
			    	ps.setInt(1, cardId);
			    	ps.executeUpdate();
				}catch(SQLException e){
					e.printStackTrace();
				}
			} catch (SQLException e) {
				System.out.println("SQLException has occured!");
			    throw new IllegalStateException("Cannot connect the database!", e);
			}
		}
		else {
			final String sql = "UPDATE Card SET daysAdjustment = daysAdjustment - 1 WHERE Card.id = ?";
			
			try (Connection c = DriverManager.getConnection(url, uname, pword)) {
			    System.out.println("Database connected!");
			    try(PreparedStatement ps = c.prepareStatement(sql)){
			    	System.out.println("test point 0");
			    	ps.setInt(1, cardId);
			    	ps.executeUpdate();
				}catch(SQLException e){
					e.printStackTrace();
				}
			} catch (SQLException e) {
				System.out.println("SQLException has occured!");
			    throw new IllegalStateException("Cannot connect the database!", e);
			}
		}
		
	}
	
	//creates a new row in the user table that will be used for some unit testing
	public void createNewTestUser(String newUsername, String newPassword, int newUserId) {
		System.out.println("createNewTestUser method in Db class has been activated...");
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String uname = "student";
		String pword = "pass123";
		
		final String sql = "INSERT INTO User (username, password, id) VALUES (?, ?, ?)";
		
		if(!newUsername.isBlank() && !newPassword.isBlank()) {
			try (Connection c = DriverManager.getConnection(url, uname, pword)) {
			    System.out.println("Database connected!");
			 
			    try(PreparedStatement ps = c.prepareStatement(sql)){
			    	System.out.println("test point 0");
			    	ps.setString(1, newUsername);
			    	ps.setString(2, newPassword);
			    	ps.setInt(3, newUserId);
			    	System.out.println("test point 1");
			    	ps.executeUpdate();
				}catch(SQLException e){
					e.printStackTrace();
				}
			} catch (SQLException e) {
				System.out.println("SQLException has occured!");
			    throw new IllegalStateException("Cannot connect the database!", e);
			}
		}
		else {
			System.out.println("New user cannot be created in db table, either username or password is blank");
		}
	}
	
	//returns a card object that has information matching the row of the card table in the database that has the card id inputted
	public Card returnCard(int cardId) {
		System.out.println("returnCard method in Db class has been activated...");
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String username = "student";
		String password = "pass123";
		
		Card newCard;
		
		final String sql = "SELECT Card.id, Card.question, Card.answer, Card.deckId, Card.revisionDate, Card.level, Card.numTimesStudied, numTimesRemembered, daysAdjustment FROM Card WHERE Card.id = ?";
		
		try (Connection c = DriverManager.getConnection(url, username, password)) {
		    System.out.println("Database connected!");
		    
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	System.out.println("test point 0");
		    	ps.setInt(1, cardId);
		    	System.out.println("test point 0.5");
				ResultSet rs = ps.executeQuery();
				System.out.println("test point 1");
				if(rs.next()) {
					newCard = new Card(rs.getInt("id"), rs.getString("question"), rs.getString("answer"), rs.getInt("deckId"), rs.getDate("revisionDate"), rs.getInt("level"), rs.getInt("numTimesStudied"), rs.getInt("numTimesRemembered"), rs.getInt("daysAdjustment"));
				    return newCard;
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		    //shouldn't reach this point, so if it does creates a card that can't exist otherwise so I know it has
		    newCard = new Card(-2, "n/a", "n/a", -2, java.sql.Date.valueOf(LocalDate.now()), -2, -2, -2, -2);
		    return newCard;
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	//updates the row of the card table in the database with all the information of the card object inputted (assuming a row has an id matching that of the card object)
	public void updateCardData(Card newCard) {
		System.out.println("updateCardData method in Db class has been activated...");
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String username = "student";
		String password = "pass123";
		
		final String sql = "UPDATE Card SET question = ?, answer = ?, revisionDate = ?, deckId = ?, level = ?, numTimesStudied = ?, numTimesRemembered = ?, daysAdjustment = ? WHERE Card.id = ?";
		
		try (Connection c = DriverManager.getConnection(url, username, password)) {
		    System.out.println("Database connected!");
		 
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	System.out.println("test point 0");
		    	ps.setString(1, newCard.getQuestion());
		    	ps.setString(2, newCard.getAnswer());
		    	ps.setDate(3, newCard.getStudyDate());
		    	ps.setInt(4, newCard.getDeckId());
		    	ps.setInt(5, newCard.getLevel());
		    	ps.setInt(6, newCard.getNumTimesStudied());
		    	ps.setInt(7,  newCard.getNumTimesRemembered());
		    	ps.setInt(8,  newCard.getDaysAdjustment());
		    	ps.setInt(9,  newCard.getId());
		    	System.out.println("test point 1");
		    	ps.executeUpdate();
			}catch(SQLException e){
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	//updates the deadline of the row, that has the inputted id, of the deck table in the database with the inputted deadline
	public void updateDeckDeadline(int deckId, Date newDeadline) {
		System.out.println("updateCardData method in Db class has been activated...");
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String username = "student";
		String password = "pass123";
		
		final String sql = "UPDATE Deck SET deadline = ? WHERE Deck.id = ?";
		
		try (Connection c = DriverManager.getConnection(url, username, password)) {
		    System.out.println("Database connected!");
		 
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	System.out.println("test point 0");
		    	ps.setDate(1, newDeadline);
		    	ps.setInt(2, deckId);
		    	System.out.println("test point 1");
		    	ps.executeUpdate();
			}catch(SQLException e){
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
}
