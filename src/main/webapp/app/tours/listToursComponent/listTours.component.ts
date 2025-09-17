import {Component, inject, OnInit} from "@angular/core";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {TourService} from "../tours.service";
import {DecimalPipe, NgForOf, NgIf} from "@angular/common";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TourLocation} from "../../entities/location/location.modal";
import {LocationService} from "../../entities/location/location.service";

@Component({
  selector: 'app-list-tour',
  templateUrl: './listTours.component.html',
  standalone: true,
  imports: [
    DecimalPipe,
    NgIf,
    NgForOf,
    FormsModule,
    ReactiveFormsModule,
    RouterLink
  ],
  styleUrls: ['./listTours.component.scss']
})
export class ListToursComponent implements OnInit {
  private readonly router = inject(Router);
  allTours: any[] = [];
  tours: any[] = [];
  searchForm: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private toursService: TourService,
    private fb: FormBuilder,
  ) {
    this.searchForm = this.fb.group({
      departure: [''],
      destination: [''],
      departure_date: [''],
      budget: ['']
    });
  }

  ngOnInit(): void {
    this.toursService.getAllTours().subscribe(data => {
      this.allTours = data;
      this.route.queryParams.subscribe(params => {
        this.filterTours(
          params['departure'],
          params['destination'],
          params['date'],
          params['budget']);
      });
    });
  }

  filterTours(departure?: string,destination?: string, date?: string, budget?: string): void {
    console.log('Filter params:', { departure, destination, date, budget });
    console.log('Budget value:', JSON.stringify(budget));
    console.log('allTours length:', this.allTours.length);
    this.tours = this.allTours.filter(t => {
      let match = true;
      if (departure) {
        match = match && t.departures.toLowerCase().includes(departure.toLowerCase());
      }

      if (destination) {
        match = match && t.destination.toLowerCase().includes(destination.toLowerCase());
      }

      // lọc theo ngày (nếu có chọn)
      if (date) {
        const selectedDate = new Date(date);
        match = match && new Date(t.startDate) > selectedDate;
      }

      // lọc theo ngân sách
      if (budget) {
        if (budget === 'under_10') {
          match = match && t.price < 3000000;
        } else if (budget === '10_25') {
          match = match && t.price >= 10000000 && t.price <= 25000000;
        } else if (budget === 'above_25') {
          match = match && t.price > 25000000;
        }
      }
      return match;
    });
  }

  onSubmit(): void {
    const formValue = this.searchForm.value;
    this.router.navigate(['tours/list'], {
      queryParams: {
        departure: formValue.departureSuggestions,
        destination: formValue.destinationSuggestions,
        date: formValue.departure_date,
        budget: formValue.budget
      }
    });
  }
}
