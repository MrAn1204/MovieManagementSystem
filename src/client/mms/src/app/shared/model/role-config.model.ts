export interface RoleConfigModel {
  create: RoleName[];
  edit: RoleName[];
  delete: RoleName[];
  getAll: RoleName[];
  getById: RoleName[];
  search?: RoleName[];
}

export type RoleName = 'ADMIN' | 'USER';
