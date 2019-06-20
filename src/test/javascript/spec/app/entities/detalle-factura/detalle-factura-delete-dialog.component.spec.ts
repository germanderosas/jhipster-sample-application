/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { DetalleFacturaDeleteDialogComponent } from 'app/entities/detalle-factura/detalle-factura-delete-dialog.component';
import { DetalleFacturaService } from 'app/entities/detalle-factura/detalle-factura.service';

describe('Component Tests', () => {
  describe('DetalleFactura Management Delete Component', () => {
    let comp: DetalleFacturaDeleteDialogComponent;
    let fixture: ComponentFixture<DetalleFacturaDeleteDialogComponent>;
    let service: DetalleFacturaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [DetalleFacturaDeleteDialogComponent]
      })
        .overrideTemplate(DetalleFacturaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DetalleFacturaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DetalleFacturaService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
