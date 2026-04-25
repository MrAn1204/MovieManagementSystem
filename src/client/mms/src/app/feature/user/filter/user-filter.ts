import { Component, OnInit, signal } from '@angular/core';
import { ControlContainer, FormGroupDirective, ReactiveFormsModule } from '@angular/forms';
import { SelectField } from '../../../shared/component/form/select/select-field';
import { FlowbiteService } from '../../../service/flowbite.service';
import { RoleService } from '../../../service/role/role.service';
import { FormOptionModel } from '../../../shared/model/form-option.model';

@Component({
  selector: 'app-user-filter',
  imports: [ReactiveFormsModule, SelectField],
  viewProviders: [{ provide: ControlContainer, useExisting: FormGroupDirective }],
  templateUrl: './user-filter.html',
  styleUrl: './user-filter.css',
})
export class UserFilter implements OnInit {
  roles = signal<FormOptionModel[]>([]);

  constructor(
    private readonly roleService: RoleService,
    private readonly flowbiteService: FlowbiteService
  ) { }

  ngOnInit(): void {
    this.flowbiteService.loadFlowbite((flowbite) => {
      flowbite.initFlowbite();
    });

    this.roleService.getAll().subscribe((roles) => {
      const roleOptions: FormOptionModel[] = roles.map((role) => ({
        label: role.name,
        value: role.id,
      }));

      roleOptions.unshift({
        label: 'Any Role',
        value: '',
      });

      this.roles.set(roleOptions);
    });
  }
}
