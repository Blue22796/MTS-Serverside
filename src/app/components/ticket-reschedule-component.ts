import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { TicketService } from '../services/ticket.service';
import { TicketRescheduleDto } from '../models/DTOs/TicketRescheduleDto';


@Component({
  selector: 'app-ticket-reschedule',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './ticket-reschedule.html',
  styleUrls: ['./ticket-reschedule.css']
})
export class TicketRescheduleComponent implements OnInit {


  ticketId!: number;

  errorMessage = '';

  availableSlots: string[] = [];

  selectedSlot = '';


  constructor(
    private route: ActivatedRoute,
    private ticketService: TicketService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}


  ngOnInit(): void {

    this.ticketId = Number(
      this.route.snapshot.paramMap.get('id')
    );


    this.loadSlots();

  }


  private loadSlots(): void {

    this.ticketService
      .getTicketSlots(this.ticketId)
      .subscribe({

        next: (slots: string[]) => {

          this.availableSlots = slots;

          this.cdr.detectChanges();

        },

        error: (err) => {

          console.error(err);

          this.errorMessage =
            'Failed to load available slots.';

          this.cdr.detectChanges();

        }

      });

  }


  rescheduleTicket(): void {

    this.errorMessage = '';


    if (this.selectedSlot === '') {

      this.errorMessage =
        'Please select a time slot.';

      this.cdr.detectChanges();

      return;

    }


    const dto: TicketRescheduleDto = {

      ticketId: this.ticketId,

      visitDate: this.selectedSlot

    };


    this.ticketService
      .rescheduleTicket(dto)
      .subscribe({

        next: (response) => {

          console.log(
            'Reschedule successful:',
            response
          );


          this.router.navigate(['/']);

        },


        error: (err) => {

          console.error(
            'Reschedule failed:',
            err
          );


          this.errorMessage =
            `Reschedule failed: ${
              err.error || err.message || 'Unknown error'
            }`;


          this.cdr.detectChanges();

        }

      });

  }

}