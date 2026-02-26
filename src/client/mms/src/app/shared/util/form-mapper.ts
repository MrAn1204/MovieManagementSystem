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
}
