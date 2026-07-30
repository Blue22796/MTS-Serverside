import { Routes } from '@angular/router';

import { TicketsComponent } from './components/tickets.component';
import { CreateTicketComponent } from './components/create-ticket-component';
import { TicketDetailsComponent } from './components/ticket-details-component';
import {TicketAssignComponent} from './components/ticket-assign-component'
import {TicketRescheduleComponent} from './components/ticket-reschedule-component'
export const routes: Routes = [

    {
        path: '',
        redirectTo: 'tickets',
        pathMatch: 'full'
    },

    {
        path: 'tickets',
        component: TicketsComponent
    },

    {
        path: 'create-ticket',
        component: CreateTicketComponent
    },
    {
        path: 'tickets/:id/assign',
        component: TicketAssignComponent,
        data : {
            mode: 'assign'
        }
    },
    {
        path: 'tickets/:id/reassign',
        component: TicketAssignComponent,
        data : {
            mode: 'reassign'
        }
    },
    {
    path: 'tickets/:id/reschedule',
    component: TicketRescheduleComponent
    },
    {
        path: 'tickets/:id',
        component: TicketDetailsComponent
    },
    {
        path: '**',
        redirectTo: 'tickets'
    }
];