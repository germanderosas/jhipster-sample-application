import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDetalleFactura } from 'app/shared/model/detalle-factura.model';

@Component({
  selector: 'jhi-detalle-factura-detail',
  templateUrl: './detalle-factura-detail.component.html'
})
export class DetalleFacturaDetailComponent implements OnInit {
  detalleFactura: IDetalleFactura;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ detalleFactura }) => {
      this.detalleFactura = detalleFactura;
    });
  }

  previousState() {
    window.history.back();
  }
}
