import { IFacturas } from 'app/shared/model/facturas.model';

export interface IClientes {
  id?: number;
  clienteNombre?: string;
  clienteDomicilio?: string;
  clienteCuit?: number;
  clienteID?: number;
  clienteIDS?: IFacturas[];
}

export class Clientes implements IClientes {
  constructor(
    public id?: number,
    public clienteNombre?: string,
    public clienteDomicilio?: string,
    public clienteCuit?: number,
    public clienteID?: number,
    public clienteIDS?: IFacturas[]
  ) {}
}
