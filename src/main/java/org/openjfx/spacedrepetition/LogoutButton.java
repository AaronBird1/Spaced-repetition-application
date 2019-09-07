package org.openjfx.spacedrepetition;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class LogoutButton {
  private Image image1 = new Image(getClass().getResourceAsStream("logoutbuttonup.png"));
  private Image image2 = new Image(getClass().getResourceAsStream("logoutbuttondown.png"));
  	
  public LogoutButton(Button newButton) {
	System.out.println("BackButton constructor started!");
	newButton.setPrefWidth(49);
	newButton.setPrefHeight(49);
  
	newButton.setGraphic(new ImageView(image1));
	newButton.setStyle("-fx-background-color: transparent;");
    initializeButtonListeners(newButton);
    System.out.println("BackButton constructor ended!");
  }

  private void setButtonPressedStyle(Button newButton) {
	newButton.setGraphic(new ImageView(image2));
	newButton.setPrefHeight(45);
	newButton.setLayoutY(newButton.getLayoutY() + 4);
  }

  private void setButtonUnpressedStyle(Button newButton) {
	newButton.setGraphic(new ImageView(image1));
	newButton.setPrefHeight(49);
	newButton.setLayoutY(newButton.getLayoutY() - 4);
  }

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