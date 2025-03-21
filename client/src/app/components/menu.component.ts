import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { CartStore } from '../cart.store';
import { Menu } from '../models';
import { RestaurantService } from '../restaurant.service';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-menu',
  standalone: false,
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css',
})
export class MenuComponent implements OnInit {
  private restaurantService = inject(RestaurantService);
  private cartStore = inject(CartStore);
  private router = inject(Router);

  protected menus$!: Observable<Menu[]>;
  protected totalQuantity$!: Observable<number>;
  protected totalPrice$!: Observable<number>;

  // protected totalPrice = 0
  // protected totalQuantity = 0

  protected menus!: Menu[]

  protected items!: Array<string>
  
  // TODO: Task 2
  ngOnInit(): void {
    this.menus$ = this.restaurantService.getMenuItems();
    // this.menus$.subscribe(menus => {
    //   this.menus = menus
    // })
    this.totalQuantity$ = this.cartStore.countTotalItemsInCart;
    this.totalPrice$ = this.cartStore.totalPriceInCart;
  }

  getQuantity(m: Menu) {

  }
  
  toShowQuantity(m: Menu): boolean {
    if (false) {
      return false
    }
    return true
  }

  addItem(m: Menu) {
    const updatedMenu: Menu = {
      id: m.id,
      name: m.name,
      description: m.description,
      price: m.price,
      quantity: 1,
    };
    this.cartStore.addItemToCart(updatedMenu);
  }

  removeItem(m: Menu) {
    if (!m.quantity) {
      m.quantity = 0;
    }
    if (m.quantity > 0) {
      const updatedMenu: Menu = {
        id: m.id,
        name: m.name,
        description: m.description,
        price: m.price,
        quantity: m.quantity - 1,
      };
      this.cartStore.removeItemFromCart(updatedMenu);
    }
  }

}
