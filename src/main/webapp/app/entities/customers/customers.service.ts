import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ResponseObject} from "../../tours/tours.modal";
import {Customers} from "./customers.modal";

@Injectable({ providedIn: 'root' })
export class CustomersService {
  private resourceUrl = 'api/customers';

  constructor(private http: HttpClient) {
  }

  getMyProfile(): Observable<ResponseObject<Customers>>{
    return this.http.get<ResponseObject<Customers>>(`${this.resourceUrl}/me`);
  }
}
