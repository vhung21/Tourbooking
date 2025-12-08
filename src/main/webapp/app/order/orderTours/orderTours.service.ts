import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Hotels} from "../../hotels/hotels.modal";

export interface CreateOrderRequest {
  tourId: number;
  fullName: string;
  phone: string;
  email?: string;
  numberOfPeople: number;
  totalPrice: number;
}

@Injectable({ providedIn: 'root' })
export class OrderToursService {
  private apiUrl = '/api/orders'; // nếu bạn có environment thì thay bằng environment.apiUrl + '/orders'

  constructor(private http: HttpClient) {}

  createOrder(body: CreateOrderRequest): Observable<any> {
    return this.http.post<any>(this.apiUrl, body);
  }

  getAllOrder(): Observable<any>{
    return this.http.get<any>(this.apiUrl)
  }
}
