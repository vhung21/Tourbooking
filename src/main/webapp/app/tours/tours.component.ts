import {Component, inject, OnDestroy, OnInit} from "@angular/core";
import SharedModule from "../shared/shared.module";
import {Router, RouterModule} from "@angular/router";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {TourService} from "./tours.service";
import {Tours} from "./tours.modal";
import {LocationService} from "../entities/location/location.service";
import {TourLocation} from "../entities/location/location.modal";

@Component({
  selector: 'jhi-tours',
  templateUrl: './tours.component.html',
  styleUrl: './tours.component.scss',
  imports: [SharedModule, RouterModule, FormsModule,ReactiveFormsModule],
})

export default class ToursComponent implements OnInit, OnDestroy{
  private readonly router = inject(Router);
  searchForm: FormGroup;
  departureSuggestions: TourLocation[] = [];
  destinationSuggestions: TourLocation[] = [];
  selectedLocationId: number | null = null;

  tours: Tours[] = [];
  visibleTours: Tours[] = [];
  currentPage = 0;
  pageSize = 3;

  constructor(
    private http: HttpClient,
    private toursService: TourService,
    private fb: FormBuilder,
    private locationService: LocationService
    ) {
    this.searchForm = this.fb.group({
      departure: [''],
      destination: [''],
      departure_date: [''],
      budget: ['']
    });
  }

  images = [
    // 'content/images/toursImg/tourImg.svg',
    // 'content/images/Tour_du_lich_chau_a_img.svg',
    // 'content/images/Tour_du_lich_chau_au.svg',
  ];
  currentIndex = 0;
  intervalId: any;

  ngOnInit() {
    this.toursService.getTopTours().subscribe({
      next: (data) => {
        this.tours = data;
        this.showPage(0);
      },
    });
    this.intervalId = setInterval(() => {
      this.nextSlide();
    }, 3000);
  }

  showPage(page: number) {
    this.currentPage = page;
    const start = page * this.pageSize;
    const end = start + this.pageSize;
    this.visibleTours = this.tours.slice(start, end);
  }

  nextPage() {
    if ((this.currentPage + 1) * this.pageSize < this.tours.length) {
      this.showPage(this.currentPage + 1);
    }
  }

  prevPage() {
    if (this.currentPage > 0) {
      this.showPage(this.currentPage - 1);
    }
  }

  ngOnDestroy(): void {
    if (this.intervalId) clearInterval(this.intervalId);
  }

  nextSlide() {
    this.currentIndex = (this.currentIndex + 1) % this.images.length;
  }

  onInputChangeDeparture(event: Event): void {
    const input = event.target as HTMLInputElement;
    const term = input.value;

    if (!term || term.trim().length < 2) {
      this.departureSuggestions = [];
      return;
    }

    this.locationService.getSuggestions(term).subscribe({
      next: (data) => {
        this.departureSuggestions = data;
      },
      error: (err) => {
        console.error('Error fetching suggestions:', err);
      }
    });
  }

  onInputChangeDestinetion(event: Event): void {
    const input = event.target as HTMLInputElement;
    const term = input.value;

    if (!term || term.trim().length < 2) {
      this.destinationSuggestions = [];
      return;
    }

    this.locationService.getSuggestions(term).subscribe({
      next: (data) => {
        this.destinationSuggestions = data;
      },
      error: (err) => {
        console.error('Error fetching suggestions:', err);
      }
    });
  }

  selectDeparture(point: TourLocation): void {
      console.log('Selected departure:', point.name);
      this.searchForm.get('departure')?.setValue(point.name);
      this.selectedLocationId = point.id;
      this.departureSuggestions = [];
  }

  selectDestination(point: TourLocation): void {
      console.log('Selected destination:', point.name);
      this.searchForm.get('destination')?.setValue(point.name);
      this.selectedLocationId = point.id;
      this.destinationSuggestions = [];
  }

  onSubmit(): void {
    const formValue = this.searchForm.value;
    this.router.navigate(['tours/list'], {
      queryParams: {
        departure: formValue.departure,
        destination: formValue.destination,
        date: formValue.departure_date,
        budget: formValue.budget
      }
    });
  }


}
