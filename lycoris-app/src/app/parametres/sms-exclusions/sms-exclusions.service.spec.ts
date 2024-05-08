import { TestBed } from '@angular/core/testing';

import { SmsExclusionsService } from './sms-exclusions.service';

describe('SmsExclusionsService', () => {
  let service: SmsExclusionsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SmsExclusionsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
