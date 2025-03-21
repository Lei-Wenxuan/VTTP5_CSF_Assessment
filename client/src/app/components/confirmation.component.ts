import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { Confirmation } from '../models';
import { PlaceOrderComponent } from './place-order.component';

@Component({
  selector: 'app-confirmation',
  standalone: false,
  templateUrl: './confirmation.component.html',
  styleUrl: './confirmation.component.css'
})
export class ConfirmationComponent implements OnInit {

  // TODO: Task 5
  // @Input({ required: true })
  // confirmation!: Confirmation

  @ViewChild(PlaceOrderComponent) placeOrderComponent!: PlaceOrderComponent;
  
  confirmation!: Confirmation;

  ngOnInit(): void {
    console.info(this.confirmation.timestamp)
    this.confirmation = this.placeOrderComponent.confirmation
  }
  
}
