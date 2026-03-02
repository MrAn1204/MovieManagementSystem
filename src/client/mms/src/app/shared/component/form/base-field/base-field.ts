import { Component, input } from '@angular/core';
import { ControlValueAccessor } from '@angular/forms';

@Component({
  selector: 'app-base-field',
  imports: [],
  templateUrl: './base-field.html',
  styleUrl: './base-field.css',
})
export class BaseField<T> implements ControlValueAccessor {
  labelText= input<string>('');
  idName = input.required<string>();

  value: T | null = null;
  disabled = false;

  protected onChange = (_: any) => {};
  protected onTouched = () => {};

  writeValue(value: T): void {
    this.value = value;
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  updateValue(value: T | null): void {
    this.value = value;
    this.onChange(value);
    this.onTouched();
  }
}
