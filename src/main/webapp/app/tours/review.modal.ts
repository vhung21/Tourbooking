import {Tours} from "./tours.modal";
import {User} from "../admin/user-management/user-management.model";

export interface Reviews{
  id?: number;
  customer?: Customer;
  tours?: Tours;
  rating?: number;
  comment?: string;
  cratedAt?: string;
}

export interface Customer {
  id?: number;                // Khóa chính
  user: User;                 // Quan hệ 1-1 với User
  fullName?: string;          // Họ tên
  email?: string;             // Email
  phone?: string;             // Số điện thoại
  gender?: string;            // Giới tính
  dateOfBirth?: string;       // Ngày sinh (ISO string khi lấy từ BE)
  address?: string;           // Địa chỉ
  // bookings?: Booking[];       // Quan hệ 1-nhiều với Booking
}

export interface ResponseObject<T> {
  status: string;
  message: string;
  data: T;
}
