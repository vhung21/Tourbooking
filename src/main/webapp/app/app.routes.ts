import { Routes } from '@angular/router';

import { Authority } from 'app/config/authority.constants';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { errorRoute } from './layouts/error/error.route';

// @ts-ignore
const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./home/home.component'),
    title: 'home.title',
  },
  {
    path: 'tours',
    loadComponent: () => import('./tours/tours.component'),
  },
  {
    path: 'tours/list',
    loadComponent: () => import('./tours/listToursComponent/listTours.component')
      .then(m => m.ListToursComponent),
  },
  {
    path: 'tours/:id',
    loadComponent: () => import('./tours/toursDetailComponent/toursDetail.component')
      .then(m => m.ToursDetailComponent),
  },
  {
    path: '',
    loadComponent: () => import('./layouts/navbar/navbar.component'),
    outlet: 'navbar',
  },
  {
    path: 'admin',
    data: {
      authorities: [Authority.ADMIN],
    },
    canActivate: [UserRouteAccessService],
    loadChildren: () => import('./admin/admin.routes'),
  },
  {
    path: 'staff',
    data: {
      authorities: [Authority.STAFF],
    },
    canActivate: [UserRouteAccessService],
    loadChildren: () => import('./staff/staff.routes'),
  },
  {
    path: 'account',
    loadChildren: () => import('./account/account.route'),
  },
  {
    path: 'login',
    loadComponent: () => import('./login/login.component'),
    title: 'login.title',
  },
  {
    path: '',
    loadChildren: () => import(`./entities/entity.routes`),
  },
  ...errorRoute,
];

export default routes;
