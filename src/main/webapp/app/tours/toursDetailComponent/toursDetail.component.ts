import {Component, OnInit} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {TourService} from "../tours.service";
import {ActivatedRoute} from "@angular/router";
import {Tours} from "../tours.modal";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-tour-detail',
  templateUrl: './toursDetail.component.html',
  imports: [
    NgIf
  ],
  styleUrls: ['./toursDetail.component.scss']
})
export class ToursDetailComponent implements OnInit {
  tourId!: number;
  tourData?: Tours;

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private toursService: TourService
  ) {}

  ngOnInit() {
    this.tourId = Number(this.route.snapshot.paramMap.get('id'));
    this.toursService.getById(this.tourId).subscribe({
      next: res => {
        this.tourData = res.data;
        console.log("Dữ liệu tour:", this.tourData);
        },
      error: err => console.error(err)
    });
  }
}
