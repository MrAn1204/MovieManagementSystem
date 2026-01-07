import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailButtons } from './detail-buttons';

describe('DetailButtons', () => {
  let component: DetailButtons;
  let fixture: ComponentFixture<DetailButtons>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetailButtons]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetailButtons);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
