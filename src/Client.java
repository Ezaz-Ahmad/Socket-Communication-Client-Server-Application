import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String hostname = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 12345;

        try (Socket socket = new Socket(hostname, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            Scanner scanner = new Scanner(System.in);
            String command;
            while (true) {
                System.out.println("Enter command (GET_CAPITAL, GET_POPULATION, ADD_COUNTRY, EXIT):");
                command = scanner.nextLine().toUpperCase();

                if (command.equals("EXIT")) {
                    break;
                }

                System.out.println("Enter parameter:");
                String parameter = scanner.nextLine();

                out.println(command + ":" + parameter);

                String response = in.readLine();
                System.out.println("Response: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
