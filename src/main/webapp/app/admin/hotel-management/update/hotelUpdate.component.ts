import {ActivatedRoute, Router, RouterLink, RouterLinkActive} from '@angular/router';
import {Component, OnInit} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {CommonModule, NgClass} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {HotelService} from "../../../hotels/hotels.service";
import {Hotels, Rooms} from "../../../hotels/hotels.modal";

@Component({
  selector: 'jhi-tourUpdate',
  templateUrl: './hotelUpdate.component.html',
  styleUrl: './hotelUpdate.component.scss',
  imports: [
    CommonModule,
    FormsModule,
    FaIconComponent,
  ]
})
export default class EditHotelComponent implements OnInit {
  hotelId!: number;
  hotelData: Hotels = {
    name: '',
    address: '',
    city: '',
    rating: '',
    imageUrl: '',
    phone: '',
    description: '',
    rooms: []
  };

  constructor(
    private route: ActivatedRoute,
    private hotelService: HotelService,
    private router: Router,
    private http: HttpClient,
  ) {}

  ngOnInit(): void {
    this.hotelId = Number(this.route.snapshot.paramMap.get('id'));
    this.hotelService.getById(this.hotelId).subscribe({
      next: res => {
        this.hotelData = res;
        console.log("Dữ liệu khách sạn:", this.hotelData);
      },
      error: err => console.error(err)
    });
  }

  updateTour() {
    this.http.put(`/api/hotels/${this.hotelId}`, this.hotelData).subscribe({
      next: () => {
        alert('Cập nhật tour thành công!');
        this.router.navigate(['/admin/hotel-management/list']);
      },
      error: err => {
        console.error(err);
        alert('Cập nhật thất bại!');
      }
    });
  }

  addRoom(): void {
    if (!this.hotelData.rooms) {
      this.hotelData.rooms = [];
    }
    this.hotelData.rooms.push({
      roomType: '',
      price: '',
      description: '',
      imageUrl:'',
    });
  }

  removeRoom(index: number): void {
    if (!this.hotelData || !this.hotelData.rooms) return;
    this.hotelData.rooms.splice(index, 1);
  }
}
