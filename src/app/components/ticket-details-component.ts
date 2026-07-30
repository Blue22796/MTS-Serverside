import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';

import { TicketViewDto } from '../models/DTOs/TicketViewDto';
import { TicketService } from '../services/ticket.service';


@Component({
    selector: 'app-ticket-details',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './ticket-details.html',
    styleUrls: ['./ticket-details.css']
})
export class TicketDetailsComponent implements OnInit {

    ticket?: TicketViewDto;

    errorMessage = '';


    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private ticketService: TicketService,
        private cdr: ChangeDetectorRef
    ) {}


    ngOnInit(): void {

        const id = Number(
            this.route.snapshot.paramMap.get('id')
        );


        this.ticketService.getTicket(id)
            .subscribe({

                next: ticket => {

                    this.ticket = ticket;

                    this.cdr.detectChanges();

                },

                error: err => {

                    console.error(err);

                    this.errorMessage =
                        'Failed to load ticket.';


                    this.cdr.detectChanges();
                    setTimeout(() => {
                      console.log("Hello");
                      this.router.navigate([
                        '/tickets',
                      ]);
                    }, 1000);
                                   }

            });

    }


    get canAssign(): boolean {

        return this.ticket?.status === 'PENDING';

    }


    get canReassign(): boolean {

        return this.ticket?.status === 'IN_PROGRESS';

    }


    get canCancel(): boolean {

        return this.ticket?.status === 'PENDING'
            || this.ticket?.status === 'IN_PROGRESS';

    }


    assign(): void {

        this.router.navigate([
            '/tickets',
            this.ticket!.id,
            'assign'
        ]);

    }


    reassign(): void {

        this.router.navigate([
            '/tickets',
            this.ticket!.id,
            'reassign'
        ]);

    }


    reschedule(): void {

        this.router.navigate([
            '/tickets',
            this.ticket!.id,
            'reschedule'
        ]);

    }


    cancel(): void {

        this.ticketService
            .cancelTicket(this.ticket!.id)
            .subscribe({

                next: () => {

                    this.router.navigate(['/']);

                },

                error: err => {

                    console.error(err);

                    this.errorMessage =
                        err.error || 'Failed to cancel ticket.';

                    this.cdr.detectChanges();

                }

            });

    }

}
