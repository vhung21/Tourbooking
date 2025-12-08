export interface Tours {
  id?: number;
  tourName?: string;
  description?: string;
  price?: number;
  startDate?: string;
  endDate?: string;
  departures?: string;
  destination?: string;
  transportation?: string;
  imageUrl?: string;
  averageRating?: number;
  reviewCount?: number;
  detail?: TourDetail;
  itineraries?: Itinerary[];
  inclusions?: Inclusion[];
  viewCount?: number;
  season?: string;
}

export interface TourDetail {
  overview?: string;
  childrenPolicy?: string;
  bookingGuide?: string;
  payment?: string;
  cancellationPolicy?: string;
  termsNotes?: string;
  additionalInfo?: string;
}

export interface Itinerary {
  toursItineraryTitle: string;
  toursItineraryDescription: string;
}

export interface Inclusion {
  toursInclusionName: string;
  toursIncluded: number;
}

export interface ResponseObject<T> {
  status: string;
  message: string;
  data: T;
}
