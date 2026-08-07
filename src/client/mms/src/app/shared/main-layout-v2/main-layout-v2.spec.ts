import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainLayoutV2 } from './main-layout-v2';

describe('MainLayoutV2', () => {
  let component: MainLayoutV2;
  let fixture: ComponentFixture<MainLayoutV2>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MainLayoutV2]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MainLayoutV2);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
