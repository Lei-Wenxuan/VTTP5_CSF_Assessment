import { ComponentStore } from '@ngrx/component-store';
import { Cart, Menu } from './models';
import { Injectable } from '@angular/core';

const INIT_STATE: Cart = {
  menus: [],
};

@Injectable()
export class CartStore extends ComponentStore<Cart> {
  constructor() {
    super(INIT_STATE);
  }

  readonly menus$ = this.select((state) => state.menus);
  
  readonly addItemToCart = this.updater<Menu>(
    (store: Cart, itemToAdd: Menu) => {
      return {
        menus: [...store.menus, itemToAdd],
      } as Cart;
    }
  );

  readonly removeItemFromCart = this.updater<Menu>(
    (store: Cart, itemToAdd: Menu) => {
      this.deleteOldItem
      return {
        menus: [...store.menus, itemToAdd],
      } as Cart;
    }
  );

  readonly deleteOldItem = this.updater<Menu>(
    (store: Cart, oldItem: Menu) => {
      return {
        menus: store.menus.filter((menu: Menu) => menu.id !== oldItem.id)
      }
    }
  )

  readonly countTotalItemsInCart = this.select<number>((store: Cart) => {
    return store.menus.length;
  });

  // readonly getQuantity$ = (id: string) => {
  //   return this.select<Menu>
  //     ((slice: Cart) => slice.menus.filter())
  // }
  
  // readonly getQuantity = (menu) this.select<number>((store: Cart) => {
  //   return store.menus.select
  // });

  readonly totalPriceInCart = this.select<number>((store: Cart) => {
    let totalPrice = 0;
    store.menus.forEach((lineItem) => {
      totalPrice += lineItem.price;
    });
    return totalPrice;
  });

  readonly resetItems = this.updater<Menu[]>(
    (store: Cart, itemsToAdd: Menu[]) => {
      return {
        menus: itemsToAdd,
      } as Cart;
    }
  );
}
