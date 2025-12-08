import {ActivatedRoute, Router, RouterLink, RouterLinkActive} from '@angular/router';
import {Component, OnInit} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {CommonModule, NgClass} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {TourService} from "../../../tours/tours.service";
import {Tours} from "app/tours/tours.modal";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";

@Component({
  selector: 'jhi-tourUpdate',
  templateUrl: './tourUpdate.component.html',
  styleUrl: './tourUpdate.component.scss',
  imports: [
    CommonModule,
    FormsModule,
    FaIconComponent,
  ]
})
export default class EditTourComponent implements OnInit {
  tourId!: number;
  tourData: Tours = {
    tourName: '',
    description: '',
    price: 0,
    startDate: '',
    endDate: '',
    departures: '',
    destination: '',
    transportation: '',
    imageUrl: '',
    detail: {
      overview: '',
      childrenPolicy: '',
      bookingGuide: '',
      payment: '',
      cancellationPolicy: '',
      termsNotes: '',
      additionalInfo: ''
    },
    itineraries: [],
    inclusions: [],
    season: ''
  };

  constructor(
    private route: ActivatedRoute,
    private toursService: TourService,
    private router: Router,
    private http: HttpClient,
  ) {}

  ngOnInit(): void {
    this.tourId = Number(this.route.snapshot.paramMap.get('id'));
    this.toursService.getById(this.tourId).subscribe({
      next: res => {
        this.tourData = res.data;
        console.log("Dữ liệu tour:", this.tourData);
      },
      error: err => console.error(err)
    });
  }

  updateTour() {
    this.http.put(`/api/tours/${this.tourId}`, this.tourData).subscribe({
      next: () => {
        alert('Cập nhật tour thành công!');
        this.router.navigate(['/admin/tour-management/list']);
      },
      error: err => {
        console.error(err);
        alert('Cập nhật thất bại!');
      }
    });
  }

  addItinerary(): void {
    if (!this.tourData.itineraries) {
      this.tourData.itineraries = [];
    }
    this.tourData.itineraries.push({
      toursItineraryTitle: '',
      toursItineraryDescription: ''
    });
  }

  removeItinerary(index: number): void {
    if (!this.tourData || !this.tourData.itineraries) return;
    this.tourData.itineraries.splice(index, 1);
  }

  addInclusion(type: number): void {
    if (!this.tourData.inclusions) {
      this.tourData.inclusions = [];
    }
    this.tourData.inclusions.push({
      toursInclusionName: '',
      toursIncluded: type,
    });
  }

  removeInclusion(itemToRemove: any): void {
    if (!this.tourData.inclusions) return;
    this.tourData.inclusions = this.tourData.inclusions.filter(
      item => item !== itemToRemove
    );
  }

  get includedInclusions() {
    return this.tourData?.inclusions?.filter(i => i.toursIncluded === 1) ?? [];
  }

  get excludedInclusions() {
    return this.tourData?.inclusions?.filter(i => i.toursIncluded === 0) ?? [];
  }
}
