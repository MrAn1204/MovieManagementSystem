import { Component, OnInit, signal } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { FormOptionModel } from '../../../shared/model/form-option.model';
import { FormSelect } from "../../../shared/component-v2/form/form-select/form-select";
import { Filter } from '../../../shared/component-v2/filter/filter';
import { RoleService } from '../../../service/role/role.service';

@Component({
  selector: 'app-user-filter-v2',
  imports: [ReactiveFormsModule, FormSelect],
  templateUrl: './user-filter-v2.html',
  styleUrl: './user-filter-v2.css',
})
export class UserFilterV2 extends Filter<UserFilterForm> implements OnInit {
  roles = signal<FormOptionModel[]>([]);

  constructor(private readonly roleService: RoleService) {
    super();
  }

  ngOnInit(): void {
    this.roleService.getAll().subscribe((roles) => {
      const roleOptions: FormOptionModel[] = roles.map((role) => ({
        label: role.name,
        value: role.id,
      }));

      roleOptions.unshift({ label: 'Any Role', value: '' });
      this.roles.set(roleOptions);
    });
  }
}

export type UserFilterForm = {
  roleId: FormControl<string | undefined>;
}
