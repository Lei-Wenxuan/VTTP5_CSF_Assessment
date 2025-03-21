package vttp.batch5.csf.assessment.server;

import java.util.Date;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import vttp.batch5.csf.assessment.server.models.Customer;
import vttp.batch5.csf.assessment.server.models.Menu;
import vttp.batch5.csf.assessment.server.models.Payment;
import vttp.batch5.csf.assessment.server.models.Success;

public class Utils {

  public static Menu toMenu(Document doc) {
    Menu menu = new Menu();
    menu.setId((String) doc.get("_id"));
    menu.setName(doc.getString("name"));
    menu.setDescription(doc.getString("description"));
    menu.setPrice(doc.getDouble("price").floatValue());
    return menu;
  }

  public static JsonObject toJson(Menu menu) {
    return Json.createObjectBuilder()
        .add("id", menu.getId())
        .add("name", menu.getName())
        .add("description", menu.getDescription())
        .add("price", menu.getPrice())
        .build();
  }

  public static Customer toCustomer(JsonObject jo) {
    Customer c = new Customer();
    c.setUsername(jo.getString("username"));
    c.setPassword(jo.getString("password"));
    return c;
  }

  public static Payment toPayment(JsonObject jo) {
    String payer = jo.getString("username");
    String payee = "Lei Wenxuan";

    float payment = 0;
    JsonArray arrItems = jo.getJsonArray("items");

    for (int i = 0 ; i < arrItems.size(); i++) {
      JsonObject obj = arrItems.getJsonObject(i);
      payment += (float) obj.getJsonNumber("price").doubleValue();
  }

    return new Payment(payer, payee, payment);
  }

  public static JsonObject toJson(Payment payment) {
    return Json.createObjectBuilder()
        .add("order_id", payment.getOrderId())
        .add("payer", payment.getPayer())
        .add("payee", payment.getPayee())
        .add("payment", payment.getPayee())
        .build();
  }

  public static Success toSuccess(JsonObject jo, Payment p) {
    Success s = new Success();
    s.setOrderId(jo.getString("order_id"));
    s.setPaymentId(jo.getString("payment_id"));
    s.setOrderDate(toDate(jo.getInt("timestamp")));
    s.setTotal(p.getPayment());
    s.setUsername(p.getPayer());
    return s;    
  }
  
  public static JsonObject successJson(Success s) {
    return Json.createObjectBuilder()
      .add("orderId", s.getOrderId())
      .add("paymentId", s.getPaymentId())
      .add("total", s.getTotal())
      .add("timestamp", toMilli(s.getOrderDate()))
      .build();
  }

  public static Date toDate(long timestampMilli) {
    return new Date(timestampMilli);
  }

  public static long toMilli(Date date) {
    return date.getTime();
  }

}
