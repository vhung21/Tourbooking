import {Routes} from "@angular/router";


const tourManagementRoute: Routes = [
  {
    path: 'list',
    loadComponent: () => import('./list/tourListing.component'),
    title: 'dashboard.title',
  },
  {
    path: 'edit/:id',
    loadComponent: () => import('./update/tourUpdate.component'),
    title: 'editTour.title'
  },
  {
    path: 'create',
    loadComponent: () => import('./create/tourCreate.component'),
    title: 'editTour.title'
  },
]

export default tourManagementRoute;
