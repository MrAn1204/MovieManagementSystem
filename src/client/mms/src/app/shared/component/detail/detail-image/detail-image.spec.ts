import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailImage } from './detail-image';

describe('DetailImage', () => {
  let component: DetailImage;
  let fixture: ComponentFixture<DetailImage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetailImage]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetailImage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
