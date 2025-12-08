import {Component, OnInit} from "@angular/core";
import {CommonModule, NgClass} from "@angular/common";
import {FormsModule} from "@angular/forms";
import {OrderToursService} from "../../../order/orderTours/orderTours.service";
import {RouterLink, RouterLinkActive} from "@angular/router";

@Component({
  selector: 'jhi-customerCareListing',
  templateUrl: './customerCareListing.component.html',
  styleUrl: './customerCareListing.component.scss',
  imports: [
    CommonModule,
    FormsModule,
    RouterLink,
    RouterLinkActive,
  ]
})
export default class CustomerCareListingComponent implements OnInit {
  orders: any[] = [];
  filteredOrders: any[] = [];
  searchTerm = '';
  pageSize = 10;

  constructor(
    private orderToursService: OrderToursService
  ) {
  }

  ngOnInit(): void {
    this.orderToursService.getAllOrder().subscribe(data => {
      this.orders = data;
      this.filteredOrders = data;
      console.log(this.orders);
    });
  }

  onPageSizeChange() {
    // (Nếu dùng phân trang thực, xử lý tại đây)
  }
}
