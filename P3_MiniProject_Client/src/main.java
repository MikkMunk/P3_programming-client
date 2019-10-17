import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class main {


    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        boolean connect = true;

        try {
            Socket connectToServer = new Socket("172.20.10.4", 9999);
            DataInputStream isFromServer = new DataInputStream(connectToServer.getInputStream());
            DataOutputStream osToServer = new DataOutputStream(connectToServer.getOutputStream());

            while (connect) {
                /*// Enter annual interest rate
                System.out.print("Enter annual interest rate, for example, 8,25: ");
                double annualInterestRate = input.nextDouble();

                // Enter number of years
                System.out.print("Enter number of years as an integer: ");
                int numOfYears = input.nextInt();

                // Enter loan amount
                System.out.print("Enter loan amount, for example, 120000,95: ");
                double loanAmount = input.nextDouble();

                // Send the annual interest rate to the server
                osToServer.writeDouble(annualInterestRate);

                // Send the number of years to the server
                osToServer.writeInt(numOfYears);

                // Send the loan amount to the server
                osToServer.writeDouble(loanAmount);

                osToServer.flush();

                // Get monthly payment from the server
                double monthlyPayment = isFromServer.readDouble();

                // Get total payment from the server
                double totalPayment = isFromServer.readDouble();

                System.out.println("Annual Interest Rate: " + annualInterestRate +
                        "\nNumber of Years: " + numOfYears + "\nLoan Amount: " +
                        loanAmount + "\n");
                System.out.println("monthlyPayment: " + monthlyPayment + " " +
                        "\ntotalPayment: " + totalPayment + '\n');
                //Exit or continue with a new set of values
                System.out.print("Type yes to continue with a new set of value or no to stop: ");*/

                word word1 = new word();

                osToServer.flush();

                if (input.next().equals("no")) {
                    connect = false;
                }
            }
            input.close();
            connectToServer.close();
        }
        catch (IOException ex) {
            System.out.println(ex.toString() + '\n');
        }

    }

}


