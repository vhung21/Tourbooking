import {Component} from "@angular/core";
import {CommonModule} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {Tours} from "../../../tours/tours.modal";
import {ActivatedRoute, Router} from "@angular/router";
import {TourService} from "../../../tours/tours.service";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'jhi-tourCreate',
  templateUrl: './tourCreate.component.html',
  styleUrl: './tourCreate.component.scss',
  imports: [
    CommonModule,
    FormsModule,
    FaIconComponent,
  ]
})
export default class EditTourComponent{
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
    inclusions: []
  };

  constructor(
    private route: ActivatedRoute,
    private toursService: TourService,
    private router: Router,
    private http: HttpClient,
  ) {}

  updateTour() {
    this.http.post(`/api/tours`, this.tourData).subscribe({
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
