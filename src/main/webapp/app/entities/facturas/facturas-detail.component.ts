import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFacturas } from 'app/shared/model/facturas.model';

@Component({
  selector: 'jhi-facturas-detail',
  templateUrl: './facturas-detail.component.html'
})
export class FacturasDetailComponent implements OnInit {
  facturas: IFacturas;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ facturas }) => {
      this.facturas = facturas;
    });
  }

  previousState() {
    window.history.back();
  }
}
