import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface MonthlyRevenue {
  month: string;
  revenue: number;
}

export interface TourStat {
  id: number;
  name: string;
  bookingCount: number;
  revenue: number;
}

export interface HotelStat {
  id: number;
  name: string;
  bookingCount: number;
}

export interface TourTimeline {
  id: number;
  name: string;
  startDate: string;
  endDate: string;
}

export interface DashboardStatistics {
  totalTours: number;
  totalHotels: number;
  totalBookings: number;
  totalRevenue: number;
  toursBySeason: { [key: string]: number };
  monthlyRevenue: MonthlyRevenue[];
  topTours: TourStat[];
  topHotels: HotelStat[];
  activeTourTimelines: TourTimeline[];
}

@Injectable({ providedIn: 'root' })
export class DashboardService {
  private resourceUrl = 'api/dashboard';

  constructor(private http: HttpClient) {}

  getStatistics(): Observable<DashboardStatistics> {
    return this.http.get<DashboardStatistics>(`${this.resourceUrl}/statistics`);
  }
}
