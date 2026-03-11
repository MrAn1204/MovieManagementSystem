import { RoleConfigModel } from "../model/role-config.model";

const DEFAULT_ROLE_CONFIG: RoleConfigModel = {
  create: [],
  edit: [],
  delete: [],
  getAll: [],
  getById: [],
};

const ROLE_CONFIG: Record<string, RoleConfigModel> = {
  movie: {
    create: ['ADMIN'],
    edit: ['ADMIN'],
    delete: ['ADMIN'],
    getAll: ['ADMIN', 'USER'],
    getById: ['ADMIN', 'USER'],
    search: ['ADMIN', 'USER']
  },
  room: {
    create: ['ADMIN'],
    edit: ['ADMIN'],
    delete: ['ADMIN'],
    getAll: ['ADMIN', 'USER'],
    getById: ['ADMIN', 'USER'],
    search: ['ADMIN', 'USER']
  },
  seat: {
    create: ['ADMIN'],
    edit: ['ADMIN'],
    delete: ['ADMIN'],
    getAll: ['ADMIN', 'USER'],
    getById: ['ADMIN', 'USER'],
  },
}

export function getRoleConfig(feature: string): RoleConfigModel {
  return ROLE_CONFIG[feature.toLowerCase()] ?? DEFAULT_ROLE_CONFIG;
}

