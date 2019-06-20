import { IFacturas } from 'app/shared/model/facturas.model';

export interface IDetalleFactura {
  id?: number;
  detalleFactID?: number;
  facturaNro?: number;
  detalleFacDescripcion?: string;
  detalleFacPrecioUnitario?: number;
  detalleFacCantidad?: number;
  detalleFacTotal?: number;
  facturas?: IFacturas;
}

export class DetalleFactura implements IDetalleFactura {
  constructor(
    public id?: number,
    public detalleFactID?: number,
    public facturaNro?: number,
    public detalleFacDescripcion?: string,
    public detalleFacPrecioUnitario?: number,
    public detalleFacCantidad?: number,
    public detalleFacTotal?: number,
    public facturas?: IFacturas
  ) {}
}
