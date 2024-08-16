import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static final Map<String, String> countryCapMap = new ConcurrentHashMap<>();
    private static final Random random = new Random();
    private static int stuNumber = 3412618;

    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 4500;
        load_Country_Cap_D();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void load_Country_Cap_D() {
        try (BufferedReader br = new BufferedReader(new FileReader("capitals.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    countryCapMap.put(data[0].trim(), data[1].trim());
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
                        case "Capital":
                            out.println(getCapitalCity(parameter));
                            break;
                        case "Population":
                            out.println(getPopulation(parameter));
                            break;
                        case "Add Country":
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
            String capital = countryCapMap.get(country);
            return capital != null ? capital : "ERROR: Country not found";
        }

        private String getPopulation(String country) {
            if (!countryCapMap.containsKey(country)) {
                return "ERROR: Country not found";
            }
            int randomFactor = random.nextInt(10) + 1;
            return String.valueOf(randomFactor * stuNumber);
        }

        private String addCountryCapital(String country, String capital) {
            // Check if both country and capital are not empty
            if (country == null || country.trim().isEmpty() || capital == null || capital.trim().isEmpty()) {
                return "ERROR: Invalid input. Both country and capital must be provided.";
            }
            // Check if the country already exists in the map
            if (countryCapMap.containsKey(country.trim())) {
                return "ERROR: Country already exists.";
            }
            // Add the new country and capital to the map
            countryCapMap.put(country.trim(), capital.trim());
            return "SUCCESS: Country added.";
        }

    }
}