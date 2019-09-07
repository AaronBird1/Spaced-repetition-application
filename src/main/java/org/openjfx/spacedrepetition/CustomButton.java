package org.openjfx.spacedrepetition;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class CustomButton {
	
  private String buttonTypeString;
  private Image home1 = new Image(getClass().getResourceAsStream("homebuttonup.png"));
  private Image home2 = new Image(getClass().getResourceAsStream("homebuttondown.png"));
  private Image back1 = new Image(getClass().getResourceAsStream("backbuttonup.png"));
  private Image back2 = new Image(getClass().getResourceAsStream("backbuttondown.png"));
  private Image login1 = new Image(getClass().getResourceAsStream("loginbuttonup.png"));
  private Image login2 = new Image(getClass().getResourceAsStream("loginbuttondown.png"));
  private Image newaccount1 = new Image(getClass().getResourceAsStream("newaccountbuttonup2.png"));
  private Image newaccount2 = new Image(getClass().getResourceAsStream("newaccountbuttondown.png"));
  private Image about1 = new Image(getClass().getResourceAsStream("aboutbuttonup.png"));
  private Image about2 = new Image(getClass().getResourceAsStream("aboutbuttondown.png"));
  private Image newcard1 = new Image(getClass().getResourceAsStream("newcardbuttonup.png"));
  private Image newcard2 = new Image(getClass().getResourceAsStream("newcardbuttondown.png"));
  private Image stats1 = new Image(getClass().getResourceAsStream("statsbuttonup.png"));
  private Image stats2 = new Image(getClass().getResourceAsStream("statsbuttondown.png"));
  private Image study1 = new Image(getClass().getResourceAsStream("studybuttonup.png"));
  private Image study2 = new Image(getClass().getResourceAsStream("studybuttondown.png"));
  private Image editdeck1 = new Image(getClass().getResourceAsStream("editdeckbuttonup.png"));
  private Image editdeck2 = new Image(getClass().getResourceAsStream("editdeckbuttondown.png"));
  private Image editdeadline1 = new Image(getClass().getResourceAsStream("editdeadlinebuttonup.png"));
  private Image editdeadline2 = new Image(getClass().getResourceAsStream("editdeadlinebuttonup.png"));
  private Image logout1 = new Image(getClass().getResourceAsStream("logoutbuttonup.png"));
  private Image logout2 = new Image(getClass().getResourceAsStream("logoutbuttondown.png"));
  private Image decks1 = new Image(getClass().getResourceAsStream("decksbuttonup.png"));
  private Image decks2 = new Image(getClass().getResourceAsStream("decksbuttondown.png"));
  	
  public CustomButton(Button newButton, String buttonType) {
	System.out.println("BackButton constructor started!");
	newButton.setPrefWidth(49);
	newButton.setPrefHeight(49);
	
	buttonTypeString = buttonType;
  
	setGraphics(newButton, true);
	
	
	newButton.setStyle("-fx-background-color: transparent;");
    initializeButtonListeners(newButton);
    System.out.println("BackButton constructor ended!");
  }
  
  //sets the image of the button based on the type of button the user inputted and whether the upstatus is true or false
  private void setGraphics(Button newButton, Boolean upstatus) {
	  if(buttonTypeString.compareTo("home") == 0 && upstatus == true) {
		  newButton.setGraphic(new ImageView(home1));
	  }
	  else if(buttonTypeString.compareTo("home") == 0 && upstatus == false) {
		  newButton.setGraphic(new ImageView(home2));
	  }
	  else if(buttonTypeString.compareTo("back") == 0 && upstatus == true) {
		  newButton.setGraphic(new ImageView(back1));
	  }
	  else if(buttonTypeString.compareTo("back") == 0 && upstatus == false) {
		  newButton.setGraphic(new ImageView(back2));
	  }
	  else if(buttonTypeString.compareTo("login") == 0 && upstatus == true) {
		  newButton.setGraphic(new ImageView(login1));
	  }
	  else if(buttonTypeString.compareTo("login") == 0 && upstatus == false) {
		  newButton.setGraphic(new ImageView(login2));
	  }
	  else if(buttonTypeString.compareTo("newaccount") == 0 && upstatus == true) {
		  newButton.setGraphic(new ImageView(newaccount1));
	  }
	  else if(buttonTypeString.compareTo("newaccount") == 0 && upstatus == false) {
		  newButton.setGraphic(new ImageView(newaccount2));
	  }
	  else if(buttonTypeString.compareTo("about") == 0 && upstatus == true) {
		  newButton.setGraphic(new ImageView(about1));
	  }
	  else if(buttonTypeString.compareTo("about") == 0 && upstatus == false) {
		  newButton.setGraphic(new ImageView(about2));
	  }
	  else if(buttonTypeString.compareTo("newcard") == 0 && upstatus == true) {
		  newButton.setGraphic(new ImageView(newcard1));
	  }
	  else if(buttonTypeString.compareTo("newcard") == 0 && upstatus == false) {
		  newButton.setGraphic(new ImageView(newcard2));
	  }
	  else if(buttonTypeString.compareTo("stats") == 0 && upstatus == true) {
		  newButton.setGraphic(new ImageView(stats1));
	  }
	  else if(buttonTypeString.compareTo("stats") == 0 && upstatus == false) {
		  newButton.setGraphic(new ImageView(stats2));
	  }
	  else if(buttonTypeString.compareTo("study") == 0 && upstatus == true) {
		  newButton.setGraphic(new ImageView(study1));
	  }
	  else if(buttonTypeString.compareTo("study") == 0 && upstatus == false) {
		  newButton.setGraphic(new ImageView(study2));
	  }
	  else if(buttonTypeString.compareTo("editdeck") == 0 && upstatus == true) {
		  newButton.setGraphic(new ImageView(editdeck1));
	  }
	  else if(buttonTypeString.compareTo("editdeck") == 0 && upstatus == false) {
		  newButton.setGraphic(new ImageView(editdeck2));
	  }
	  else if(buttonTypeString.compareTo("editdeadline") == 0 && upstatus == true) {
		  newButton.setGraphic(new ImageView(editdeadline1));
	  }
	  else if(buttonTypeString.compareTo("editdeadline") == 0 && upstatus == false) {
		  newButton.setGraphic(new ImageView(editdeadline2));
	  }
	  else if(buttonTypeString.compareTo("logout") == 0 && upstatus == true) {
		  newButton.setGraphic(new ImageView(logout1));
	  }
	  else if(buttonTypeString.compareTo("logout") == 0 && upstatus == false) {
		  newButton.setGraphic(new ImageView(logout2));
	  }
	  else if(buttonTypeString.compareTo("decks") == 0 && upstatus == true) {
		  newButton.setGraphic(new ImageView(decks1));
	  }
	  else if(buttonTypeString.compareTo("decks") == 0 && upstatus == false) {
		  newButton.setGraphic(new ImageView(decks2));
	  }
  }

  //changes the height and layout of the button to appear as if it is pressed down
  private void setButtonPressedStyle(Button newButton) {
	setGraphics(newButton, false);
	newButton.setPrefHeight(45);
	newButton.setLayoutY(newButton.getLayoutY() + 4);
  }

  //changes the height and layout of the button to appear as if it is not pressed down
  private void setButtonUnpressedStyle(Button newButton) {
	setGraphics(newButton, true);
	newButton.setPrefHeight(49);
	newButton.setLayoutY(newButton.getLayoutY() - 4);
  }

  //sets up how the button will respond to the mouse clicking it and unclicking it
  private void initializeButtonListeners(Button newButton) {

	  newButton.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)) {
          setButtonPressedStyle(newButton);
        }
      }
    });

	  newButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if(event.getButton().equals(MouseButton.PRIMARY)) {
          setButtonUnpressedStyle(newButton);
          System.out.println("The back button has been pressed!");
        }
      }
    });

	  newButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
    	  //newButton.setEffect(new DropShadow());
      }
    });

	  newButton.setOnMouseExited(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
    	  newButton.setEffect(null);
      }
    });
  }


}