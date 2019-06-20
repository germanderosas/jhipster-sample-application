import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DetalleFactura } from 'app/shared/model/detalle-factura.model';
import { DetalleFacturaService } from './detalle-factura.service';
import { DetalleFacturaComponent } from './detalle-factura.component';
import { DetalleFacturaDetailComponent } from './detalle-factura-detail.component';
import { DetalleFacturaUpdateComponent } from './detalle-factura-update.component';
import { DetalleFacturaDeletePopupComponent } from './detalle-factura-delete-dialog.component';
import { IDetalleFactura } from 'app/shared/model/detalle-factura.model';

@Injectable({ providedIn: 'root' })
export class DetalleFacturaResolve implements Resolve<IDetalleFactura> {
  constructor(private service: DetalleFacturaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDetalleFactura> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DetalleFactura>) => response.ok),
        map((detalleFactura: HttpResponse<DetalleFactura>) => detalleFactura.body)
      );
    }
    return of(new DetalleFactura());
  }
}

export const detalleFacturaRoute: Routes = [
  {
    path: '',
    component: DetalleFacturaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DetalleFacturas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DetalleFacturaDetailComponent,
    resolve: {
      detalleFactura: DetalleFacturaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DetalleFacturas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DetalleFacturaUpdateComponent,
    resolve: {
      detalleFactura: DetalleFacturaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DetalleFacturas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DetalleFacturaUpdateComponent,
    resolve: {
      detalleFactura: DetalleFacturaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DetalleFacturas'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const detalleFacturaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DetalleFacturaDeletePopupComponent,
    resolve: {
      detalleFactura: DetalleFacturaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'DetalleFacturas'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
