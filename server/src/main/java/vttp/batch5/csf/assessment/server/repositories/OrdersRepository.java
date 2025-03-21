package vttp.batch5.csf.assessment.server.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonArray;
import vttp.batch5.csf.assessment.server.Utils;
import vttp.batch5.csf.assessment.server.models.Menu;
import vttp.batch5.csf.assessment.server.models.Payment;
import vttp.batch5.csf.assessment.server.models.Success;

@Repository
public class OrdersRepository {

  @Autowired
  private MongoTemplate template;
  
  public static final String C_SERIES = "menus";

  public static final String F_NAME = "name";

  // TODO: Task 2.2
  // You may change the method's signature
  // Write the native MongoDB query in the comment below
  //
  //  db.menus.find({})
  //    .sort({ name: 1 })
  //
  public List<Menu> getMenu() {
    Query query = new Query()
        .with(Sort.by(Sort.Direction.ASC, F_NAME));   
        
    return template.find(query, Document.class, C_SERIES)
        .stream()
        .map(Utils::toMenu)
        .toList();
  }

  // TODO: Task 4
  // Write the native MongoDB query for your access methods in the comment below
  //
  // db.orders.insert({
  //   _id: 'abcd1234',
  //   order_id: 'abcd1234',
  //   payment_id: 'xyz789',
  //   username: 'fred',
  //   total: 23.10,
  //   timestamp: '23-dec',
  //   items: [
  //       { id:'xxx', price: 1.40, quantity: 2 },
  //       { id:'yyy', price: 2.40, quantity: 4 },
  //   ]
  // })
  //
  public Document insertSuccess(Payment p, Success s, JsonArray a) {
    Document toInsert = new Document();
    toInsert.put("_id", p.getOrderId());
    toInsert.put("order_id", p.getOrderId());
    toInsert.put("payment_id", s.getPaymentId());
    toInsert.put("username", p.getPayer());
    toInsert.put("timestamp", s.getOrderDate());

    List<String> items = new ArrayList<>();
    
    
    toInsert.put("items")
    
  }
  
}
