import { Component, computed, input, output, signal } from '@angular/core';

@Component({
  selector: 'app-pagination',
  imports: [],
  templateUrl: './pagination.html',
  styleUrl: './pagination.css',
})
export class Pagination {
  pageNumber = input.required<number>();
  pageCount = input.required<number>();
  itemCount = input.required<number>();

  changePageNumber = output<number>();
  changePageSize = output<number>();

  pageSizes = [10, 25, 50, 100];
  currentSize = signal(this.pageSizes[0]);

  pages = computed(() => this.setPagination());
  pageSummary = computed(() => {
    const start = (this.pageNumber() - 1) * this.currentSize() + 1;
    const end = Math.min(this.pageNumber() * this.currentSize(), this.itemCount());

    return `${start} - ${end} of ${this.itemCount()} entries`;
  });

  private setPagination(): (number | null)[] {
    const pageCount = this.pageCount();
    const pageNumber = this.pageNumber();

    if (pageCount <= 7) {
      return new Array(pageCount).fill(0).map((_, index) => index + 1);
    }

    let pages: (number | null)[] = [];
    if (pageNumber <= 4) {
      pages = new Array(5).fill(0).map((_, index) => index + 1);
      pages.push(null, pageCount);
    } else if (pageNumber <= pageCount - 4) {
      pages = [1, null, pageNumber - 1, pageNumber, pageNumber + 1, null, pageCount];
    } else {
      pages = [1, null, pageCount - 4, pageCount - 3, pageCount - 2, pageCount - 1, pageCount];
    }

    return pages;
  }

  updatePageSize(event: Event): void {
    const size = Number.parseInt((event.target as HTMLSelectElement).value);
    this.currentSize.set(size);
    this.changePageSize.emit(size);
  }

  goToPage(event: Event): void {
    const page = Number.parseInt((event.target as HTMLSelectElement).value);
    this.updatePageNumber(page);
  }

  updatePageNumber(page: number): void {
    if (page < 1 || page > this.pageCount()) {
      return;
    }
    this.changePageNumber.emit(page);
  }
}
