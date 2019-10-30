import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    static int cardChanged = 100;
    static String hintWord = "nothing";
    static int guessNum = 0;
    static boolean hintSubmitted = false;
    static boolean cardChosen = false;
    static int turn = 7;
    static boolean isPlaying = true;
    static Card[] cards = new Card[25];
    static Player_role player_role;
    static boolean myTurn = false;
    static int role_number;
    static boolean isFirstTurn = true;

    static DataOutputStream osToServer;

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        boolean connect = true;

        try {
            System.out.println("about to try stuff");
            Socket socket = new Socket("localhost", 5000);
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
                role_number = isFromServer.readInt();

                while(isPlaying){
                    turn = isFromServer.readInt();

                    if(turn == role_number){
                        myTurn = true;
                        loadDisplay(role_number, objectInputStream, isFromServer);

                        while (myTurn) {
                            System.out.println("It is your turn");
                            Thread.sleep(5000);
                        }
                    } else {
                        System.out.println("Wait for your turn");
                    }
                    Thread.sleep(5000);
                    //System.out.println("Im number " + role_number + " it is turn " + turn);
                }

            }
            input.close();
            socket.close();
        }
        catch (Exception e) {
            System.out.println(e);
            throw new Error ("bad thing also happen");
        }
    }

    public static void changedColor (int cardNumber) throws IOException {
        cardChanged = cardNumber;
        System.out.println("The card " + cardChanged + " has been chosen");
        cards[cardChanged].setPlayed(true);
        guessNum--;

        if (guessNum == 0) {
            cardChosen = true;
            sendStuff();
        }
        if (role_number ==  1){
            if (cards[cardChanged].getNumber() == 3){
                System.out.println("Correct guess");
            } else {
                System.out.println("Wrong guess");
                cardChosen = true;
                sendStuff();
            }
        }
        else if (role_number == 3){
            if (cards[cardChanged].getNumber() == 2){
                System.out.println("Correct guess");
            } else {
                System.out.println("Wrong guess");
                cardChosen = true;
                sendStuff();
            }
        }

    }

    public static void submittedHint (String hint, int guess) throws IOException {
        hintWord = hint;
        guessNum = guess;
        hintSubmitted = true;
        System.out.println(hintWord + " " + guessNum + " has been submitted");
        sendStuff();
    }

    static void gameOver (){
        isPlaying = false;
    }

    public static void loadDisplay (int role_number, ObjectInputStream objectInputStream, DataInputStream isFromServer)
            throws IOException, ClassNotFoundException, InvocationTargetException, InterruptedException {
        System.out.println(isFromServer.readUTF());
        if (isFirstTurn) {
            System.out.println("getting the cards");
            for (int i = 0; i < 25; i++) {
                cards[i] = (Card) objectInputStream.readObject();
                System.out.println(cards[i].getName());
            }
            System.out.println("cards received");
            isFirstTurn = false;
        } else {
            System.out.println("getting booleans");
            for (int i = 0; i < 25; i++){
                cards[i].setPlayed(isFromServer.readBoolean());
            }
        }
        osToServer.writeUTF("cards received");

        hintWord = isFromServer.readUTF();
        guessNum = isFromServer.readInt();
        osToServer.writeUTF("hint received");

        if (role_number == 0 || role_number == 2){
            player_role = new Player_role(cards, 1, 1,  hintWord, guessNum);
            player_role.display(); }
        else if (role_number == 1 || role_number == 3){
            player_role = new Player_role(cards, 1, 2,  hintWord, guessNum);
            player_role.display(); }

    }

    static void sendStuff() throws IOException{
        if (cardChosen) {
            for (int i = 0; i < 25; i++) {
                osToServer.writeBoolean(cards[i].isPlayed());
            }
            cardChanged = 100;
            cardChosen = false;
            turn = 7;
            System.out.println("card chosen");
            player_role.closeUI();
            myTurn = false;
        }
        if (hintSubmitted) {
            System.out.println("Submitted hint became true");
            osToServer.writeUTF(hintWord);
            osToServer.writeInt(guessNum);
            hintSubmitted = false;
            turn = 7;
            System.out.println("hint submitted");
            player_role.closeUI();
            myTurn = false;
        }
    }

}

