import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {ResponseObject, TopTours} from "./tours.modal";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";

@Injectable({ providedIn: 'root' })
export class TourService {
  private resourceUrl = 'api/tours';
  constructor(private http: HttpClient) {}


  getTopTours(): Observable<TopTours[]> {
    return this.http
      .get<ResponseObject<TopTours[]>>(`${this.resourceUrl}/getTopTours`)
      .pipe(
        map(res => res.data)
      );
  }
}
