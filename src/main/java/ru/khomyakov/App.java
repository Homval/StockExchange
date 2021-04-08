package ru.khomyakov;

import ru.khomyakov.domain.ClientAccount;
import ru.khomyakov.services.InOutService;
import ru.khomyakov.services.OrderRequestsService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App {
    private static final String CLIENTS_FILE_PATH = "C:\\Users\\in00\\IdeaProjects\\StockExchange\\src\\resources\\clients.txt";
    private static final String ORDERS_FILE_PATH = "C:\\Users\\in00\\IdeaProjects\\StockExchange\\src\\resources\\orders.txt";
    private static final String PRINT_FILE_PATH = "C:\\Users\\in00\\IdeaProjects\\StockExchange\\src\\resources\\results.txt";

    public static Map<String, ClientAccount> clients;

    public static void main( String[] args ) {

//      Reading file Clients.txt and creating client accounts "data base" in map
        try {
            clients = InOutService.initClientAccountDataBase(new File(CLIENTS_FILE_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        Produce business operations
        try {
            OrderRequestsService.executeOrdersRequests(ORDERS_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Save clients map to file
        try {
            InOutService.saveClientsToFile(clients, PRINT_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
