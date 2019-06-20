import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
  DetalleFacturaComponent,
  DetalleFacturaDetailComponent,
  DetalleFacturaUpdateComponent,
  DetalleFacturaDeletePopupComponent,
  DetalleFacturaDeleteDialogComponent,
  detalleFacturaRoute,
  detalleFacturaPopupRoute
} from './';

const ENTITY_STATES = [...detalleFacturaRoute, ...detalleFacturaPopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DetalleFacturaComponent,
    DetalleFacturaDetailComponent,
    DetalleFacturaUpdateComponent,
    DetalleFacturaDeleteDialogComponent,
    DetalleFacturaDeletePopupComponent
  ],
  entryComponents: [
    DetalleFacturaComponent,
    DetalleFacturaUpdateComponent,
    DetalleFacturaDeleteDialogComponent,
    DetalleFacturaDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationDetalleFacturaModule {}
