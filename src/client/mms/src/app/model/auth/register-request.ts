export interface RegisterRequest {
    username: string;
    password: string;
    confirmPassword: string;
    fullname: string;
    gender: 'MALE' | 'FEMALE' | 'OTHER';
    dateOfBirth: string;
    email: string;
    phoneNumber: string;
}