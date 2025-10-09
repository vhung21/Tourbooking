import {Component, OnInit} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {TourService} from "../../../tours/tours.service";
import {FormsModule} from "@angular/forms";
import {CommonModule, NgClass} from "@angular/common";
import {Router, RouterLink, RouterLinkActive} from "@angular/router";
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
export default class TourListingComponent implements OnInit{
  tours: any[] = [];
  filteredTours: any[] = [];
  searchTerm = '';
  pageSize = 10;

  constructor(
    private http: HttpClient,
    private toursService: TourService,
    private router: Router
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

  editTour(id: number): void {
    this.router.navigate(['/admin/tour-management/edit', id]);
  }

  deleteTour(id: number): void {
    if (confirm('Bạn có chắc chắn muốn xóa tour này?')) {
      this.toursService.delete(id).subscribe({
        next: () => {
          alert('Xóa thành công!');
          window.location.reload();
        },
        error: err => alert('Xóa thất bại!'),
      });
    }
  }
}
