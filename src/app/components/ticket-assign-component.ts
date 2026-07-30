import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { TicketService } from '../services/ticket.service';
import { TicketAssignDto } from '../models/DTOs/TicketAssignDto';
import { TechnicianViewDto } from '../models/DTOs/TechnicianViewDto';


type TicketAssignmentMode = 'assign' | 'reassign';


@Component({
  selector: 'app-ticket-assignment',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './ticket-assign.html',
  styleUrls: ['./ticket-assign.css']
})
export class TicketAssignComponent implements OnInit {

  ticketId!: number;

  mode: TicketAssignmentMode = 'assign';

  errorMessage = '';

  technicians: TechnicianViewDto[] = [];

  selectedTechnicianId: number | null = null;

  selectedSlot = '';

  availableSlots: string[] = [];


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


    this.mode =
      this.route.snapshot.data['mode'] ?? 'assign';


    this.loadTechnicians();

  }


  private loadTechnicians(): void {

    this.ticketService
      .getTechnicians()
      .subscribe({

        next: (techs: TechnicianViewDto[]) => {

          this.technicians = techs;

          this.cdr.detectChanges();

        },

        error: (err) => {

          console.error(err);

          this.errorMessage =
            'Failed to load technicians.';

          this.cdr.detectChanges();

        }

      });

  }


  changeTechnician(): void {

    this.errorMessage = '';

    this.selectedSlot = '';

    this.availableSlots = [];


    if (!this.selectedTechnicianId) {

      this.cdr.detectChanges();

      return;

    }


    this.ticketService
      .getTechnicianSlots(this.selectedTechnicianId)
      .subscribe({

        next: (slots: string[]) => {

          this.availableSlots = slots;

          this.cdr.detectChanges();

        },

        error: (err) => {

          console.error(err);

          this.errorMessage =
            'No slots available for this technician.';

          this.cdr.detectChanges();

        }

      });

  }


  assignTicket(): void {

    this.errorMessage = '';


    if (
      this.selectedTechnicianId === null ||
      this.selectedSlot === ''
    ) {

      this.errorMessage =
        'Please select a technician and a time slot.';

      this.cdr.detectChanges();

      return;

    }


    const dto: TicketAssignDto = {

      ticketId: this.ticketId,

      technicianId: this.selectedTechnicianId,

      vistDate: this.selectedSlot

    };


    const request =
      this.mode === 'reassign'
        ? this.ticketService.reassignTicket(dto)
        : this.ticketService.assignTicket(dto);



    request.subscribe({

      next: (response) => {

        console.log(
          `${this.mode} successful:`,
          response
        );


        this.errorMessage = '';

        this.cdr.detectChanges();


        this.router.navigate(['/']);

      },


      error: (err) => {
        this.errorMessage =`${this.mode} failed: ${err.error || err.message || 'Unknown error'}`;

        console.error(this.errorMessage);


        this.cdr.detectChanges();

      }

    });

  }

}