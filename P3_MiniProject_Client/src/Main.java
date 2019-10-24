import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    static int cardChanged = 100;
    static String hintWord = "nothing";
    static int guessNum = 0;
    static boolean hintSubmitted = false;
    static int turn = 7;
    static boolean isPlaying = true;
    static Card[] cards = new Card[25];

    public static void main(String[] args) {
        Instructor_role instructor_role = new Instructor_role();
        Guesser_role guesser_role = new Guesser_role();

        Scanner input = new Scanner(System.in);
        boolean connect = true;

        try {
            System.out.println("about to try stuff");
            Socket socket = new Socket("172.20.10.6", 8000);
            System.out.println("socket made");
            DataInputStream isFromServer = new DataInputStream(socket.getInputStream());
            System.out.println("about to make object input");
            ObjectInputStream objectInputStream = new ObjectInputStream(isFromServer);
            System.out.println("about to make data output");
            DataOutputStream osToServer = new DataOutputStream(socket.getOutputStream());
            System.out.println("about to make object output");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(osToServer);
            System.out.println("Stuff got tried");

            while (connect) {
                int role_number;

                System.out.println("The game has started");
                System.out.println(isFromServer.readUTF());
                role_number = isFromServer.readInt();

                while (isPlaying) {
                    updateDisplay(role_number, instructor_role, guesser_role, objectInputStream, isFromServer);

                    nextTurn(role_number, osToServer, isFromServer);
                }

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

    public static void changedColor (int cardNumber){
        cardChanged = cardNumber;
    }

    public static void submittedHint (String hint, int guess){
        hintWord = hint;
        guessNum = guess;
        hintSubmitted = true;
    }

    static void gameOver (){
        isPlaying = false;
    }

    public static void updateDisplay (int role_number, Instructor_role instructor_role, Guesser_role guesser_role, ObjectInputStream objectInputStream, DataInputStream isFromServer)
            throws IOException, ClassNotFoundException {
        for (int i = 0; i < 25; i++ ) {
            cards[i] = (Card) objectInputStream.readObject();
            System.out.println(cards[i].getName());
        }
        System.out.println("cards received");

        hintWord = isFromServer.readUTF();
        guessNum = isFromServer.readInt();

        if (role_number == 0){
            instructor_role.display(cards, 1, hintWord, guessNum); }
        else if (role_number == 1){
            guesser_role.display(cards, 1, hintWord, guessNum); }
        else if (role_number == 2){
            instructor_role.display(cards, 2, hintWord, guessNum); }
        else if (role_number == 3){
            guesser_role.display(cards, 2, hintWord, guessNum); }
    }

    static void nextTurn (int role_number, DataOutputStream osToServer, DataInputStream isFromServer)
            throws IOException {
        turn = isFromServer.readInt();

        if (turn == role_number) {
            System.out.println("It is you turn, please provide input");

            while(turn == role_number) {

                if (cardChanged != 100) {
                    osToServer.writeInt(cardChanged);
                    cardChanged = 100;
                    turn = 7;
                    System.out.println("card chosen");
                }

                if (hintSubmitted) {
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
}


