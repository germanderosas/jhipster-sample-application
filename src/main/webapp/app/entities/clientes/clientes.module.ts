import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
  ClientesComponent,
  ClientesDetailComponent,
  ClientesUpdateComponent,
  ClientesDeletePopupComponent,
  ClientesDeleteDialogComponent,
  clientesRoute,
  clientesPopupRoute
} from './';

const ENTITY_STATES = [...clientesRoute, ...clientesPopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ClientesComponent,
    ClientesDetailComponent,
    ClientesUpdateComponent,
    ClientesDeleteDialogComponent,
    ClientesDeletePopupComponent
  ],
  entryComponents: [ClientesComponent, ClientesUpdateComponent, ClientesDeleteDialogComponent, ClientesDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationClientesModule {}
