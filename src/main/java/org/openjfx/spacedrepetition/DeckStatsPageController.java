package org.openjfx.spacedrepetition;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

public class DeckStatsPageController implements Initializable {
	
	private static int num;
	private static Deck deck;
	private CategoryAxis xAxis = new CategoryAxis();
	private NumberAxis yAxis = new NumberAxis();
	@FXML private BarChart cardLevelChart = new BarChart(xAxis, yAxis);
	private CategoryAxis xAxis2 = new CategoryAxis();
	private NumberAxis yAxis2 = new NumberAxis();
	@FXML private BarChart cardsToStudyChart = new BarChart(xAxis, yAxis);
	@FXML private Button switchButton;
	@FXML private Button switchButton2;
	@FXML private Button backButton;
	@FXML private Button homeButton;
	
	//switches to the home page
	@FXML
    private void switchToHome() throws IOException {
    	System.out.println("\n\nswitchToHome method in DeckStatsPageController class has been activated...\n\n");
        App.setRoot("primary");
    }
	
	//switches back to the deck page
	@FXML
    private void backPage() throws IOException {
    	System.out.println("\n\nbackPage method in DeckStatsPageController class has been activated...\n\n");
        App.setRoot("deckpage");
    }
	
	@Override
    public void initialize(final URL url, final ResourceBundle rb) {
    	System.out.println("initialize method in DeckStatsPageController has been activated...");
    	System.out.println("The id of the deck is: " + num);
    	getNumCardsInEachLevel(num);
    	setupNumCardsToStudyChart();
    	cardsToStudyChart.setVisible(false);
    	cardLevelChart.setVisible(true);
    	switchButton.setVisible(true);
		switchButton2.setVisible(false);
		
		setupButtons();
    }
	
	//sets up the custom buttons for the page
	public void setupButtons() {
		CustomButton cb1 = new CustomButton(homeButton, "home");
		CustomButton cb2 = new CustomButton(backButton, "back");
	}
	
	//sets the num variable to the new inputted int
	public void setNum(int newnum) {
		System.out.println("setNum method in DeckStatsPageController class has been activated...");
		System.out.println("The new num is: " + newnum);
		num = newnum;
	}
	
	//sets the deck variable to the new inputted deck
	public void setDeck(Deck newDeck) {
		System.out.println("setDeck method in DeckStatsPageController class has been activated...");
		deck = newDeck;
	}
	
	//add the data of how many cards are in each level to the chart
	public void getNumCardsInEachLevel(int deckId) {
		System.out.println("getNumCardsInEachLevel method in DeckStatsPageController class has been activated...");
		
		XYChart.Series series = new XYChart.Series();
		
		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String uname = "student";
		String pword = "pass123";
		
		final String sql = "SELECT Card.level, COUNT(Card.level) AS 'levelCount' FROM Card WHERE Card.deckId = ? GROUP BY Card.level";
		
		try (Connection c = DriverManager.getConnection(url, uname, pword)) {
		    System.out.println("Database connected!");
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	System.out.println("test point 0");
		    	ps.setInt(1, deckId);
		    	ResultSet rs = ps.executeQuery();
		    	System.out.println("test point 1");
		    	while(rs.next()) {
		    		System.out.println("The number of cards in level" + rs.getInt("level") + "is: " + rs.getInt("levelCount"));
					series.getData().add(new XYChart.Data(Integer.toString(rs.getInt("level")), rs.getInt("levelCount")));
		    	}
		    	cardLevelChart.getData().add(series);
			}catch(SQLException e){
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	//adds the data of how many cards are to be studied on which days to the chart
	private void setupNumCardsToStudyChart() {
		System.out.println("setupNumCardsToStudyChart method in DeckStatsPageController class has been activated...");
		
		LocalDate todaysDate = LocalDate.now();
		
		XYChart.Series series2 = new XYChart.Series();

		String url = "jdbc:mysql://localhost:3306/spacedrep";
		String uname = "student";
		String pword = "pass123";
		
		final String sql = "SELECT card.revisionDate, COUNT(Card.revisionDate) AS 'dateCount' FROM Card WHERE Card.deckId = ? GROUP BY Card.revisionDate";
		
		try (Connection c = DriverManager.getConnection(url, uname, pword)) {
		    System.out.println("Database connected!");
		    try(PreparedStatement ps = c.prepareStatement(sql)){
		    	System.out.println("test point 0");
		    	ps.setInt(1, num);
		    	ResultSet rs = ps.executeQuery();
		    	System.out.println("test point 1");
		    	while(rs.next()) {
					series2.getData().add(new XYChart.Data(rs.getDate("revisionDate").toString(), rs.getInt("dateCount")));
		    	}
		    	cardsToStudyChart.getData().add(series2);
			}catch(SQLException e){
				e.printStackTrace();
			}
		} catch (SQLException e) {
			System.out.println("SQLException has occured!");
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
	
	//switches to the chart displaying how many cards must be studied and on which days
	public void switchCharts() {
		cardsToStudyChart.setVisible(true);
		cardLevelChart.setVisible(false);
		switchButton.setVisible(false);
		switchButton2.setVisible(true);
	}
	
	//switches to the chart displaying how many cards are in each level
	public void switchCharts2() {
		cardsToStudyChart.setVisible(false);
		cardLevelChart.setVisible(true);
		switchButton.setVisible(true);
		switchButton2.setVisible(false);
	}
}
