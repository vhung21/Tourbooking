import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {ResponseObject, Tours} from "./tours.modal";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";

@Injectable({ providedIn: 'root' })
export class TourService {
  private resourceUrl = 'api/tours';
  constructor(private http: HttpClient) {}


  getTopTours(): Observable<Tours[]> {
    return this.http
      .get<ResponseObject<Tours[]>>(`${this.resourceUrl}/getTopTours`)
      .pipe(
        map(res => res.data)
      );
  }

  getAllTours(): Observable<Tours[]> {
    return this.http
      .get<ResponseObject<Tours[]>>(`${this.resourceUrl}`)
      .pipe(
        map(res => res.data)
      );
  }

  getById(id: number): Observable<any> {
    return this.http.get<any>(`${this.resourceUrl}/${id}`);
  }
}

