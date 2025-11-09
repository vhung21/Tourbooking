import {Routes} from "@angular/router";


const hotelManagementRoute: Routes = [
  {
    path: 'list',
    loadComponent: () => import('./list/hotelListing.component'),
    title: 'dashboard.title',
  },
  {
    path: 'edit/:id',
    loadComponent: () => import('./update/hotelUpdate.component'),
    title: 'editTour.title'
  },
  {
    path: 'create',
    loadComponent: () => import('./create/hotelCreate.component'),
    title: 'editTour.title'
  },
]

export default hotelManagementRoute;
