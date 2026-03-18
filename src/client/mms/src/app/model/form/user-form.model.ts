export interface UserFormModel {
  username: string;
  fullname: string;
  password: string;
  confirmPassword: string;
  gender: 'MALE' | 'FEMALE' | 'OTHER';
  dateOfBirth: string;
  email: string;
  citizenIdNumber: string;
  phoneNumber: string;
  address: string;
  score: number;
  roleIds: string[];
}
