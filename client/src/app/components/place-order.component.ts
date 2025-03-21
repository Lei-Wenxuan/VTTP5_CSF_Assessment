import { Component, inject, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CartStore } from '../cart.store';
import { Confirmation, Menu, Order } from '../models';
import { RestaurantService } from '../restaurant.service';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-place-order',
  standalone: false,
  templateUrl: './place-order.component.html',
  styleUrl: './place-order.component.css',
})
export class PlaceOrderComponent implements OnInit {
  // @Output()
  // confirmation = new Subject<Confirmation>();

  // TODO: Task 3
  private restaurantService = inject(RestaurantService);
  private cartStore = inject(CartStore);
  private router = inject(Router);

  private menus$ = this.cartStore.menus$;

  private fb: FormBuilder = inject(FormBuilder);

  protected form!: FormGroup;

  protected menus: Menu[] = [];
  protected totalPrice = 0;

  confirmation!: Confirmation;

  ngOnInit(): void {
    this.form = this.createForm();
    this.menus$.subscribe((menus) => {
      this.menus = menus;
      this.form.patchValue({ cart: this.menus });
      menus.forEach((i) => {
        this.totalPrice += i.price * i.quantity;
      });
    });
  }

  protected processForm() {
    const order: Order = this.form.value;
    console.info('>>> form: ', order);
    this.restaurantService.placeOrder(order).subscribe(
      (data: any) => {
        console.info('>>> data: ' + data);
        // this.confirmation.next(data);
        this.confirmation = data.message;
        console.info(this.confirmation);
        this.cartStore.resetItems;
        this.router.navigate(['/confirm-order']);
      },
      (error) => {
        const errorMessage = error.error?.message;
        alert(errorMessage);
      }
    );
  }

  protected invalid(): boolean {
    return this.form.invalid;
  }

  protected startOver() {
    this.cartStore.resetItems;
    this.router.navigate(['']);
  }

  private createForm(): FormGroup {
    return this.fb.group({
      username: this.fb.control<string>('', [Validators.required]),
      password: this.fb.control<string>('', [Validators.required]),
      items: this.fb.control<Menu[]>([]),
    });
  }
}
