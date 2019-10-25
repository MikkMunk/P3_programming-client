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
    static Instructor_role instructor_role;
    static Guesser_role guesser_role;

    static DataOutputStream osToServer;

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        boolean connect = true;

        try {
            System.out.println("about to try stuff");
            Socket socket = new Socket("192.168.43.18", 8000);
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
                int role_number;

                System.out.println("The game has started");
                System.out.println(isFromServer.readUTF());
                role_number = isFromServer.readInt();

                loadDisplay(role_number, objectInputStream, isFromServer);

                nextTurn(role_number, osToServer, isFromServer);

                while (isPlaying) {
                    updateDisplay(isFromServer, objectInputStream, role_number);

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

    public static void submittedHint (String hint, int guess) throws IOException {
        hintWord = hint;
        guessNum = guess;
        hintSubmitted = true;
        System.out.println(hintWord + " " + guessNum + " " + hintSubmitted);
        sendStuff();
    }

    static void gameOver (){
        isPlaying = false;
    }

    public static void loadDisplay (int role_number, ObjectInputStream objectInputStream, DataInputStream isFromServer)
            throws IOException, ClassNotFoundException {
        for (int i = 0; i < 25; i++ ) {
            cards[i] = (Card) objectInputStream.readObject();
            System.out.println(cards[i].getName());
        }
        System.out.println("cards received");

        hintWord = isFromServer.readUTF();
        guessNum = isFromServer.readInt();

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
        for (int i = 0; i < 25; i++ ) {
            cards[i] = (Card) objectInputStream.readObject();
        }
        System.out.println("cards updated");

        hintWord = isFromServer.readUTF();
        guessNum = isFromServer.readInt();

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
        if (cardChanged != 100) {
            osToServer.writeInt(cardChanged);
            cardChanged = 100;
            turn = 7;
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
}

