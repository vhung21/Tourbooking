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

  testimonials = [
    {
      name: "Minh Anh",
      text: "“Chuyến đi thật tuyệt! Mọi thứ được sắp xếp chu đáo và tôi cảm giác rất an tâm khi đồng hành cùng dịch vụ.”",
      avatar: "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?auto=format&fit=crop&w=200&q=60"
    },
    {
      name: "Quang Huy",
      text: "“Tôi bất ngờ vì đội ngũ hỗ trợ quá nhiệt tình. Lịch trình rõ ràng, gợi ý địa điểm rất hợp gu. Rất đáng để trải nghiệm!”",
      avatar: "https://images.unsplash.com/photo-1599566150163-29194dcaad36?auto=format&fit=crop&w=200&q=60"
    },
    {
      name: "Thu Trang",
      text: "“Mình đi du lịch một mình nhưng không hề cảm thấy lạc lõng. Nhân viên tư vấn rất dễ thương và đưa nhiều mẹo hay.”",
      avatar: "https://images.unsplash.com/photo-1529626455594-4ff0802cfb7e?auto=format&fit=crop&w=200&q=60"
    }
  ];
  activeIndex = 0;
  show = true;

  tours: Tours[] = [];
  firstTour: any;
  visibleTours: Tours[] = [];
  toursByViewCount: Tours[] = [];
  firstTourByViewCount: any;
  visibleToursByViewCount: Tours[] = [];
  toursBySeason: Tours[] = [];
  firstTourBySeason: any;
  visibleToursBySeason: Tours[] = [];

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
        this.firstTour = data[0];
        this.showPage(0);
        console.log("tours",this.tours);
        console.log("firstTour",this.firstTour);
      },
    });
    this.toursService.getTopToursByViewCount().subscribe({
      next: (data) => {
        this.toursByViewCount = data;
        this.firstTourByViewCount = data[0];
        this.showPage(0);
        console.log("toursByViewCount",this.toursByViewCount);
        console.log("firstTourByViewCount",this.firstTourByViewCount);
      },
    });
    const currentSeason = this.getCurrentSeason();
    console.log("Season now:", currentSeason);

    // Lấy tour theo mùa hiện tại
    this.toursService.getToursBySeason(currentSeason).subscribe({
      next: (data) => {
        this.toursBySeason = data;
        this.firstTourBySeason = data[0];
        this.showPage(0);
        console.log("toursBySeason",this.toursBySeason);
        console.log("firstTourBySeason",this.firstTourBySeason);
      },
      error: (err) => {
        console.error("Error fetching tours by season:", err);
      }
    });
    this.startSlider();
  }

  showPage(page: number) {
    this.currentPage = page;
    const start = page * this.pageSize;
    const end = start + this.pageSize;
    this.visibleTours = this.tours.slice(start, end);
    this.visibleToursByViewCount = this.toursByViewCount.slice(start, end);
    this.visibleToursBySeason = this.toursBySeason.slice(start, end);
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
    clearInterval(this.intervalId);
  }

  // nextSlide() {
  //   this.currentIndex = (this.currentIndex + 1) % this.images.length;
  // }

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

  viewAll(): void{
    this.router.navigate(['tours/list'])
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

  startSlider() {
    this.intervalId = setInterval(() => {
      this.show = false;

      setTimeout(() => {
        this.activeIndex = (this.activeIndex + 1) % this.testimonials.length;
        this.show = true;
      }, 200); // fade out trước khi đổi nội dung
    }, 3000);
  }

  getCurrentSeason(): string {
    const month = new Date().getMonth() + 1;

    if (month >= 3 && month <= 5) return 'SPRING';
    if (month >= 6 && month <= 8) return 'SUMMER';
    if (month >= 9 && month <= 11) return 'AUTUMN';
    return 'WINTER';
  }
}
