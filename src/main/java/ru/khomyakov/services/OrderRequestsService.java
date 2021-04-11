package ru.khomyakov.services;

import ru.khomyakov.domain.StockNames;
import ru.khomyakov.domain.StockRequest;
import ru.khomyakov.exceptions.WrongClientAccountException;
import ru.khomyakov.exceptions.WrongStockRequestException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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
                if (requestParts.length < 5 || requestParts[0] == null || requestParts[0].equals("") || (requestParts[1].equals("s") || requestParts[1].equals("b"))) {
                    throw new WrongStockRequestException();
                }
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
                    addToListOrExecuteRequest(buyersRequests, sellersRequests, stockRequest);
                    break;
                case ("s") :
                    addToListOrExecuteRequest(sellersRequests, buyersRequests, stockRequest);
                    break;
            }
        }
    }

//        If request not exist yet add it in appropriate list. In other case make transaction
    private static void addToListOrExecuteRequest(List<StockRequest> sameTypeRequests, List<StockRequest> otherTypeRequests, StockRequest stockRequest) {
        if (isSuchRequest(stockRequest, otherTypeRequests)) {
            String clientName = otherTypeRequests.stream().filter(req -> req.equals(stockRequest)).map(StockRequest::getClientName).findFirst().orElse(null);
            if (clientName != null && !clientName.equals(stockRequest.getClientName())) {
                TransactionService.executeTransaction(stockRequest.getClientName(), clientName, stockRequest);
                otherTypeRequests.remove(stockRequest);
            } else if (clientName != null) {
                sameTypeRequests.add(stockRequest);
            }
        } else {
            sameTypeRequests.add(stockRequest);
        }
    }

//         Check request on existing equal purpose.
    private static boolean isSuchRequest(StockRequest request, List<StockRequest> requests) {
        return requests.contains(request);
    }
}
