export interface TicketViewDto {
    id: number;
    customerName: string;
    customerAddress: string;
    customerPhone: string;
    category: string;
    notes?: string;
    proposedDate: string;
    scheduledDate?: string;
    calls: number;
    creationDate: string;
    technicianName?: string;
    status: string;
}
