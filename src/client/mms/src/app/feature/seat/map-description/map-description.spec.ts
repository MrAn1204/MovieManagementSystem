import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MapDescription } from './map-description';

describe('MapDescription', () => {
  let component: MapDescription;
  let fixture: ComponentFixture<MapDescription>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MapDescription]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MapDescription);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
