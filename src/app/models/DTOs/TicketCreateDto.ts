export interface TicketCreateDto {
    customerName: string;
    customerAddress: string;
    customerPhone: string;
    customerEmail: string;
    category: string;
    notes?: string;
    proposedDate: string;
}
