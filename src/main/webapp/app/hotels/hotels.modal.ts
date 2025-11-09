export interface Hotels {
  id?: number;
  name?: string;
  address?: string;
  city?: string;
  rating?: string;
  imageUrl?: string;
  phone?: string;
  description?: string;
  rooms?: Rooms[];
}

export interface Rooms {
  id?: number;
  roomType?: string;
  price?: string;
  description?: string;
  imageUrl?: string;
  roomDetails?: RoomDetails[];
}

export interface RoomDetails {
  id?: number;
  roomDetailTitle?: string;
  roomDetailDescription?: string;
}
