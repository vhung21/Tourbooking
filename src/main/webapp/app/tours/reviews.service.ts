import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Reviews} from "./review.modal";
import {ResponseObject} from "./tours.modal";


@Injectable({ providedIn: 'root' })
export class ReviewsService {
  private resourceUrl = '/api/review';
  constructor(private http: HttpClient) {}

  getAllReviewsByTourId(id: number): Observable<ResponseObject<Reviews[]>> {
    return this.http.get<ResponseObject<Reviews[]>>(`${this.resourceUrl}/${id}/reviews`);
  }
}
