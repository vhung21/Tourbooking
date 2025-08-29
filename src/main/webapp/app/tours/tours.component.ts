import {Component, inject, OnDestroy, OnInit} from "@angular/core";
import SharedModule from "../shared/shared.module";
import {Router, RouterModule} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {TourService} from "./tours.service";
import {TopTours} from "./tours.modal";

@Component({
  selector: 'jhi-tours',
  templateUrl: './tours.component.html',
  styleUrl: './tours.component.scss',
  imports: [SharedModule, RouterModule, FormsModule,],
})

export default class ToursComponent implements OnInit, OnDestroy{
  private readonly router = inject(Router);

  tours: TopTours[] = [];
  visibleTours: TopTours[] = [];
  currentPage = 0;
  pageSize = 3;

  constructor(
    private http: HttpClient,
    private toursService: TourService
    ) {}

  images = [
    'content/images/Tour_du_lich_xuyen_viet.svg',
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
}
