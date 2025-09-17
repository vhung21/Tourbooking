import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {TourLocation} from "./location.modal";

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  private apiUrl = '/api/location';

  constructor(private http: HttpClient) { }

  createDeparturePoint(location: Location): Observable<TourLocation> {
    return this.http.post<TourLocation>(this.apiUrl, location);
  }

  getSuggestions(keyword: string): Observable<TourLocation[]> {
    let params = new HttpParams();
    if (keyword) {
      params = params.append('keyword', keyword);
    }
    return this.http.get<TourLocation[]>(this.apiUrl, { params });
  }
}
