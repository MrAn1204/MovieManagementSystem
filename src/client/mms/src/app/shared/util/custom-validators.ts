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

  private static buildError(name: string, message: string, args?: Record<string, any>): ValidationErrors {
    return {
      [name]: {
        message: message,
        args: args
      }
    };
  }

  static required(message: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const value = control.value;

      if (value == null ||
          (typeof value === 'string' && value.trim() === '') ||
          (Array.isArray(value) && value.length === 0) ||
          (typeof value === 'object' && Object.keys(value).length === 0)
      ) {
        // use key 'mandatory' since Angular Material already uses 'required'
        return this.buildError('mandatory', message);
      }

      return null;
    };
  }

  static minLength(length: number, message: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (control.value && String(control.value).length < length) {
        return this.buildError('minLength', message, {
          value: length
        });
      }

      return null;
    };
  }

  static maxLength(length: number, message: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (control.value && String(control.value).length > length) {
        return this.buildError('maxLength', message, {
          value: length
        });
      }

      return null;
    };
  }

  static length(min: number, max: number, message: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!control.value) {
        return null;
      }

      const valueLength = String(control.value).length;

      if (valueLength < min || valueLength > max) {
        return this.buildError('length', message, {
          min: min,
          max: max
        });
      }

      return null;
    }
  }

  static min(min: number, message: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (control.value != null && control.value < min) {
        return this.buildError('min', message, {
          value: min
        });
      }

      return null;
    }
  }

  static max(max: number, message: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (control.value != null && control.value > max) {
        return this.buildError('max', message, {
          value: max
        });
      }

      return null;
    }
  }

  static range(min: number, max: number, message: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (!control.value) {
        return null;
      }

      const value = Number(control.value);

      if (value < min || value > max) {
        return this.buildError('range', message, {
          min: min,
          max: max
        });
      }

      return null;
    }
  }

  static email(message: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (control.value && Validators.email(control)) {
        return this.buildError('email', message);
      }

      return null;
    };
  }

  static pastDate(message: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (control.value) {
        const inputDate = new Date(control.value);
        const today = new Date();

        inputDate.setHours(0, 0, 0, 0);
        today.setHours(0, 0, 0, 0);

        if (inputDate >= today) {
          return this.buildError('pastDate', message);
        }
      }

      return null;
    };
  }

  static arrayContainNoNull(message: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const value = control.value;

      if (Array.isArray(value) && value.some(item => item === null || item === undefined)) {
        return this.buildError('arrayContainNull', message);
      }

      return null;
    };
  }

  static passwordValid(minLength: number, message: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const password = control.value;

      if (!password) {
        return null;
      }

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

      return this.buildError('passwordInvalid', message, {
        min: minLength
      });
    };
  }


  static passwordMatch(message: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const password = control.get('password');
      const confirmPassword = control.get('confirmPassword');

      if (password?.value !== confirmPassword?.value) {
        const error = this.buildError('passwordMismatch', message);

        confirmPassword?.setErrors(error);
      }

      return null;
    }
  }
}
