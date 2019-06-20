import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDetalleFactura } from 'app/shared/model/detalle-factura.model';

type EntityResponseType = HttpResponse<IDetalleFactura>;
type EntityArrayResponseType = HttpResponse<IDetalleFactura[]>;

@Injectable({ providedIn: 'root' })
export class DetalleFacturaService {
  public resourceUrl = SERVER_API_URL + 'api/detalle-facturas';

  constructor(protected http: HttpClient) {}

  create(detalleFactura: IDetalleFactura): Observable<EntityResponseType> {
    return this.http.post<IDetalleFactura>(this.resourceUrl, detalleFactura, { observe: 'response' });
  }

  update(detalleFactura: IDetalleFactura): Observable<EntityResponseType> {
    return this.http.put<IDetalleFactura>(this.resourceUrl, detalleFactura, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDetalleFactura>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDetalleFactura[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
