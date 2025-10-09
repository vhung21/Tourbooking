import {Component} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {TourService} from "../../tours/tours.service";
import {FormsModule} from "@angular/forms";
import {CommonModule, NgClass} from "@angular/common";
import {RouterLink, RouterLinkActive} from "@angular/router";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";

@Component({
  selector: 'jhi-tourListing',
  templateUrl: './tourListing.component.html',
  styleUrl: './tourListing.component.scss',
  imports: [
    CommonModule,
    FormsModule,
    NgClass,
    RouterLink,
    RouterLinkActive,
  ]
})
export default class TourListingComponent {
  tours: any[] = [];
  filteredTours: any[] = [];
  searchTerm = '';
  pageSize = 10;

  constructor(
    private http: HttpClient,
    private toursService: TourService,
  ) {}

  ngOnInit(): void {
    this.toursService.getAllTours().subscribe(data => {
      this.tours = data;
      this.filteredTours = data;
      console.log(this.filteredTours);
    });
  }

  onPageSizeChange() {
    // (Nếu dùng phân trang thực, xử lý tại đây)
  }

  ngOnChanges(): void {
    this.applyFilter();
  }

  applyFilter() {
    this.filteredTours = this.tours.filter(tour =>
      tour.title.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }
}
