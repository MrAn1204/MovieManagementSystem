import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InvoiceCreateEdit } from './invoice-create-edit';

describe('InvoiceCreateEdit', () => {
  let component: InvoiceCreateEdit;
  let fixture: ComponentFixture<InvoiceCreateEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InvoiceCreateEdit]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InvoiceCreateEdit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
