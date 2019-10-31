import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    static int cardChanged = 100; //used to keep track of the most recently chosen card (100 is used as a placeholder)
    static String hintWord; //the most recently given hint
    static int guessNum; //the max number og guesses associated with the current hint
    static boolean hintSubmitted = false; //to check when an instructor has given their input
    static boolean cardChosen = false; //to check when a guesser has given their input
    static int turn = 7; //tracking whos' turn it currently is (7 is used as a placeholder)
    static boolean isPlaying = true; //used to keep the program looping after the initial information is received
    static Card[] cards = new Card[25]; //an array to contain the cards
    static Player_role player_role; //the player is used to open and close the UI
    static boolean myTurn = false; //used to check when it is the players turn to provide input
    static int role_number; //Used to assign a role and control which information/actions are available
    static boolean isFirstTurn = true; //used to load things on the first turn, then simply update them on later turns

    static DataOutputStream osToServer;

    public static void main(String[] args) {

        boolean connect = true;

        try {
            System.out.println("about to try stuff");
            Socket socket = new Socket("172.20.10.6", 5000);
            System.out.println("socket made");
            DataInputStream isFromServer = new DataInputStream(socket.getInputStream());
            System.out.println("about to make object input");
            ObjectInputStream objectInputStream = new ObjectInputStream(isFromServer);
            System.out.println("about to make data output");
            osToServer = new DataOutputStream(socket.getOutputStream());
            System.out.println("about to make object output");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(osToServer);
            System.out.println("Stuff got tried");

            while (connect) {

                System.out.println("The game has started");
                System.out.println(isFromServer.readUTF());
                role_number = isFromServer.readInt(); //receiving role number based on order of connection

                while(isPlaying){
                    turn = isFromServer.readInt(); //receiving update on which turn it it

                    if(turn == role_number){ //checking if it is the players turn
                        myTurn = true;
                        loadDisplay(role_number, objectInputStream, isFromServer); //creating/showing the UI

                        while (myTurn) { //Keeps looping until input is given, which ends the turn
                            System.out.println("It is your turn");
                            Thread.sleep(5000);
                        }
                    } else {
                        System.out.println("Wait for your turn");
                    }
                    Thread.sleep(5000);
                }

            }
            socket.close();
        }
        catch (Exception e) {
            System.out.println(e);
            throw new Error ("bad thing also happen");
        }
    }

    //this function is called when a guesser selects a card
    public static void changedColor (int cardNumber) throws IOException, InterruptedException {
        cardChanged = cardNumber; //checking the array position of the selected cards (0 - 24)
        System.out.println("The card " + cardChanged + " has been chosen");
        cards[cardChanged].setPlayed(true); //registering that the card has been chosen
        guessNum--; //counting down the number of possible guesses based on the number given by the instructor

        if (role_number ==  1){ //checking if the guess was right for members on the red team
            if (cards[cardChanged].getNumber() == 3){
                System.out.println("Correct guess");
            } else { //runs if the guess was wrong
                System.out.println("Wrong guess");
                cardChosen = true;//registering that input has been given
                Thread.sleep(2000); //putting a small delay so the player has time to see the color of their last choice
                sendStuff(); //function that sends info to the server and ends turn
            }
        }
        else if (role_number == 3){ //checking if the guess was right for members on the blue team
            if (cards[cardChanged].getNumber() == 2){
                System.out.println("Correct guess");
            } else { //runs if the guess was wrong
                System.out.println("Wrong guess");
                cardChosen = true;//registering that input has been given
                Thread.sleep(2000); //putting a small delay so the player has time to see the color of their last choice
                sendStuff(); //function that sends info to the server and ends turn
            }
        }
        if (guessNum == 0) { //running when all guesses have been used
            cardChosen = true;//registering that input has been given
            Thread.sleep(2000); //putting a small delay so the player has time to see the color of their last choice
            sendStuff(); //function that sends info to the server and ends turn
        }

    }

    //called when an instructor clicks the submit button
    public static void submittedHint (String hint, int guess) throws IOException {
        hintWord = hint; //setting the hintWord based on text input
        guessNum = guess; //setting the guessNum based on text input
        hintSubmitted = true; //registering that input has been given
        System.out.println(hintWord + " " + guessNum + " has been submitted");
        sendStuff();//function that sends info to the server and ends turn
    }

    //called whenever the players turn starts to display the current state of the game and allow input
    public static void loadDisplay (int role_number, ObjectInputStream objectInputStream, DataInputStream isFromServer)
            throws IOException, ClassNotFoundException, InvocationTargetException, InterruptedException {
        System.out.println(isFromServer.readUTF());

        if (isFirstTurn) { //checking if this is the game's first turn
            System.out.println("getting the cards");
            for (int i = 0; i < 25; i++) {
                cards[i] = (Card) objectInputStream.readObject(); //receiving 25 card objects from the server
                System.out.println(cards[i].getName());
            }
            System.out.println("cards received");
            isFirstTurn = false; //setting that the first turn is over
        } else { //if it's not the fors turn
            System.out.println("getting booleans");
            for (int i = 0; i < 25; i++){
                cards[i].setPlayed(isFromServer.readBoolean()); //updating if the cards have been chosen, based on boolean array
            }
        }
        osToServer.writeUTF("cards received");

        hintWord = isFromServer.readUTF(); //receiving current hint word
        guessNum = isFromServer.readInt(); //receiving current guess number
        osToServer.writeUTF("hint received");

        if (role_number == 0){ //loads and displays the UI if the player is an instructor
            player_role = new Player_role(cards, 1, 1,  hintWord, guessNum);
            player_role.display(); }
        else if (role_number == 1){ //loads and displays the UI if the player is a guesser
            player_role = new Player_role(cards, 1, 2,  hintWord, guessNum);
            player_role.display(); }
        else if (role_number == 2){ //loads and displays the UI if the player is an instructor
            player_role = new Player_role(cards, 2, 1,  hintWord, guessNum);
            player_role.display(); }
        else if (role_number == 3){ //loads and displays the UI if the player is a guesser
            player_role = new Player_role(cards, 2, 2,  hintWord, guessNum);
            player_role.display(); }

    }

    //Called to send input back to the server and end the players current turn
    static void sendStuff() throws IOException{
        if (cardChosen) { //if the input is from a guesser
            for (int i = 0; i < 25; i++) {
                osToServer.writeBoolean(cards[i].isPlayed()); //sends updated list of booleans, indicating which cards have been chosen
            }
            cardChanged = 100; //setting the number back to its' placeholder value
            cardChosen = false; //registering that the input has been send
            turn = 7; //setting the number back to its' placeholder value
            System.out.println("card chosen");
            player_role.closeUI(); //closing down the UI to prevent further input
            myTurn = false; //ending the current turn
        }
        if (hintSubmitted) { //if the input is from an instructor
            System.out.println("Submitted hint became true");
            osToServer.writeUTF(hintWord); //sending new hint word
            osToServer.writeInt(guessNum); //sending new guess number
            hintSubmitted = false; //registering that the input has been send
            turn = 7; //setting the number back to its' placeholder value
            System.out.println("hint submitted");
            player_role.closeUI(); //closing down the UI to prevent further input
            myTurn = false; //ending the current turn
        }
    }

}

