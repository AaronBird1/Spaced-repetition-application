package org.openjfx.spacedrepetition;

import java.time.LocalDate;

public class SpacingAlgorithm {

	public void updateCardDate(Card inputtedCard) {
		
	}
	
	//returns true of the study date of the inputted card has passed, otherwise returns false
	public Boolean checkCardDate(Card inputtedCard) {
		System.out.println("checkCardDate in SpacingAlgorithm class has been activated...");
		LocalDate todaysDate = LocalDate.now();
		LocalDate cardsDate = inputtedCard.getStudyDate().toLocalDate();
		System.out.println("\n\nTodays date is " + todaysDate + "\n\n");
		System.out.println("\n\n Cards date is " + cardsDate + "\n\n");
		if(cardsDate.isBefore(todaysDate)) {
			inputtedCard.setDateToToday();
			System.out.println("The cards date in the past!");
			return true;
		}
		else if(cardsDate.equals(todaysDate)) {
			System.out.println("The cards date is today!");
			return false;
		}
		else {
			System.out.println("The cards date is in the future!");
			return false;
		}
	}
	
	//increases the level the card is in if the rememberedStatus is true else the level is reset to 0
	public void updateCardRevisionDate(Card inputtedCard, Boolean rememberedStatus) {
		if(rememberedStatus == true) {
			inputtedCard.goUpALevel();
		}
		else {
			inputtedCard.resetLevel();
		}
	}
	
	/*public int checkRememberedPercentage(Card inputtedCard) {
		Double percentage = inputtedCard.getRatioTimesRemembered();
		
		if(percentage < 0.9) {
			return -1;
		}
		else if(percentage == 0.9) {
			return 0;
		}
		else {
			return 1;
		}
		
	}*/
	
}
