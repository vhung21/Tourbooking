import {Component, inject} from "@angular/core";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {LocationService} from "../entities/location/location.service";
import {TourLocation} from "../entities/location/location.modal";
import {Router} from "@angular/router";

@Component({
  selector: 'jhi-hotels',
  templateUrl: './hotels.component.html',
  styleUrl: './hotels.component.scss',
  imports: [
    FormsModule,
    NgForOf,
    NgIf,
    ReactiveFormsModule,
    FormsModule
  ]
})
export default class HotelsComponent{
  private readonly router = inject(Router);
  searchForm: FormGroup;
  destinationSuggestions: TourLocation[] = [];
  selectedLocationId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private locationService: LocationService
  ) {
    this.searchForm = this.fb.group({
      city: [''],
      rating: ['']
    });
  }

  onSubmit(): void {
    const formValue = this.searchForm.value;
    this.router.navigate(['hotels/list'], {
      queryParams: {
        city: formValue.city,
        rating: formValue.rating
      }
    });
    console.log(this.searchForm.value);
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

  selectDestination(point: TourLocation): void {
    console.log('Selected destination:', point.name);
    this.searchForm.get('destination')?.setValue(point.name);
    this.selectedLocationId = point.id;
    this.destinationSuggestions = [];
  }
}
