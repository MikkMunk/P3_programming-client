import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Word[] words = new Word[25];
        Instructor_role instructor_role = new Instructor_role();
        instructor_role.display(words);

        Scanner input = new Scanner(System.in);
        boolean connect = true;

        try {
            Socket socket = new Socket("172.20.10.4", 9999);
            DataInputStream isFromServer = new DataInputStream(socket.getInputStream());
            //ObjectInputStream objectInputStream = new ObjectInputStream(isFromServer);
            DataOutputStream osToServer = new DataOutputStream(socket.getOutputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(osToServer);

            while (connect) {

                Word word1 = new Word();

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
                }
            }
            input.close();
            socket.close();
        }
        catch (Exception e) {
            throw new Error ("bad thing also happen");
        }

    }

}


