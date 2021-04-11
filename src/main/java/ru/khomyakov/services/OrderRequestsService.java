package ru.khomyakov.services;

import ru.khomyakov.App;
import ru.khomyakov.domain.ClientAccount;
import ru.khomyakov.domain.StockNames;
import ru.khomyakov.domain.StockRequest;
import ru.khomyakov.exceptions.WrongStockRequestException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OrderRequestsService {

    public static void executeOrdersRequests(String ordersFilePath) throws IOException {
//        Create lists for buyers and sellers requests
        List<StockRequest> sellersRequests = new LinkedList<>();
        List<StockRequest> buyersRequests = new LinkedList<>();

        BufferedReader reader = new BufferedReader(new FileReader(ordersFilePath));
        String request;
        while ((request = reader.readLine()) != null) {
            String[] requestParts = request.split("\t");
//            check the entered data for StockRequest
            try{
                areRequestParametersCorrect(requestParts);
            } catch (WrongStockRequestException e) {
                e.printStackTrace();
            }
            StockRequest stockRequest = new StockRequest(requestParts[0],
                                                        requestParts[1],
                                                        StockNames.valueOf(requestParts[2]),
                                                        Integer.parseInt(requestParts[3]),
                                                        Integer.parseInt(requestParts[4]));

            switch (requestParts[1]) {
                case ("b") :
                    addToListOrExecuteRequest(buyersRequests, sellersRequests, stockRequest, App.clients);
                    break;
                case ("s") :
                    addToListOrExecuteRequest(sellersRequests, buyersRequests, stockRequest, App.clients);
                    break;
            }
        }

        System.out.println(sellersRequests.size() + " " + buyersRequests.size());
    }

//        If request not exist yet add it in appropriate list. In other case make transaction
    public static void addToListOrExecuteRequest(List<StockRequest> sameTypeRequests, List<StockRequest> otherTypeRequests, StockRequest stockRequest, Map<String, ClientAccount> clients) {
        if (isRequestAvailableForExecute(stockRequest, otherTypeRequests)) {
            String clientName = otherTypeRequests.stream()
                                                .filter(req -> req.equals(stockRequest) && !req.getClientName().equals(stockRequest.getClientName()))
                                                .map(StockRequest::getClientName)
                                                .findFirst().orElse(null);
            if (stockRequest.getStockAction().equals("s")) {
                TransactionService.executeTransaction(stockRequest.getClientName(), clientName, stockRequest, clients);
            } else {
                TransactionService.executeTransaction(clientName, stockRequest.getClientName(), stockRequest, clients);
            }
            otherTypeRequests.remove(stockRequest);

        } else {
            sameTypeRequests.add(stockRequest);
        }
    }

//         Check request on existing equal purpose.
    public static boolean isRequestAvailableForExecute(StockRequest request, List<StockRequest> requests) {
        for (StockRequest request1 : requests) {
            if (request1.equals(request) && !request1.getClientName().equals(request.getClientName()))
                return true;
        }
        return false;
    }

//    Check the entered parameters of request
    public static void areRequestParametersCorrect(String[] param) throws WrongStockRequestException{
        if (param.length < 5
                || param[0] == null
                || param[0].equals("")
                || (!param[1].equals("s") && !param[1].equals("b"))
                || !List.of(StockNames.values()).contains(StockNames.valueOf(param[2]))) {
            throw new WrongStockRequestException();
        }
    }
}
