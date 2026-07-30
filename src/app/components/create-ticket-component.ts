import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { TicketCreateDto } from '../models/DTOs/TicketCreateDto';
import { TicketService } from '../services/ticket.service';

@Component({
    selector: 'app-create-ticket',
    standalone: true,
    imports: [FormsModule, CommonModule],
    templateUrl: './create-ticket.html',
    styleUrls: ['./create-ticket.css']
})
export class CreateTicketComponent implements OnInit {

    errorMessage = '';

    ticket: TicketCreateDto = {
        customerName: '',
        customerAddress: '',
        customerPhone: '',
        customerEmail: '',
        category: 'Internet',
        notes: '',
        proposedDate: ''
    };

    constructor(
        private ticketService: TicketService,
        private router: Router,
        private cdr: ChangeDetectorRef
    ) {}

    ngOnInit(): void {
        this.ticketService.getNextAvailableSlot().subscribe({
            next: (slot) => {
                const date = new Date(slot);

                this.ticket.proposedDate = slot.substring(0, 10);
                console.log("ticket date = " + this.ticket.proposedDate);

                this.cdr.detectChanges();
            },
            error: (err) => {
                console.error('Failed to load next available slot:', err);
            }
        });
    }
   createTicket(): void {
        this.errorMessage = '';

        this.ticketService.createTicket(this.ticket).subscribe({
            next: () => {
                console.log('Ticket created successfully');
                this.router.navigate(['/']);
            },
            error: (err) => {
                if (err.error?.message) {
                    this.errorMessage = err.error.message;
                } else if (typeof err.error === 'string') {
                    this.errorMessage = err.error;
                } else {
                    this.errorMessage = 'Failed to create ticket.';
                }
                this.cdr.detectChanges()
                console.error('Create ticket failed:', err);
            }
        });
    }
}
