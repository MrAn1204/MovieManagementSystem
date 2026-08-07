import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RowMenu } from './row-menu';

describe('RowMenu', () => {
  let component: RowMenu;
  let fixture: ComponentFixture<RowMenu>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RowMenu]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RowMenu);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
