import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

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

                System.out.print("Enter word string: ");
                word1.hej = input.nextLine();
                System.out.print("Enter word int: ");
                word1.number = input.nextInt();
                System.out.print("Enter word bool: ");
                word1.setHah(input.nextBoolean());

                objectOutputStream.writeObject(word1);


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


