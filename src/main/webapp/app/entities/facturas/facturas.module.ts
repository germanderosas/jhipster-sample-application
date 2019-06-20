import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared';
import {
  FacturasComponent,
  FacturasDetailComponent,
  FacturasUpdateComponent,
  FacturasDeletePopupComponent,
  FacturasDeleteDialogComponent,
  facturasRoute,
  facturasPopupRoute
} from './';

const ENTITY_STATES = [...facturasRoute, ...facturasPopupRoute];

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FacturasComponent,
    FacturasDetailComponent,
    FacturasUpdateComponent,
    FacturasDeleteDialogComponent,
    FacturasDeletePopupComponent
  ],
  entryComponents: [FacturasComponent, FacturasUpdateComponent, FacturasDeleteDialogComponent, FacturasDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationFacturasModule {}
