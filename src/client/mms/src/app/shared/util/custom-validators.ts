import { AbstractControl, ValidationErrors, ValidatorFn, Validators } from "@angular/forms";

export class CustomValidators {
  private static readonly passwordPattern = {
    minLength: 8,
    hasUpper: /[A-Z]/,
    hasLower: /[a-z]/,
    hasDigit: /\d/,
    hasSpecial: /[!@#$%^&*()\-_=+[\]{};:',.?/]/,
    hasNoWhitespace: /^\S*$/
  }

  static required(message: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!control.value) {
        return { required: message };
      }

      return null;
    };
  }

  static minLength(length: number, message: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (String(control.value).length < length) {
        return { minLength: message };
      }

      return null;
    };
  }

  static maxLength(length: number, message: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (String(control.value).length > length) {
        return { maxLength: message };
      }

      return null;
    };
  }

  static size(min: number, max: number, message: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const value = String(control.value);

      if (value.length < min || value.length > max) {
        return { size: message };
      }

      return null;
    }
  }

  static email(message: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (Validators.email(control)) {
        return { email: message };
      }

      return null;
    };
  }

  static arrayContainNoNull(control: AbstractControl): ValidationErrors | null {
    const value = control.value;

    if (Array.isArray(value) && value.some(item => item === null || item === undefined)) {

      return { arrayContainNull: true };
    }

    return null;
  }

  static passwordValid(control: AbstractControl): ValidationErrors | null {
    const password = control.value;

    const pattern = CustomValidators.passwordPattern;

    const condition = {
      minLength: password.length >= pattern.minLength,
      hasUpper: pattern.hasUpper.test(password),
      hasLower: pattern.hasLower.test(password),
      hasDigit: pattern.hasDigit.test(password),
      hasSpecial: pattern.hasSpecial.test(password),
      hasNoWhitespace: pattern.hasNoWhitespace.test(password)
    };

    if (condition.minLength && condition.hasUpper && condition.hasLower && condition.hasDigit && condition.hasSpecial && condition.hasNoWhitespace) {
      return null;
    }

    return { passwordInvalid: 'Password must have at least 8 characters, one uppercase letter, one lowercase letter, one digit, one special character, and no whitespace' };
  }


  static passwordMatch(control: AbstractControl): ValidationErrors | null {
    const password = control.get('password');
    const confirmPassword = control.get('confirmPassword');

    if (password?.value !== confirmPassword?.value) {
      const error = { passwordMismatch: 'Passwords do not match' };

      confirmPassword?.setErrors(error);
      return error;
    }

    return null;
  }
}