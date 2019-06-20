import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDetalleFactura } from 'app/shared/model/detalle-factura.model';
import { AccountService } from 'app/core';
import { DetalleFacturaService } from './detalle-factura.service';

@Component({
  selector: 'jhi-detalle-factura',
  templateUrl: './detalle-factura.component.html'
})
export class DetalleFacturaComponent implements OnInit, OnDestroy {
  detalleFacturas: IDetalleFactura[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected detalleFacturaService: DetalleFacturaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.detalleFacturaService
      .query()
      .pipe(
        filter((res: HttpResponse<IDetalleFactura[]>) => res.ok),
        map((res: HttpResponse<IDetalleFactura[]>) => res.body)
      )
      .subscribe(
        (res: IDetalleFactura[]) => {
          this.detalleFacturas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDetalleFacturas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDetalleFactura) {
    return item.id;
  }

  registerChangeInDetalleFacturas() {
    this.eventSubscriber = this.eventManager.subscribe('detalleFacturaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
