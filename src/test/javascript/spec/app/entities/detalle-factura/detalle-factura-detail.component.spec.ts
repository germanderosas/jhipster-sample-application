/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { DetalleFacturaDetailComponent } from 'app/entities/detalle-factura/detalle-factura-detail.component';
import { DetalleFactura } from 'app/shared/model/detalle-factura.model';

describe('Component Tests', () => {
  describe('DetalleFactura Management Detail Component', () => {
    let comp: DetalleFacturaDetailComponent;
    let fixture: ComponentFixture<DetalleFacturaDetailComponent>;
    const route = ({ data: of({ detalleFactura: new DetalleFactura(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [DetalleFacturaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DetalleFacturaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DetalleFacturaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.detalleFactura).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
