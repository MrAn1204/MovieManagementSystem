import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableMenu } from './table-menu';

describe('TableMenu', () => {
  let component: TableMenu;
  let fixture: ComponentFixture<TableMenu>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TableMenu]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TableMenu);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
