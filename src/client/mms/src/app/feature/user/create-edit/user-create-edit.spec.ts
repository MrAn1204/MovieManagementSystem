import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserCreateEdit } from './user-create-edit';

describe('UserCreateEdit', () => {
  let component: UserCreateEdit;
  let fixture: ComponentFixture<UserCreateEdit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserCreateEdit]
    })
      .compileComponents();

    fixture = TestBed.createComponent(UserCreateEdit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
