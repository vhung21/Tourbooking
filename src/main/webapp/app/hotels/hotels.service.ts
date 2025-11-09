import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Hotels} from "./hotels.modal";

@Injectable({ providedIn: 'root' })
export class HotelService {
  private resourceUrl = 'api/hotels';

  constructor(private http: HttpClient) {
  }

  getAllHotels(): Observable<Hotels[]> {
    return this.http.get<Hotels[]>(`${this.resourceUrl}`);
  }

  getById(id: number): Observable<any> {
    return this.http.get<any>(`${this.resourceUrl}/${id}`);
  }

  delete(id:number): Observable<any> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`)
  }
}
