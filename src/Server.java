import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static final Map<String, String> countryCapitalMap = new ConcurrentHashMap<>();
    private static final Random random = new Random();
    private static int studentNumber = 3412618;

    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 12345;
        loadCountryCapitalData();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadCountryCapitalData() {
        try (BufferedReader br = new BufferedReader(new FileReader("countries_capitals.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    countryCapitalMap.put(data[0].trim(), data[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                String request;
                while ((request = in.readLine()) != null) {
                    String[] parts = request.split(":", 2);
                    String command = parts[0];
                    String parameter = parts.length > 1 ? parts[1] : "";

                    switch (command) {
                        case "GET_CAPITAL":
                            out.println(getCapitalCity(parameter));
                            break;
                        case "GET_POPULATION":
                            out.println(getPopulation(parameter));
                            break;
                        case "ADD_COUNTRY":
                            String[] newCountryData = parameter.split(",", 2);
                            out.println(addCountryCapital(newCountryData[0], newCountryData[1]));
                            break;
                        default:
                            out.println("ERROR: Unknown command");
                            break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String getCapitalCity(String country) {
            String capital = countryCapitalMap.get(country);
            return capital != null ? capital : "ERROR: Country not found";
        }

        private String getPopulation(String country) {
            if (!countryCapitalMap.containsKey(country)) {
                return "ERROR: Country not found";
            }
            int randomFactor = random.nextInt(10) + 1;
            return String.valueOf(randomFactor * studentNumber);
        }

        private String addCountryCapital(String country, String capital) {
            if (countryCapitalMap.containsKey(country)) {
                return "ERROR: Country already exists";
            }
            countryCapitalMap.put(country, capital);
            return "SUCCESS: Country added";
        }
    }
}
