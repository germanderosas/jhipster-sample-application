import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IFacturas, Facturas } from 'app/shared/model/facturas.model';
import { FacturasService } from './facturas.service';
import { IClientes } from 'app/shared/model/clientes.model';
import { ClientesService } from 'app/entities/clientes';

@Component({
  selector: 'jhi-facturas-update',
  templateUrl: './facturas-update.component.html'
})
export class FacturasUpdateComponent implements OnInit {
  isSaving: boolean;

  clientes: IClientes[];

  editForm = this.fb.group({
    id: [],
    facturaNro: [null, [Validators.required]],
    facturaFecha: [],
    facturaClienteID: [],
    clientes: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected facturasService: FacturasService,
    protected clientesService: ClientesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ facturas }) => {
      this.updateForm(facturas);
    });
    this.clientesService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IClientes[]>) => mayBeOk.ok),
        map((response: HttpResponse<IClientes[]>) => response.body)
      )
      .subscribe((res: IClientes[]) => (this.clientes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(facturas: IFacturas) {
    this.editForm.patchValue({
      id: facturas.id,
      facturaNro: facturas.facturaNro,
      facturaFecha: facturas.facturaFecha != null ? facturas.facturaFecha.format(DATE_TIME_FORMAT) : null,
      facturaClienteID: facturas.facturaClienteID,
      clientes: facturas.clientes
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const facturas = this.createFromForm();
    if (facturas.id !== undefined) {
      this.subscribeToSaveResponse(this.facturasService.update(facturas));
    } else {
      this.subscribeToSaveResponse(this.facturasService.create(facturas));
    }
  }

  private createFromForm(): IFacturas {
    const entity = {
      ...new Facturas(),
      id: this.editForm.get(['id']).value,
      facturaNro: this.editForm.get(['facturaNro']).value,
      facturaFecha:
        this.editForm.get(['facturaFecha']).value != null ? moment(this.editForm.get(['facturaFecha']).value, DATE_TIME_FORMAT) : undefined,
      facturaClienteID: this.editForm.get(['facturaClienteID']).value,
      clientes: this.editForm.get(['clientes']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFacturas>>) {
    result.subscribe((res: HttpResponse<IFacturas>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackClientesById(index: number, item: IClientes) {
    return item.id;
  }
}
