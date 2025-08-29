export interface TopTours {
  id?: number;
  tourName?: string;
  description?: string;
  price?: number;
  startDate?: string;
  endDate?: string;
  location?: string;
  transportation?: string;
  imageUrl?: string;
  averageRating?: number;
  reviewCount?: number;
}

export interface ResponseObject<T> {
  status: string;
  message: string;
  data: T;
}
