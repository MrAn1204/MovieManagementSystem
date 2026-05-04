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
  schedule: {
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
  promotion: {
    create: ['ADMIN'],
    edit: ['ADMIN'],
    delete: ['ADMIN'],
    getAll: ['ADMIN', 'USER'],
    getById: ['ADMIN', 'USER'],
    search: ['ADMIN', 'USER']
  },
  ticket: {
    create: ['ADMIN', 'USER'],
    edit: ['ADMIN'],
    delete: ['ADMIN'],
    getAll: ['ADMIN'],
    getById: ['ADMIN', 'USER'],
    search: ['ADMIN']
  },
  seat: {
    create: ['ADMIN'],
    edit: ['ADMIN'],
    delete: ['ADMIN'],
    getAll: ['ADMIN', 'USER'],
    getById: ['ADMIN', 'USER'],
  },
  user: {
    create: ['ADMIN'],
    edit: ['ADMIN', 'USER'],
    delete: ['ADMIN'],
    getAll: ['ADMIN'],
    getById: ['ADMIN'],
    search: ['ADMIN']
  },
  invoice: {
    create: ['ADMIN'],
    edit: ['ADMIN'],
    delete: ['ADMIN'],
    getAll: ['ADMIN'],
    getById: ['ADMIN', 'USER'],
  },
}

export function getRoleConfig(feature: string): RoleConfigModel {
  return ROLE_CONFIG[feature.toLowerCase()] ?? DEFAULT_ROLE_CONFIG;
}

