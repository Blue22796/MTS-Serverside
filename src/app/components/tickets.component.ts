import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { FormsModule } from '@angular/forms';
import { TicketViewDto } from '../models/DTOs/TicketViewDto';
import { TicketService } from '../services/ticket.service';

@Component({
    selector: 'app-tickets',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './tickets.html',
    styleUrls: ['./tickets.css']
})
export class TicketsComponent implements OnInit {

    tickets: TicketViewDto[] = [];

    constructor(
        private ticketService: TicketService,
        private cdr: ChangeDetectorRef,
        private router: Router
    ) {}

    ngOnInit(): void {
        this.loadTickets();
    }

    loadTickets(): void {
        this.ticketService.getTickets().subscribe({
            next: (tickets) => {
                this.tickets = tickets;
                this.cdr.detectChanges();
            },
            error: (err) => console.error(err)
        });
    }

    openTicket(ticketId: number): void {
        this.router.navigate(['/tickets', ticketId]);
    }
}
