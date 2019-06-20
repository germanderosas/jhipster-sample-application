/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { FacturasComponent } from 'app/entities/facturas/facturas.component';
import { FacturasService } from 'app/entities/facturas/facturas.service';
import { Facturas } from 'app/shared/model/facturas.model';

describe('Component Tests', () => {
  describe('Facturas Management Component', () => {
    let comp: FacturasComponent;
    let fixture: ComponentFixture<FacturasComponent>;
    let service: FacturasService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [FacturasComponent],
        providers: []
      })
        .overrideTemplate(FacturasComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FacturasComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FacturasService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Facturas(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.facturas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
