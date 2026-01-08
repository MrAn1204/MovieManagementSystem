import { Component, effect, forwardRef, signal } from '@angular/core';
import { BaseField } from '../base-field/base-field';
import { NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'app-image-field',
  imports: [],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: forwardRef(() => ImageField)
    },
  ],
  templateUrl: './image-field.html',
  styleUrl: './image-field.css',
})
export class ImageField extends BaseField<string> {
  private readonly DEFAULT_SRC = 'https://dummyimage.com/300x400/dddddd/000000&text=No+Image';

  imagePreviewSrc = signal(this.DEFAULT_SRC);

  constructor() {
    super();

    effect(() => {
      this.imagePreviewSrc.set(this.value ?? this.DEFAULT_SRC);
    });
  }

  onFileChange(event: Event) {
    const input = event.target as HTMLInputElement;
    
    if (input.files?.[0]) {
      const file = input.files[0];
      const reader = new FileReader();
      
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.imagePreviewSrc.set(reader.result as string);
      };
    }
  }
}
