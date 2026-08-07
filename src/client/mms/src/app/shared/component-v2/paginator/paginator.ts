import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, inject, input, output, signal } from '@angular/core';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-paginator',
  imports: [MatPaginatorModule],
  templateUrl: './paginator.html',
  styleUrl: './paginator.css',
})
export class Paginator {
  pageNumber = input.required<number>();
  pageCount = input.required<number>();
  pageSize = input.required<number>();
  itemCount = input.required<number>();

  updatePage = output<PageEvent>();

  pageSizeOptions = [10, 25, 50, 100];

  showFirstLast = signal(false);

  constructor() {
    const breakpointObserver = inject(BreakpointObserver);

    breakpointObserver.observe('(min-width: 640px)').subscribe((result) => {
      this.showFirstLast.set(result.matches);
    });
  }

  goToPage(event: Event) {
    let page = Number.parseInt((event.target as HTMLInputElement).value);

    if (Number.isNaN(page) || page < 1) {
      page = 1;
    } else if (page > this.pageCount()) {
      page = this.pageCount();
    }

    const pageEvent: PageEvent = {
      pageIndex: page - 1,
      pageSize: this.pageSize(),
      length: this.itemCount(),
    };

    this.updatePage.emit(pageEvent);
  }
}
