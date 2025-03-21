package vttp.batch5.csf.assessment.server.controllers;

import java.io.StringReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.validation.ValidationException;
import vttp.batch5.csf.assessment.server.Utils;
import vttp.batch5.csf.assessment.server.models.Customer;
import vttp.batch5.csf.assessment.server.models.Payment;
import vttp.batch5.csf.assessment.server.models.Success;
import vttp.batch5.csf.assessment.server.services.RestaurantService;

@RestController
@RequestMapping(path="/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

  @Autowired
  private RestaurantService restaurantService;

  // TODO: Task 2.2
  // You may change the method's signature
  @GetMapping(path="/menu")
  public ResponseEntity<String> getMenus() {
    List<JsonObject> menus = restaurantService.getMenu().stream()
        .map(Utils::toJson)
        .toList();
    return ResponseEntity.ok(Json.createArrayBuilder(menus).build().toString());    
  }

  // TODO: Task 4
  // Do not change the method's signature
  @PostMapping("/food_order")
  public ResponseEntity<String> postFoodOrder(@RequestBody String payload) {
    JsonObject jo = Json.createReader(new StringReader(payload)).readObject();
    
    Customer customer = Utils.toCustomer(jo);
    JsonArray arrItems = jo.getJsonArray("items");
    try {
      if (!restaurantService.validateCustomer(customer)) {
        System.out.println("Validation error");
        throw new ValidationException("Invalid username and/or password");
      }

      Payment payment = Utils.toPayment(jo);

      JsonObject successJson = restaurantService.makePayment(payment, arrItems);
      
      return ResponseEntity.ok(Json.createObjectBuilder()
          .add("message", successJson.toString())
          .build()
          .toString());
    } catch (ValidationException e) {
      return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(Json.createObjectBuilder()
          .add("message", e.getMessage())
          .build()
          .toString());
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(Json.createObjectBuilder()
          .add("message", e.getMessage())
          .build()
          .toString());
    }

    
    // Order order = Utils.toOrder(payload);
    // try {
    //   if (poSvc.createNewPurchaseOrder(order))
    //     return ResponseEntity.ok(Json.createObjectBuilder()
    //         .add("orderId", order.getOrderId())
    //         .build()
    //         .toString());
    // } catch (Exception e) {
    //   return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(Json.createObjectBuilder()
    //       .add("message", e.getMessage())
    //       .build()
    //       .toString());
    // }
    // return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(Json.createObjectBuilder()
    //     .add("message", "Something is broken, check OrderController")
    //     .build()
    //     .toString());
  }
}
