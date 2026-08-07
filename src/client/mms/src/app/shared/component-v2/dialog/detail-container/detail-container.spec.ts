import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailContainer } from './detail-container';

describe('DetailContainer', () => {
  let component: DetailContainer;
  let fixture: ComponentFixture<DetailContainer>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetailContainer]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetailContainer);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
