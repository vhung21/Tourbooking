import {Component, OnInit} from "@angular/core";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {TourService} from "../../tours/tours.service";
import {OrderToursService} from "./orderTours.service";
import {DecimalPipe, NgIf} from "@angular/common";
import {PaymentService} from "../../payment/CreateVnPayPaymentResponse.service";
import {firstValueFrom} from "rxjs";
import { QRCodeComponent } from 'angularx-qrcode';

@Component({
  selector: 'app-order',
  templateUrl: './orderTours.component.html',
  imports: [
    ReactiveFormsModule,
    RouterLink,
    DecimalPipe,
    NgIf,
    QRCodeComponent
  ],
  styleUrls: ['./orderTours.component.scss']
})
export class OrderToursComponent implements OnInit {
  orderForm!: FormGroup;
  tourId!: number;
  tourData: any;
  totalPrice: number = 0;
  loading: boolean = false;
  paymentUrl: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private orderService: OrderToursService,
    private tourService: TourService,
    private paymentService: PaymentService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.tourId = Number(this.route.snapshot.paramMap.get('id'));
    console.log("TourID: ", this.tourId)

    // Lấy thông tin tour để hiển thị và tính tiền
    this.tourService.getById(this.tourId).subscribe({next : res => {
        this.tourData = res.data;
        console.log("Dữ liệu tour:", this.tourData);
        this.initForm();
      },
      error: err => console.error(err)
    });
  }

  initForm(): void {
    this.orderForm = this.fb.group({
      fullName: ['', [Validators.required]],
      phone: ['', [Validators.required]],
      email: ['', [Validators.email]],
      numberOfPeople: [1, [Validators.required, Validators.min(1)]],
    });

    this.calculateTotal(); // tính lần đầu

    // Khi số người thay đổi thì tính lại tổng
    this.orderForm.get('numberOfPeople')?.valueChanges.subscribe(() => {
      this.calculateTotal();
    });
  }

  calculateTotal(): void {
    const num = this.orderForm?.get('numberOfPeople')?.value || 1;
    const price = this.tourData?.price || 0;
    this.totalPrice = price * num;
  }

  async submit(): Promise<void> {
    if (this.orderForm.invalid) {
      this.orderForm.markAllAsTouched();
      return;
    }

    this.loading = true;

    const body = {
      tourId: this.tourId,
      fullName: this.orderForm.value.fullName,
      phone: this.orderForm.value.phone,
      email: this.orderForm.value.email,
      numberOfPeople: this.orderForm.value.numberOfPeople,
      totalPrice: this.totalPrice
    };

    try {
      // 1. Tạo Order (status = PENDING)
      const orderRes = await firstValueFrom(this.orderService.createOrder(body));
      const orderId = orderRes.id; // tùy backend trả về

      // 2. Gọi BE tạo VNPay paymentUrl
      const payRes = await firstValueFrom(this.paymentService.createVnPayPayment(orderId));

      this.paymentUrl = payRes.paymentUrl;
      // Không redirect ngay, mà hiện QR cho khách quét
    } catch (err) {
      console.error(err);
      alert('Có lỗi xảy ra khi tạo thanh toán VNPay.');
    } finally {
      this.loading = false;
    }
  }
}
