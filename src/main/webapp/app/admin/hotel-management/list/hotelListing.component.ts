import {Component, OnInit} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {CommonModule, NgClass} from "@angular/common";
import {Router, RouterLink, RouterLinkActive} from "@angular/router";
import {HotelService} from "../../../hotels/hotels.service";

@Component({
  selector: 'jhi-hotelListing',
  templateUrl: './hotelListing.component.html',
  styleUrl: './hotelListing.component.scss',
  imports: [
    CommonModule,
    FormsModule,
    RouterLink,
    RouterLinkActive,
  ]
})
export default class HotelListingComponent implements OnInit{
  hotels: any[] = [];
  filteredHotels: any[] = [];
  searchTerm = '';
  pageSize = 10;

  constructor(
    private http: HttpClient,
    private hotelService: HotelService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.hotelService.getAllHotels().subscribe(data => {
      this.hotels = data;
      this.filteredHotels = data;
      console.log(this.filteredHotels);
    });
  }

  onPageSizeChange() {
    // (Nếu dùng phân trang thực, xử lý tại đây)
  }

  ngOnChanges(): void {
    this.applyFilter();
  }

  applyFilter() {
    this.filteredHotels = this.hotels.filter(hotel =>
      hotel.title.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  editTour(id: number): void {
    this.router.navigate(['/admin/hotel-management/edit', id]);
  }

  deleteTour(id: number): void {
    if (confirm('Bạn có chắc chắn muốn xóa tour này?')) {
      this.hotelService.delete(id).subscribe({
        next: () => {
          alert('Xóa thành công!');
          window.location.reload();
        },
        error: err => alert('Xóa thất bại!'),
      });
    }
  }
}
