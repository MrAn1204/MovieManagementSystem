import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, computed, inject, signal } from '@angular/core';
import { AuthService } from '../../service/auth/auth.service';
import { HeaderV2 } from "../component-v2/header/header";
import { MatSidenavModule } from '@angular/material/sidenav';
import { SidebarV2 } from "../component-v2/sidebar/sidebar";
import { RouterOutlet } from "@angular/router";

@Component({
  selector: 'app-main-layout-v2',
  imports: [HeaderV2, SidebarV2, MatSidenavModule, RouterOutlet],
  templateUrl: './main-layout-v2.html',
  styleUrl: './main-layout-v2.css',
})
export class MainLayoutV2 {
  private readonly authService = inject(AuthService);

  protected readonly showSidebar = signal(true);

  sidebarMode = computed<'side' | 'over'>(() => this.showSidebar() ? 'side' : 'over');

  protected hasUser = computed<boolean>(() => this.authService.isAuthenticated());

  constructor() {
    const breakpointObserver = inject(BreakpointObserver);

    breakpointObserver.observe('(min-width: 1024px)').subscribe((result) => {
      this.showSidebar.set(result.matches);
    });
  }
}
