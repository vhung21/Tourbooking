import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DecimalPipe, NgForOf, NgIf } from '@angular/common';
import { LocationService } from '../entities/location/location.service';
import { TourLocation } from '../entities/location/location.modal';
import { Router, RouterLink } from '@angular/router';
import { TourService } from '../tours/tours.service';
import { HotelService } from './hotels.service';
import { Hotels } from './hotels.modal';
import { Tours } from '../tours/tours.modal';

@Component({
  selector: 'jhi-hotels',
  templateUrl: './hotels.component.html',
  styleUrl: './hotels.component.scss',
  imports: [FormsModule, NgForOf, NgIf, ReactiveFormsModule, FormsModule, RouterLink, DecimalPipe],
})
export default class HotelsComponent implements OnInit {
  private readonly router = inject(Router);
  searchForm: FormGroup;
  destinationSuggestions: TourLocation[] = [];
  selectedLocationId: number | null = null;
  hotels: Hotels[] = [];
  firstHotels: any;
  currentPage = 0;
  pageSize = 3;
  visibleHotel: Hotels[] = [];
  intervalId: any;
  show = true;
  activeIndex = 0;
  testimonials = [
    {
      name: 'Minh Anh',
      text: '“Chuyến đi thật tuyệt! Mọi thứ được sắp xếp chu đáo và tôi cảm giác rất an tâm khi đồng hành cùng dịch vụ.”',
      avatar: 'https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?auto=format&fit=crop&w=200&q=60',
    },
    {
      name: 'Quang Huy',
      text: '“Tôi bất ngờ vì đội ngũ hỗ trợ quá nhiệt tình. Lịch trình rõ ràng, gợi ý địa điểm rất hợp gu. Rất đáng để trải nghiệm!”',
      avatar: 'https://images.unsplash.com/photo-1599566150163-29194dcaad36?auto=format&fit=crop&w=200&q=60',
    },
    {
      name: 'Thu Trang',
      text: '“Mình đi du lịch một mình nhưng không hề cảm thấy lạc lõng. Nhân viên tư vấn rất dễ thương và đưa nhiều mẹo hay.”',
      avatar: 'https://images.unsplash.com/photo-1529626455594-4ff0802cfb7e?auto=format&fit=crop&w=200&q=60',
    },
  ];

  constructor(
    private fb: FormBuilder,
    private hotelService: HotelService,
    private locationService: LocationService,
  ) {
    this.searchForm = this.fb.group({
      city: [''],
      rating: [''],
    });
  }

  ngOnInit() {
    this.hotelService.getTopHotel().subscribe({
      next: data => {
        this.hotels = data;
        this.firstHotels = data[0];
        this.showPage(0);
        console.log('tours', this.hotels);
        console.log('firstTour', this.firstHotels);
      },
    });
    this.startSlider();
  }

  showPage(page: number) {
    this.currentPage = page;
    const start = page * this.pageSize;
    const end = start + this.pageSize;
    this.visibleHotel = this.hotels.slice(start, end);
  }

  onSubmit(): void {
    const formValue = this.searchForm.value;
    this.router.navigate(['hotels/list'], {
      queryParams: {
        city: formValue.city,
        rating: formValue.rating,
      },
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
      next: data => {
        this.destinationSuggestions = data;
      },
      error: err => {
        console.error('Error fetching suggestions:', err);
      },
    });
  }

  selectDestination(point: TourLocation): void {
    console.log('Selected destination:', point.name);
    this.searchForm.get('destination')?.setValue(point.name);
    this.selectedLocationId = point.id;
    this.destinationSuggestions = [];
  }

  startSlider() {
    this.intervalId = setInterval(() => {
      this.show = false;

      setTimeout(() => {
        this.activeIndex = (this.activeIndex + 1) % this.testimonials.length;
        this.show = true;
      }, 200);
    }, 3000);
  }

  nextPage() {
    if ((this.currentPage + 1) * this.pageSize < this.hotels.length) {
      this.showPage(this.currentPage + 1);
    }
  }

  prevPage() {
    if (this.currentPage > 0) {
      this.showPage(this.currentPage - 1);
    }
  }

  viewAll(): void {
    this.router.navigate(['hotels/list']);
  }

  get currentTestimonial() {
    return this.testimonials[this.activeIndex];
  }

  manualSelect(i: number) {
    this.show = false;
    setTimeout(() => {
      this.activeIndex = i;
      this.show = true;
    }, 200);
  }
}
