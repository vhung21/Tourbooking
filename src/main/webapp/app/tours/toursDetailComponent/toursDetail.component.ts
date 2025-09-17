import {Component, OnInit} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {TourService} from "../tours.service";
import {ActivatedRoute, RouterLink} from "@angular/router";
import {Itinerary, Tours} from "../tours.modal";
import {CommonModule, NgForOf, NgIf} from "@angular/common";
import {ReviewsService} from "../../entities/review/reviews.service";
import {FormsModule} from "@angular/forms";
import {CustomersService} from "../../entities/customers/customers.service";

@Component({
  selector: 'app-tour-detail',
  templateUrl: './toursDetail.component.html',
  standalone: true,
  imports: [
    NgIf,
    NgForOf,
    [CommonModule],
    FormsModule,
    RouterLink
  ],
  styleUrls: ['./toursDetail.component.scss']
})
export class ToursDetailComponent implements OnInit {
  currentTourId!: number;
  tourId!: number;
  tourData?: Tours;
  tours: Tours[] = [];
  isOpen: boolean[] = [];
  rating: boolean = true;
  private avgRating: number = 0;
  stars: string[] = [];
  reviews: any[] = [];
  starsRating: any[] = [];
  ratingDistribution: number[] = [0, 0, 0, 0, 0];
  ratingPercentages: number[] = [0, 0, 0, 0, 0];
  selectedStar = 0;
  comment = '';
  selected = 0;
  hovered  = 0;
  customer: any;
  tour: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private toursService: TourService,
    private reviewService: ReviewsService,
    private customersService: CustomersService
  ) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      if (idParam) {
        this.tourId = +idParam;
        this.currentTourId = this.tourId;
        this.toursService.getById(this.tourId).subscribe({
          next: res => {
            this.tourData = res.data;
            console.log("Dữ liệu tour:", this.tourData);
            this.avgRating = this.tourData?.averageRating ?? 0;
            this.generateStars(this.avgRating);
          },
          error: err => console.error(err)
        });
        this.reviewService.getAllReviewsByTourId(this.tourId).subscribe({
          next: res => {
            this.reviews = res.data;
            console.log("Danh sách reviews:", this.reviews);
            this.calculateRatingDistribution(this.reviews);
            this.reviews.forEach(review => {
              review.stars = this.generateStarsReview(review.rating);
            });
          },
          error: err => console.error(err)
        });
      }
    });

    this.customersService.getMyProfile().subscribe({
      next: res => {
        this.customer = res;
        console.log("Customer:",this.customer)
      },
      error: err => console.error(err)
    });
    this.toursService.getAllTours().subscribe({
      next: (data) => {
        this.tours = data.slice(0,4);
        console.log("AllTours: ", this.tours);
      },
    });
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.currentTourId = +idParam;
    }
  }

  toggleAccordion(index: number) {
    this.isOpen[index] = !this.isOpen[index];
  }

  ratingDetail() {
    this.rating = true;
    console.log(this.rating);
  }


  addRating() {
    this.rating = false;
    console.log(this.rating);
  }

  generateStars(rating: number) {
    this.stars = [];
    for (let i = 1; i <= 5; i++) {
      if (rating >= i) {
        this.stars.push('full');
      } else if (rating >= i - 0.5) {
        this.stars.push('half');
      } else {
        this.stars.push('empty');
      }
    }
  }

  generateStarsReview(rating: number): string[] {
    const stars: string[] = [];
    for (let i = 1; i <= 5; i++) {
      if (rating >= i) {
        stars.push('full');
      } else if (rating >= i - 0.5) {
        stars.push('half');
      } else {
        stars.push('empty');
      }
    }
    return stars;
  }

  calculateRatingDistribution(reviews: any[]) {
    this.ratingDistribution = [0, 0, 0, 0, 0];

    reviews.forEach(r => {
      const star = Math.floor(r.rating);
      if (star >= 1 && star <= 5) {
        this.ratingDistribution[star - 1]++;
      }
    });

    const total = reviews.length;
    if (total > 0) {
      this.ratingPercentages = this.ratingDistribution.map(count => (count / total) * 100);
    } else {
      this.ratingPercentages = [0, 0, 0, 0, 0];
    }

    console.log("Số lượng review theo sao:", this.ratingDistribution);
    console.log("Phần trăm review:", this.ratingPercentages);

  }

  submitReview() {
    if (this.selectedStar === 0) {
      alert('Bạn phải chọn số sao!');
      return;
    }
    if (!this.comment.trim()) {
      alert('Bạn phải nhập đánh giá!');
      return;
    }
    const reviewId = this.reviews.find(r => r.customer.id === this.customer.id)?.id;
    const params = new HttpParams().set('customerId', this.customer.id.toString());
    if (reviewId) {
      this.http.put(`/api/review/${reviewId}`, {
        rating: this.selectedStar,
        comment: this.comment
      },
        {params}
      ).subscribe(() => {
        alert('Cập nhật đánh giá thành công!');
        window.location.reload();
      });
    } else {
      this.http.post(`/api/review/${this.tourId}/reviews`, {
        tourId: this.tourId,
        customerId: this.customer.id,
        rating: this.selectedStar,
        comment: this.comment
      },
        {params}
      ).subscribe(() => {
        alert('Thêm đánh giá thành công!');
        window.location.reload();
      });
    }
  }

  getState(star: number): 'full' | 'half' | 'empty' {
    const r = this.hovered || this.selectedStar;
    if (r >= star) return 'full';
    if (r >= star - 0.5) return 'half';
    return 'empty';
  }

  private valueFromEvent(e: MouseEvent, star: number): number {
    const el = e.currentTarget as HTMLElement;
    const rect = el.getBoundingClientRect();
    const isLeftHalf = (e.clientX - rect.left) < rect.width / 2;
    return star - (isLeftHalf ? 0.5 : 0);
  }

  previewHalf(e: MouseEvent, star: number) {
    if (star === 1) {
      this.hovered = 1;
    } else {
      this.hovered = this.valueFromEvent(e, star);
    }
  }

  selectHalf(e: MouseEvent, star: number) {
    if (star === 1) {
      this.selectedStar = 1;
    } else {
      this.selectedStar = this.valueFromEvent(e, star);
    }
    this.hovered = 0;
  }
}
