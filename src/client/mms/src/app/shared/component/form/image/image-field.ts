import { Component, signal } from '@angular/core';
import { BaseField } from '../base-field/base-field';

@Component({
  selector: 'app-image-field',
  imports: [],
  templateUrl: './image-field.html',
  styleUrl: './image-field.css',
})
export class ImageField extends BaseField<File> {
  private readonly DEFAULT_SRC = 'https://dummyimage.com/300x400/dddddd/000000&text=No+Image';

  imagePreviewSrc = signal(this.DEFAULT_SRC);

  onFileChange(event: Event) {
    const input = event.target as HTMLInputElement;
    
    if (input.files?.[0]) {
      const file = input.files[0];
      const reader = new FileReader();
      
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.imagePreviewSrc.set(reader.result as string);
      };

      this.updateValue(file);
    }
  }
}
