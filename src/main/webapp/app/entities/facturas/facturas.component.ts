import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFacturas } from 'app/shared/model/facturas.model';
import { AccountService } from 'app/core';
import { FacturasService } from './facturas.service';

@Component({
  selector: 'jhi-facturas',
  templateUrl: './facturas.component.html'
})
export class FacturasComponent implements OnInit, OnDestroy {
  facturas: IFacturas[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected facturasService: FacturasService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.facturasService
      .query()
      .pipe(
        filter((res: HttpResponse<IFacturas[]>) => res.ok),
        map((res: HttpResponse<IFacturas[]>) => res.body)
      )
      .subscribe(
        (res: IFacturas[]) => {
          this.facturas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInFacturas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFacturas) {
    return item.id;
  }

  registerChangeInFacturas() {
    this.eventSubscriber = this.eventManager.subscribe('facturasListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
