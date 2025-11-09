import {Component, inject, OnInit} from "@angular/core";
import {FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {HotelService} from "../hotels.service";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {DecimalPipe, NgForOf, NgIf, UpperCasePipe} from "@angular/common";
import {match} from "cypress/types/minimatch";

@Component({
  selector: 'jhi-listHotels',
  templateUrl: './listHotels.component.html',
  styleUrl: './listHotels.component.scss',
  imports: [
    RouterLink,
    ReactiveFormsModule,
    DecimalPipe,
    NgForOf,
    NgIf,
  ]
})
export class ListHotelsComponent implements OnInit{
  private readonly router = inject(Router);
  searchForm: FormGroup;
  allHotels: any[] = [];
  hotels: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private hotelService: HotelService,
    private fb: FormBuilder,
  ) {
    this.searchForm = this.fb.group({
      city: [''],
      rating: ['']
    });
  }

  ngOnInit(): void {
    this.hotelService.getAllHotels().subscribe((data: any[]) => {
      this.allHotels = data;
      this.route.queryParams.subscribe(params => {
        this.filterHotels(
          params['city'],
          params['rating']);
      });
    });
  }

  filterHotels(city?: string, rating?: string): void {
    console.log('Filter params:', {city, rating});
    this.hotels = this.allHotels.filter(t => {
      let match = true;
      if (city) {
        match = match && t.city.toLowerCase().includes(city.toLowerCase());
      }
      if (rating) {
        match = match && t.rating.includes(rating);
      }
      return match;
    });
    console.log(this.hotels);
  }

  onSubmit(): void {
    const formValue = this.searchForm.value;
    this.router.navigate(['hotels/list'], {
      queryParams: {
        city: formValue.city,
        rating: formValue.rating
      }
    });
  }
}
