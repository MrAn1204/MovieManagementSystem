import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailText } from './detail-text';

describe('DetailText', () => {
  let component: DetailText;
  let fixture: ComponentFixture<DetailText>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetailText]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetailText);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
