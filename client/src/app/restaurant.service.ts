import { HttpClient } from "@angular/common/http"
import { inject } from "@angular/core"
import { Observable } from "rxjs"
import { Menu, Order } from "./models"

export class RestaurantService {

  private http = inject(HttpClient)

  // TODO: Task 2.2
  // You change the method's signature but not the name
  getMenuItems(): Observable<Menu[]> {
    return this.http.get<Menu[]>('/api/menu')
  }

  // TODO: Task 3.2
  placeOrder(order: Order) {
    return this.http.post<Order>('/api/food_order', order)
  }
}
