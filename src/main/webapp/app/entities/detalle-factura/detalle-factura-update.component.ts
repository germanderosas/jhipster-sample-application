import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDetalleFactura, DetalleFactura } from 'app/shared/model/detalle-factura.model';
import { DetalleFacturaService } from './detalle-factura.service';
import { IFacturas } from 'app/shared/model/facturas.model';
import { FacturasService } from 'app/entities/facturas';

@Component({
  selector: 'jhi-detalle-factura-update',
  templateUrl: './detalle-factura-update.component.html'
})
export class DetalleFacturaUpdateComponent implements OnInit {
  isSaving: boolean;

  facturas: IFacturas[];

  editForm = this.fb.group({
    id: [],
    detalleFactID: [],
    facturaNro: [null, [Validators.required]],
    detalleFacDescripcion: [],
    detalleFacPrecioUnitario: [],
    detalleFacCantidad: [],
    detalleFacTotal: [],
    facturas: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected detalleFacturaService: DetalleFacturaService,
    protected facturasService: FacturasService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ detalleFactura }) => {
      this.updateForm(detalleFactura);
    });
    this.facturasService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFacturas[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFacturas[]>) => response.body)
      )
      .subscribe((res: IFacturas[]) => (this.facturas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(detalleFactura: IDetalleFactura) {
    this.editForm.patchValue({
      id: detalleFactura.id,
      detalleFactID: detalleFactura.detalleFactID,
      facturaNro: detalleFactura.facturaNro,
      detalleFacDescripcion: detalleFactura.detalleFacDescripcion,
      detalleFacPrecioUnitario: detalleFactura.detalleFacPrecioUnitario,
      detalleFacCantidad: detalleFactura.detalleFacCantidad,
      detalleFacTotal: detalleFactura.detalleFacTotal,
      facturas: detalleFactura.facturas
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const detalleFactura = this.createFromForm();
    if (detalleFactura.id !== undefined) {
      this.subscribeToSaveResponse(this.detalleFacturaService.update(detalleFactura));
    } else {
      this.subscribeToSaveResponse(this.detalleFacturaService.create(detalleFactura));
    }
  }

  private createFromForm(): IDetalleFactura {
    const entity = {
      ...new DetalleFactura(),
      id: this.editForm.get(['id']).value,
      detalleFactID: this.editForm.get(['detalleFactID']).value,
      facturaNro: this.editForm.get(['facturaNro']).value,
      detalleFacDescripcion: this.editForm.get(['detalleFacDescripcion']).value,
      detalleFacPrecioUnitario: this.editForm.get(['detalleFacPrecioUnitario']).value,
      detalleFacCantidad: this.editForm.get(['detalleFacCantidad']).value,
      detalleFacTotal: this.editForm.get(['detalleFacTotal']).value,
      facturas: this.editForm.get(['facturas']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetalleFactura>>) {
    result.subscribe((res: HttpResponse<IDetalleFactura>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackFacturasById(index: number, item: IFacturas) {
    return item.id;
  }
}
