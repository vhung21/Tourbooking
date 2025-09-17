import {User} from "../../admin/user-management/user-management.model";

export interface Customers {
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
