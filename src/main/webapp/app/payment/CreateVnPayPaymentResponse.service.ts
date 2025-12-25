import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

export interface CreateVnPayPaymentResponse {
  paymentUrl: string;
}

export interface VerifyVnPayReturnResponse {
  success: boolean;
  message: string;
  orderId?: number;
  responseCode?: string;
}

@Injectable({ providedIn: 'root' })
export class PaymentService {
  constructor(private http: HttpClient) {}

  createVnPayPayment(orderId: number): Observable<CreateVnPayPaymentResponse> {
    return this.http.post<CreateVnPayPaymentResponse>(
      '/api/payments/vnpay/create',
      { orderId }
    );
  }

  verifyVnPayReturn(params: any): Observable<VerifyVnPayReturnResponse> {
    return this.http.post<VerifyVnPayReturnResponse>(
      '/api/payments/vnpay/return',
      params
    );
  }
}
