import { AbstractControl, ValidationErrors } from "@angular/forms";

export class CustomValidators {
  private static readonly passwordPattern = {
    minLength: 8,
    hasUpper: /[A-Z]/,
    hasLower: /[a-z]/,
    hasDigit: /\d/,
    hasSpecial: /[!@#$%^&*()\-_=+[\]{};:',.?/]/,
    hasNoWhitespace: /^\S*$/
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

    return { passwordInvalid: true };
  }


  static passwordMatch(control: AbstractControl): ValidationErrors | null {
    const password = control.get('password')?.value;
    const confirmPassword = control.get('confirmPassword')?.value;

    if (password !== confirmPassword) {
      return { passwordMismatch: true };
    }

    return null;
  }
}