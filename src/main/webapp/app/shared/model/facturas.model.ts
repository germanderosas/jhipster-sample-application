import { Moment } from 'moment';
import { IClientes } from 'app/shared/model/clientes.model';
import { IDetalleFactura } from 'app/shared/model/detalle-factura.model';

export interface IFacturas {
  id?: number;
  facturaNro?: number;
  facturaFecha?: Moment;
  facturaClienteID?: number;
  clientes?: IClientes;
  facturaNros?: IDetalleFactura[];
}

export class Facturas implements IFacturas {
  constructor(
    public id?: number,
    public facturaNro?: number,
    public facturaFecha?: Moment,
    public facturaClienteID?: number,
    public clientes?: IClientes,
    public facturaNros?: IDetalleFactura[]
  ) {}
}
