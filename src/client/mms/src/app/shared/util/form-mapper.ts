import { ErrorRespondModel } from "../model/error-respond.model";
import { FormGroup } from "@angular/forms";

export abstract class FormMapper {
  static toFormData(form: Record<string, any>): FormData {
    const formData = new FormData();

    for (const key in form) {
      const value = form[key];

      if (!value) {
        continue;
      } else if (value instanceof File) {
        formData.append(key, value, value.name);
      } else if (Array.isArray(value)) {
        value.forEach((item: any) => formData.append(key, item));
      } else {
        formData.append(key, value);
      }
    }

    return formData;
  }

  static mapErrorResponse(res: ErrorRespondModel, form: FormGroup): void {
    if (!res || !form) {
      return;
    }

    const messages = res.messages;

    for (const field in messages) {
      const control = form.get(field);

      if (control) {
        control.setErrors({ serverError: messages[field] });
      } else {
        form.setErrors({ serverError: messages[field] });
      }
    }
  }
}
