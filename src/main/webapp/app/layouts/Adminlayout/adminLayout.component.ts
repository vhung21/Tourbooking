import {Component} from "@angular/core";
import {RouterLink, RouterLinkActive, RouterOutlet} from "@angular/router";
import NavbarComponent from "../navbar/navbar.component";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";

@Component({
  selector: 'app-admin-layout',
  templateUrl: './adminLayout.component.html',
  imports: [
    RouterOutlet,
    RouterLink,
    RouterLinkActive,
    FaIconComponent,
  ],
  styleUrls: ['./adminLayout.component.scss']
})
export class AdminLayoutComponent {
}
