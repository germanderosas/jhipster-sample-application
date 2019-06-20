import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFacturas } from 'app/shared/model/facturas.model';
import { FacturasService } from './facturas.service';

@Component({
  selector: 'jhi-facturas-delete-dialog',
  templateUrl: './facturas-delete-dialog.component.html'
})
export class FacturasDeleteDialogComponent {
  facturas: IFacturas;

  constructor(protected facturasService: FacturasService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.facturasService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'facturasListModification',
        content: 'Deleted an facturas'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-facturas-delete-popup',
  template: ''
})
export class FacturasDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ facturas }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FacturasDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.facturas = facturas;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/facturas', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/facturas', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
