package vttp.batch5.csf.assessment.server.services;

import java.io.StringReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

import org.springframework.http.HttpHeaders;
import vttp.batch5.csf.assessment.server.Utils;
import vttp.batch5.csf.assessment.server.models.Customer;
import vttp.batch5.csf.assessment.server.models.Menu;
import vttp.batch5.csf.assessment.server.models.Payment;
import vttp.batch5.csf.assessment.server.models.Success;
import vttp.batch5.csf.assessment.server.repositories.OrdersRepository;
import vttp.batch5.csf.assessment.server.repositories.RestaurantRepository;

@Service
public class RestaurantService {

  @Autowired
  private OrdersRepository ordersRepo;

  @Autowired
  private RestaurantRepository restaurantRepository;

  RestTemplate restTemplate = new RestTemplate();

  public static final String PAYMENT_BASE_URI = "https://payment-service-production-a75a.up.railway.app/api/payment";

  // TODO: Task 2.2
  // You may change the method's signature
  public List<Menu> getMenu() {
    return ordersRepo.getMenu();
  }

  // TODO: Task 4
  public boolean validateCustomer(Customer c) {
    return restaurantRepository.validateCustomer(c);
  }

  public JsonObject makePayment(Payment payment, JsonArray arrItems) throws Exception {
    String reqPayload = Utils.toJson(payment).toString();

    HttpHeaders headers = new HttpHeaders();
    headers.set("Content-Type", "application/json");
    headers.set("Accept", "application/json");
    headers.set("X-Authenticate", payment.getPayer());

    RequestEntity<String> req = RequestEntity.post(PAYMENT_BASE_URI)
        .headers(headers)
        .body(reqPayload);

    String response = restTemplate.exchange(req, String.class).getBody();

    JsonObject jo = Json.createReader(new StringReader(response)).readObject();

    Success success = Utils.toSuccess(jo, payment);
    
    restaurantRepository.insertSuccess(success);
    ordersRepo.insertSuccess(payment, success, arrItems);
    
    return Utils.successJson(success);
  }

}
