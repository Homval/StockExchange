package ru.khomyakov;

import ru.khomyakov.domain.ClientAccount;
import ru.khomyakov.services.InOutService;
import ru.khomyakov.services.OrderRequestsService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class App {

    public static Map<String, ClientAccount> clients;

    public static void main( String[] args ) {

        Properties properties = new Properties();
        try (FileReader reader = new FileReader("C:\\Users\\in00\\IdeaProjects\\StockExchange\\src\\resources\\app.properties")){
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }


//      Reading file Clients.txt and creating client accounts "data base" in map
        try {
            clients = InOutService.initClientAccountDataBase(new File(properties.getProperty("CLIENTS_FILE_PATH")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        Produce business operations
        try {
            OrderRequestsService.executeOrdersRequests(properties.getProperty("ORDERS_FILE_PATH"));
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Save clients map to file
        try {
            InOutService.saveClientsToFile(clients, properties.getProperty("PRINT_FILE_PATH"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
