import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IClientes } from 'app/shared/model/clientes.model';
import { AccountService } from 'app/core';
import { ClientesService } from './clientes.service';

@Component({
  selector: 'jhi-clientes',
  templateUrl: './clientes.component.html'
})
export class ClientesComponent implements OnInit, OnDestroy {
  clientes: IClientes[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected clientesService: ClientesService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.clientesService
      .query()
      .pipe(
        filter((res: HttpResponse<IClientes[]>) => res.ok),
        map((res: HttpResponse<IClientes[]>) => res.body)
      )
      .subscribe(
        (res: IClientes[]) => {
          this.clientes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInClientes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IClientes) {
    return item.id;
  }

  registerChangeInClientes() {
    this.eventSubscriber = this.eventManager.subscribe('clientesListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
