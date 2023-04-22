package com.example.tictactoe;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    //GUI Elements from private static Scene scene; to  Font font = Font.font("Poppins", FontWeight.BOLD, 30);.
    private static Scene scene;
    private GridPane gridpane = new GridPane();
    private BorderPane borderpane = new BorderPane();
    private Label title = new Label("Tic-Tac-Toe Game");
    private Button restartButton = new Button("Restart Game");
    Font font = Font.font("Poppins", FontWeight.BOLD, 30);
    private Button[] btns = new Button[9];//to print 9 grids, we create an array.

    //all logic variables
    boolean gameOver = false;
    int activePlayer = 0;
    int gameState[]={3,3,3,3,3,3,3,3,3};
    int winningPosition[][]={
            {0,1,2},
            {3,4,5},
            {6,7,8},
            {0,3,6},
            {1,4,7},
            {2,5,8},
            {0,4,8},
            {2,4,6}
    };

    @Override
    public void start(Stage stage)throws IOException {
        this.createGUI();
        this.handleEvent();
        Scene scene = new Scene(borderpane,550,650);
        stage.setScene(scene);
        stage.show();
    }

    //This function is for creating GUI.
    private void createGUI() {
        //setting Title's Font to Poppins Bold.
        title.setFont(font);
        //setting Restart Button's Font to Poppins Bold.
        restartButton.setFont(font);
        //Disabling Reset Button at the start.
        restartButton.setDisable(true);
        //setting Title & Restart Button to Border Pane.
        borderpane.setTop(title);
        borderpane.setBottom(restartButton);
        //Aligning Title & Restart Button to Border Pane to Center.
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setAlignment(restartButton, Pos.CENTER);
        //Padding
        borderpane.setPadding(new Insets(20,20,20,20));
        //Working on those 9 buttons.
        int label=0;
        for(int i=0; i<3; i++){
            for(int j=0;j<3;j++){
                Button button = new Button();
                button.setId(label+"");
                button.setFont(font);
                //Setting Width & Height
                button.setPrefWidth(150);//Setting Width
                button.setPrefHeight(150);//Setting Height
                gridpane.add(button,j,i);
                gridpane.setAlignment(Pos.CENTER);
                btns[label] = button;
                label++;
            }
        }
        borderpane.setCenter(gridpane);
    }

    //method for handling events
    private void handleEvent(){
        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for (int i=0;i<9;i++){
                    gameState[i]=3;
                    //btns[i].setText("");
                    btns[i].setGraphic(null);
                    gameOver=false;
                    restartButton.setDisable(true);
                }
            }
        });
        for(Button btn:btns){
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    //System.out.println("Number Button Clicked!!");
                    Button currentBtn = (Button)actionEvent.getSource();
                    String idS=currentBtn.getId();
                    int idI = Integer.parseInt(idS);
                    if(gameOver){
                        //Game-Over, and print message.
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message!!");
                        alert.setContentText("Game Over, Try to Restart!!");
                        alert.show();
                    }
                    else{
                        //Game is not Over, so do chances.
                        //Check for players.
                        if(gameState[idI]==3){
                            //Proceed
                            if(activePlayer==1){
                                //Chance of One.
                                //currentBtn.setText(activePlayer+"");
                                currentBtn.setGraphic(new ImageView(new Image("file:src/main/resources/img/X.png")));
                                gameState[idI]=activePlayer;
                                checkForWinner();//CHECKS FOR WINNER
                                activePlayer=0;
                            }else{
                                //Chance of Zero.t
                                //currentBtn.setText(activePlayer+"");
                                currentBtn.setGraphic(new ImageView(new Image("file:src/main/resources/img/O.png")));
                                gameState[idI]=activePlayer;
                                checkForWinner();//CHECKS FOR WINNER
                                activePlayer=1;
                            }
                        }
                        else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Message!!");
                            alert.setContentText("Invalid Move, Place is Already Taken!!");
                            alert.show();
                        }
                    }
                    //System.out.println(idI+" Button Clicked!!");
                }
                //This method checks for a Winner.
                private void checkForWinner() {
                    if(!gameOver){
                        for(int wp[]: winningPosition){
                        if(gameState[wp[0]]==gameState[wp[1]]&&gameState[wp[1]]==gameState[wp[2]]&&gameState[wp[1]]!=3){
                            //Active Player has a Winner!!
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Winning Message!!");
                            alert.setContentText((activePlayer==1?"X":"O")+" has Won the Game!!");
                            alert.show();
                            gameOver=true;
                            restartButton.setDisable(false);
                            break;
                        }
                            }
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
