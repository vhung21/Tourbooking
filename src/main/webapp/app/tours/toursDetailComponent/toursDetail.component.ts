import {Component, OnInit} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {TourService} from "../tours.service";
import {ActivatedRoute} from "@angular/router";
import {Itinerary, Tours} from "../tours.modal";
import {CommonModule, NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-tour-detail',
  templateUrl: './toursDetail.component.html',
  standalone: true,
  imports: [
    NgIf,
    NgForOf,
    [CommonModule]
  ],
  styleUrls: ['./toursDetail.component.scss']
})
export class ToursDetailComponent implements OnInit {
  tourId!: number;
  tourData?: Tours;
  isOpen: boolean[] = [];

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

  toggleAccordion(index: number) {
    this.isOpen[index] = !this.isOpen[index];
  }
}
