/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { FacturasDetailComponent } from 'app/entities/facturas/facturas-detail.component';
import { Facturas } from 'app/shared/model/facturas.model';

describe('Component Tests', () => {
  describe('Facturas Management Detail Component', () => {
    let comp: FacturasDetailComponent;
    let fixture: ComponentFixture<FacturasDetailComponent>;
    const route = ({ data: of({ facturas: new Facturas(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [FacturasDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FacturasDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FacturasDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.facturas).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
