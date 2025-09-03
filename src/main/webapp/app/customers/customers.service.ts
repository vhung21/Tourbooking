import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Customer, Reviews} from "../tours/review.modal";
import {ResponseObject} from "../tours/tours.modal";

@Injectable({ providedIn: 'root' })
export class CustomersService {
  private resourceUrl = 'api/customers';

  constructor(private http: HttpClient) {
  }

  getMyProfile(): Observable<ResponseObject<Customer>>{
    return this.http.get<ResponseObject<Customer>>(`${this.resourceUrl}/me`);
  }
}
