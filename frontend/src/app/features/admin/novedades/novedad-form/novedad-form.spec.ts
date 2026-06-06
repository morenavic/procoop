import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NovedadFormComponent } from './novedad-form';

describe('NovedadForm', () => {
  let component: NovedadFormComponent;
  let fixture: ComponentFixture<NovedadFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NovedadForm],
    }).compileComponents();

    fixture = TestBed.createComponent(NovedadForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
