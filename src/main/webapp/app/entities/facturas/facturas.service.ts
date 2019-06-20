import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFacturas } from 'app/shared/model/facturas.model';

type EntityResponseType = HttpResponse<IFacturas>;
type EntityArrayResponseType = HttpResponse<IFacturas[]>;

@Injectable({ providedIn: 'root' })
export class FacturasService {
  public resourceUrl = SERVER_API_URL + 'api/facturas';

  constructor(protected http: HttpClient) {}

  create(facturas: IFacturas): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(facturas);
    return this.http
      .post<IFacturas>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(facturas: IFacturas): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(facturas);
    return this.http
      .put<IFacturas>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFacturas>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFacturas[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(facturas: IFacturas): IFacturas {
    const copy: IFacturas = Object.assign({}, facturas, {
      facturaFecha: facturas.facturaFecha != null && facturas.facturaFecha.isValid() ? facturas.facturaFecha.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.facturaFecha = res.body.facturaFecha != null ? moment(res.body.facturaFecha) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((facturas: IFacturas) => {
        facturas.facturaFecha = facturas.facturaFecha != null ? moment(facturas.facturaFecha) : null;
      });
    }
    return res;
  }
}
