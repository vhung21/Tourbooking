import {Component} from "@angular/core";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {Tours} from "../../../tours/tours.modal";
import {ActivatedRoute, Router} from "@angular/router";
import {TourService} from "../../../tours/tours.service";
import {HttpClient} from "@angular/common/http";
import {Hotels} from "../../../hotels/hotels.modal";

@Component({
  selector: 'jhi-tourCreate',
  templateUrl: './hotelCreate.component.html',
  styleUrl: './hotelCreate.component.scss',
  imports: [
    CommonModule,
    FormsModule,
    FaIconComponent,
  ]
})
export default class CreateHotelComponent{
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
    private toursService: TourService,
    private router: Router,
    private http: HttpClient,
  ) {}

  updateTour() {
    this.http.post(`/api/hotels`, this.hotelData).subscribe({
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
