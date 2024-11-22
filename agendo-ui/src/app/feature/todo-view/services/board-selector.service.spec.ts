import { TestBed } from '@angular/core/testing';

import { BoardSelectorService } from './board-selector.service';

describe('BoardSelectorService', () => {
  let service: BoardSelectorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BoardSelectorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
