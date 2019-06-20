import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'clientes',
        loadChildren: './clientes/clientes.module#JhipsterSampleApplicationClientesModule'
      },
      {
        path: 'facturas',
        loadChildren: './facturas/facturas.module#JhipsterSampleApplicationFacturasModule'
      },
      {
        path: 'detalle-factura',
        loadChildren: './detalle-factura/detalle-factura.module#JhipsterSampleApplicationDetalleFacturaModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationEntityModule {}
