import { Component, Input } from '@angular/core';
import { Confirmation } from '../models';

@Component({
  selector: 'app-confirmation',
  standalone: false,
  templateUrl: './confirmation.component.html',
  styleUrl: './confirmation.component.css'
})
export class ConfirmationComponent {

  // TODO: Task 5

  @Input({ required: true })
  confirmation!: Confirmation

}
