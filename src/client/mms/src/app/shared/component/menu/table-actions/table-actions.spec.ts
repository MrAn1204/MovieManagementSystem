import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableActions } from './table-actions';

describe('TableActions', () => {
  let component: TableActions;
  let fixture: ComponentFixture<TableActions>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TableActions]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TableActions);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
