import { ChangeDetectorRef, Component } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Router, RouterLink, RouterLinkActive } from "@angular/router";

@Component({
    selector: "app-nav",
    standalone: true,
    templateUrl: "./navbar.html",
    styleUrls: ["./navbar.css"],
    imports: [RouterLink, RouterLinkActive, FormsModule]
})
export class navbar {

    ticketSearchId = "";

    constructor(
        private router: Router,
        private cdr: ChangeDetectorRef
    ) {}

    searchTicket(): void {
        const id = this.ticketSearchId;

        if (!id) {
            return;
        }

        this.router.navigate(["/tickets", id]).then(() => {
            this.ticketSearchId = "";
            this.cdr.detectChanges();
        });
    }
}
