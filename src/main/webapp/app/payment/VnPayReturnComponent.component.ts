import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { NgIf } from '@angular/common';
import {PaymentService} from "./CreateVnPayPaymentResponse.service";


@Component({
  standalone: true,
  selector: 'app-vnpay-return',
  templateUrl: './VnpayReturn.component.html',
  imports: [NgIf, RouterLink],
})
export class VnPayReturnComponent implements OnInit {
  loading = true;
  success = false;
  message = '';
  orderId?: number;
  responseCode?: string;

  constructor(
    private route: ActivatedRoute,
    private paymentService: PaymentService
  ) {}

  ngOnInit(): void {
    this.route.queryParamMap.subscribe(params => {
      const payload: any = {};
      params.keys.forEach(key => {
        const value = params.get(key);
        if (value !== null) {
          payload[key] = value;
        }
      });

      const vnpTxnRef = params.get('vnp_TxnRef');

      this.paymentService.verifyVnPayReturn(payload).subscribe({
        next: res => {
          this.loading = false;
          this.success = res.success;
          this.message = res.message;
          this.responseCode = res.responseCode;
          this.orderId = res.orderId ?? this.extractOrderId(vnpTxnRef);
        },
        error: err => {
          console.error(err);
          this.loading = false;
          // fallback: chỉ dựa vào mã responseCode trên URL
          const code = params.get('vnp_ResponseCode');
          this.responseCode = code ?? undefined;
          this.success = code === '00';
          this.orderId = this.extractOrderId(vnpTxnRef);
          this.message = this.success
            ? 'Thanh toán có vẻ thành công (không xác thực được với server).'
            : 'Thanh toán thất bại hoặc bị hủy.';
        }
      });
    });
  }

  private extractOrderId(txnRef: string | null): number | undefined {
    if (!txnRef) return undefined;
    const parts = txnRef.split('-');
    const id = Number(parts[0]);
    return Number.isNaN(id) ? undefined : id;
  }
}
