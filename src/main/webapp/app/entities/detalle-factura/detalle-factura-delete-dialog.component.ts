import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDetalleFactura } from 'app/shared/model/detalle-factura.model';
import { DetalleFacturaService } from './detalle-factura.service';

@Component({
  selector: 'jhi-detalle-factura-delete-dialog',
  templateUrl: './detalle-factura-delete-dialog.component.html'
})
export class DetalleFacturaDeleteDialogComponent {
  detalleFactura: IDetalleFactura;

  constructor(
    protected detalleFacturaService: DetalleFacturaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.detalleFacturaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'detalleFacturaListModification',
        content: 'Deleted an detalleFactura'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-detalle-factura-delete-popup',
  template: ''
})
export class DetalleFacturaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ detalleFactura }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DetalleFacturaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.detalleFactura = detalleFactura;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/detalle-factura', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/detalle-factura', { outlets: { popup: null } }]);
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
