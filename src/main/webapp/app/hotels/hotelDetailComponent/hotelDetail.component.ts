import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, RouterLink} from "@angular/router";
import {HotelService} from "../hotels.service";
import {Hotels} from "../hotels.modal";
import {DecimalPipe, NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'jhi-hotel-detail',
  templateUrl: './hotelDetail.component.html',
  imports: [
    RouterLink,
    NgIf,
    DecimalPipe,
    NgForOf
  ],
  styleUrls: ['./hotelDetail.component.scss']
})
export class HotelDetailComponent implements OnInit{
  currentHotelId!: number;
  hotelId!: number;
  hotelData?: Hotels;
  selectedRoom: any;

  constructor(
    private route: ActivatedRoute,
    private hotelsService: HotelService,
  ){}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      if (idParam) {
        this.hotelId = +idParam;
        this.currentHotelId = this.hotelId;
        this.hotelsService.getById(this.hotelId).subscribe({
          next: res => {
            this.hotelData = res;
            console.log("Dữ liệu khách sạn:", this.hotelData);
          },
          error: err => console.error(err)
        });
      }
    });
  }
}
