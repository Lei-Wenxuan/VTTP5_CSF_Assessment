package vttp.batch5.csf.assessment.server;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp.batch5.csf.assessment.server.models.Menu;

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
}
