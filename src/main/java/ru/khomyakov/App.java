package ru.khomyakov;

import ru.khomyakov.domain.ClientAccount;
import ru.khomyakov.services.InOutService;
import ru.khomyakov.services.OrderRequestsService;

import java.io.File;
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

    public static void main( String[] args ) throws IOException{

        Properties properties = new Properties();
        FileReader reader = new FileReader("C:\\Users\\in00\\IdeaProjects\\StockExchange\\src\\resources\\app.properties");
        properties.load(reader);
        reader.close();



//      Reading file Clients.txt and creating client accounts "data base" in map
        clients = InOutService.initClientAccountDataBase(new File(properties.getProperty("CLIENTS_FILE_PATH")));

//        Produce business operations
        OrderRequestsService.executeOrdersRequests(properties.getProperty("ORDERS_FILE_PATH"));

//        Save clients map to file
        InOutService.saveClientsToFile(clients, properties.getProperty("PRINT_FILE_PATH"));

    }


}
