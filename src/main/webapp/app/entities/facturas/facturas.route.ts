import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Facturas } from 'app/shared/model/facturas.model';
import { FacturasService } from './facturas.service';
import { FacturasComponent } from './facturas.component';
import { FacturasDetailComponent } from './facturas-detail.component';
import { FacturasUpdateComponent } from './facturas-update.component';
import { FacturasDeletePopupComponent } from './facturas-delete-dialog.component';
import { IFacturas } from 'app/shared/model/facturas.model';

@Injectable({ providedIn: 'root' })
export class FacturasResolve implements Resolve<IFacturas> {
  constructor(private service: FacturasService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFacturas> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Facturas>) => response.ok),
        map((facturas: HttpResponse<Facturas>) => facturas.body)
      );
    }
    return of(new Facturas());
  }
}

export const facturasRoute: Routes = [
  {
    path: '',
    component: FacturasComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Facturas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FacturasDetailComponent,
    resolve: {
      facturas: FacturasResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Facturas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FacturasUpdateComponent,
    resolve: {
      facturas: FacturasResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Facturas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FacturasUpdateComponent,
    resolve: {
      facturas: FacturasResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Facturas'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const facturasPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FacturasDeletePopupComponent,
    resolve: {
      facturas: FacturasResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Facturas'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
