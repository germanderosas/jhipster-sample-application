/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { FacturasService } from 'app/entities/facturas/facturas.service';
import { IFacturas, Facturas } from 'app/shared/model/facturas.model';

describe('Service Tests', () => {
  describe('Facturas Service', () => {
    let injector: TestBed;
    let service: FacturasService;
    let httpMock: HttpTestingController;
    let elemDefault: IFacturas;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(FacturasService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Facturas(0, 0, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            facturaFecha: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Facturas', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            facturaFecha: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            facturaFecha: currentDate
          },
          returnedFromService
        );
        service
          .create(new Facturas(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Facturas', async () => {
        const returnedFromService = Object.assign(
          {
            facturaNro: 1,
            facturaFecha: currentDate.format(DATE_TIME_FORMAT),
            facturaClienteID: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            facturaFecha: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Facturas', async () => {
        const returnedFromService = Object.assign(
          {
            facturaNro: 1,
            facturaFecha: currentDate.format(DATE_TIME_FORMAT),
            facturaClienteID: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            facturaFecha: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Facturas', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
