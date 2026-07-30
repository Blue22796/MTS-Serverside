    import { Injectable } from "@angular/core";
    import { HttpClient } from "@angular/common/http";
    import { Observable } from "rxjs";

    import { TicketCreateDto } from "../models/DTOs/TicketCreateDto";
    import { TicketViewDto } from "../models/DTOs/TicketViewDto";
    import {TicketAssignDto} from "../models/DTOs/TicketAssignDto"
    import {TechnicianViewDto} from "../models/DTOs/TechnicianViewDto"
    import { TicketRescheduleDto } from "../models/DTOs/TicketRescheduleDto";
    @Injectable({
    providedIn: "root"
    })
    export class TicketService {
    private readonly apiUrl = "http://localhost:8080/api"
    //private readonly apiUrl = "http://10.1.1.105:8080/omarfadii/api";

    constructor(private http: HttpClient) {}

    getTickets(): Observable<TicketViewDto[]> {
        return this.http.get<TicketViewDto[]>(`${this.apiUrl}/tickets`);
    }

    getTicket(id: number): Observable<TicketViewDto> {
        return this.http.get<TicketViewDto>(`${this.apiUrl}/tickets/${id}`);
    }

    createTicket(ticket: TicketCreateDto): Observable<TicketViewDto> {
        return this.http.post<TicketViewDto>(
        `${this.apiUrl}/tickets`,
        ticket
        );
    }

    getNextAvailableSlot(): Observable<string> {
        return this.http.get<string>(
        `${this.apiUrl}/reservation/next-slot`
        );
    }

    getTechnicianSlots(id: number): Observable<string[]> {
        return this.http.get<string[]>(
        `${this.apiUrl}/reservation/technician-slots/${id}`
        );
    }
    assignTicket(dto: TicketAssignDto): Observable<any> {
                return this.http.put<any>(
                    `${this.apiUrl}/tickets/assign`,
                    dto
                );
            }
    reassignTicket(dto: TicketAssignDto): Observable<any> {
                return this.http.put<any>(
                    `${this.apiUrl}/tickets/re-assign`,
                    dto
                );
            }            
    getTechnicians(): Observable<TechnicianViewDto[]> {
        return this.http.get<TechnicianViewDto[]>(
            `${this.apiUrl}/technicians`
        );
    }
rescheduleTicket(dto: TicketRescheduleDto): Observable<any> {
    return this.http.put<any>(
        `${this.apiUrl}/tickets/reschedule`,
        dto
    );
}

getTicketSlots(id: number): Observable<string[]> {
    return this.http.get<string[]>(
        `${this.apiUrl}/reservation/ticket-slots/${id}`
    );
}
    cancelTicket(id : number) : Observable<any>{
        return this.http.put<any>(
            `${this.apiUrl}/tickets/cancel/` + id, {}
        )
    }
}