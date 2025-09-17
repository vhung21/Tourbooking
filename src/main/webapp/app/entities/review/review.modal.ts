import {Tours} from "../../tours/tours.modal";
import {Customers} from "../customers/customers.modal";


export interface Reviews{
  id?: number;
  customer?: Customers;
  tours?: Tours;
  rating?: number;
  comment?: string;
  cratedAt?: string;
}
