/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { FacturasUpdateComponent } from 'app/entities/facturas/facturas-update.component';
import { FacturasService } from 'app/entities/facturas/facturas.service';
import { Facturas } from 'app/shared/model/facturas.model';

describe('Component Tests', () => {
  describe('Facturas Management Update Component', () => {
    let comp: FacturasUpdateComponent;
    let fixture: ComponentFixture<FacturasUpdateComponent>;
    let service: FacturasService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipsterSampleApplicationTestModule],
        declarations: [FacturasUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FacturasUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FacturasUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FacturasService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Facturas(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Facturas();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
