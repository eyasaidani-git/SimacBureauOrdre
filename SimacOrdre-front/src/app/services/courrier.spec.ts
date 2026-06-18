import { TestBed } from '@angular/core/testing';

import { Courrier } from './courrier';

describe('Courrier', () => {
  let service: Courrier;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Courrier);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
