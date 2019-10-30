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
    static Instructor_role instructor_role;
    static Guesser_role guesser_role;
    static boolean myTurn = false;
    static int role_number;

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
                    System.out.println("Im number " + role_number + " it is turn " + turn);

                }

                /*loadDisplay(role_number, objectInputStream, isFromServer);

                nextTurn(role_number, osToServer, isFromServer);

                while (isPlaying) {
                    updateDisplay(isFromServer, objectInputStream, role_number);

                    nextTurn(role_number, osToServer, isFromServer);
                } */

                //connect = false;


                /*Cards word1 = new Cards();

                System.out.print("1Enter word string: ");
                word1.text = input.next();
                System.out.print("1Enter word int: ");
                word1.number = input.nextInt();
                System.out.print("1Enter word bool: ");
                word1.setHah(input.nextBoolean());

                objectOutputStream.writeObject(word1);

                Word word2 = new Word();

                System.out.print("2Enter word string: ");
                word2.text = input.next();
                System.out.print("2Enter word int: ");
                word2.number = input.nextInt();
                System.out.print("2Enter word bool: ");
                word2.setHah(input.nextBoolean());

                objectOutputStream.writeObject(word2);



                if (input.next().equals("no")) {
                    connect = false;
                } */
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
        cardChosen = true;
        System.out.println("The card " + cardChanged + " has been chosen");
        sendStuff();
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
        for (int i = 0; i < 25; i++ ) {
            cards[i] = (Card) objectInputStream.readObject();
            System.out.println(cards[i].getName());
        }
        System.out.println("cards received");
        osToServer.writeUTF("cards received");

        hintWord = isFromServer.readUTF();
        guessNum = isFromServer.readInt();
        osToServer.writeUTF("hint received");

        if (role_number == 0){
            instructor_role = new Instructor_role(cards, 1, hintWord, guessNum);
            instructor_role.display(); }
        else if (role_number == 1){
            guesser_role = new Guesser_role(cards, 1, hintWord, guessNum);
            guesser_role.display(); }
        else if (role_number == 2){
            instructor_role = new Instructor_role(cards, 2, hintWord, guessNum);
            instructor_role.display(); }
        else if (role_number == 3){
            guesser_role = new Guesser_role(cards, 2, hintWord, guessNum);
            guesser_role.display(); }
    }

    static void nextTurn (int role_number, DataOutputStream osToServer, DataInputStream isFromServer)
            throws IOException {
        System.out.println("new turn");
        turn = isFromServer.readInt();
        osToServer.writeUTF("turn number received");

        if (turn == role_number) {
            System.out.println("It is you turn, please provide input");

            while(turn == role_number) {
                if (cardChosen) {
                    osToServer.writeInt(cardChanged);
                    cardChanged = 100;
                    turn = 7;
                    cardChosen = false;
                    System.out.println("card chosen");
                }
                if (hintSubmitted) {
                    System.out.println("Submitted hint became true");
                    osToServer.writeUTF(hintWord);
                    osToServer.writeInt(guessNum);
                    hintSubmitted = false;
                    turn = 7;
                    System.out.println("hint submitted");
                }
            }
        } else {
            System.out.println("wait for your turn");

        }
    }

    static void updateDisplay (DataInputStream isFromServer, ObjectInputStream objectInputStream, int role_number)
            throws IOException, ClassNotFoundException {
        System.out.println(isFromServer.readUTF());
        System.out.println("updating");
        for (int i = 0; i < 25; i++ ) {
            cards[i] = (Card) objectInputStream.readObject();
        }
        System.out.println("cards updated");
        osToServer.writeUTF("cards received");

        hintWord = isFromServer.readUTF();
        guessNum = isFromServer.readInt();
        osToServer.writeUTF("hint received");

        if (role_number == 0 || role_number == 2){
            instructor_role.updateCards(cards);
            instructor_role.displayHint(hintWord, guessNum);
        }
        else if (role_number == 1 || role_number == 3){
            guesser_role.updateCards(cards);
            guesser_role.displayHint(hintWord, guessNum);
        }
    }
    static void sendStuff() throws IOException{
        if (cardChosen) {
            osToServer.writeInt(cardChanged);
            cardChanged = 100;
            cardChosen = false;
            turn = 7;
            System.out.println("card chosen");
            closeDisplay();
            myTurn = false;
        }
        if (hintSubmitted) {
            System.out.println("Submitted hint became true");
            osToServer.writeUTF(hintWord);
            osToServer.writeInt(guessNum);
            hintSubmitted = false;
            turn = 7;
            System.out.println("hint submitted");
            closeDisplay();
            myTurn = false;
        }
    }


    static void closeDisplay(){
        if(role_number == 0 || role_number == 2){
            instructor_role.closeUI();
        }
        else {
            guesser_role.closeUI();
        }
    }
}

