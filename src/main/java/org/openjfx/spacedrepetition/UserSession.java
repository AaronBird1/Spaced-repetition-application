package org.openjfx.spacedrepetition;

public final class UserSession {
	
	private static UserSession instance;
	
	private int id;
	
	//sets the id int to the inputted userId
	public UserSession(int userId) {
		this.id = userId;
	}
	
	public UserSession() {
		
	}
	
	//checks if the instance is currently null, if so sets it to a new UserSession with the inputted userId
	public static void setInstance(int userId) {
		if(instance == null) {
			instance = new UserSession(userId);
		}
	}
	
	//returns the instance if it has been set
	public static UserSession getInstance() {
		System.out.println("getInstance method in UserSession class has been activated");
		if(instance == null) {
			System.out.println("instance hasnt been set yet");
		}
		return instance;
	}
	
	//returns the userId of the instance
	public int getUserId() {
		System.out.println("getUserId method in UserSession class has been activated");
		return id;
	}
	
	//resets the userSession instance to be null
	public void cleanUserSession() {
		System.out.println("cleanUserSession method in UserSession class has been activated");
		instance = null;
	}
}
