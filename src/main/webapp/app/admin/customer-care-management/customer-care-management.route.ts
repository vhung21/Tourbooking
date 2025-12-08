import {Routes} from "@angular/router";

const tourManagementRoute: Routes = [
  {
    path: 'list',
    loadComponent: () => import('./list/customerCareListing.component'),
    title: 'dashboard.title',
  },
]

export default tourManagementRoute;
