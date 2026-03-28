import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogButtons } from './dialog-buttons';

describe('DetailButtons', () => {
  let component: DialogButtons;
  let fixture: ComponentFixture<DialogButtons>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DialogButtons]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DialogButtons);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
