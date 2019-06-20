import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IClientes, Clientes } from 'app/shared/model/clientes.model';
import { ClientesService } from './clientes.service';

@Component({
  selector: 'jhi-clientes-update',
  templateUrl: './clientes-update.component.html'
})
export class ClientesUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    clienteNombre: [null, [Validators.required]],
    clienteDomicilio: [],
    clienteCuit: [],
    clienteID: []
  });

  constructor(protected clientesService: ClientesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ clientes }) => {
      this.updateForm(clientes);
    });
  }

  updateForm(clientes: IClientes) {
    this.editForm.patchValue({
      id: clientes.id,
      clienteNombre: clientes.clienteNombre,
      clienteDomicilio: clientes.clienteDomicilio,
      clienteCuit: clientes.clienteCuit,
      clienteID: clientes.clienteID
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const clientes = this.createFromForm();
    if (clientes.id !== undefined) {
      this.subscribeToSaveResponse(this.clientesService.update(clientes));
    } else {
      this.subscribeToSaveResponse(this.clientesService.create(clientes));
    }
  }

  private createFromForm(): IClientes {
    const entity = {
      ...new Clientes(),
      id: this.editForm.get(['id']).value,
      clienteNombre: this.editForm.get(['clienteNombre']).value,
      clienteDomicilio: this.editForm.get(['clienteDomicilio']).value,
      clienteCuit: this.editForm.get(['clienteCuit']).value,
      clienteID: this.editForm.get(['clienteID']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClientes>>) {
    result.subscribe((res: HttpResponse<IClientes>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
