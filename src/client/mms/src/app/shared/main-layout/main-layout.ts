import { Component, OnInit } from '@angular/core';
import { Header } from "../component/header/header";
import { Sidebar } from "../component/sidebar/sidebar";
import { FlowbiteService } from '../../service/flowbite.service';
import { RouterOutlet } from '@angular/router';
import { AuthService } from '../../service/auth/auth.service';

@Component({
  selector: 'app-main-layout',
  imports: [Header, Sidebar, RouterOutlet],
  templateUrl: './main-layout.html',
  styleUrl: './main-layout.css',
  host: {
    '(window:resize)': 'resetSidebar()'
  }
})
export class MainLayout implements OnInit {
  sidebarVisible: boolean = false;
  hasUser: boolean = false;

  constructor(private readonly flowbiteService: FlowbiteService, private readonly authService: AuthService) { }

  ngOnInit(): void {
    this.flowbiteService.loadFlowbite((flowbite) => {
      flowbite.initFlowbite();
    });

    this.hasUser = this.authService.isAuthenticated();
  }

  resetSidebar(): void {
    if (window.innerWidth >= 1024) {
      this.sidebarVisible = false;
    }
  }

  toggleSidebar(): void {
    if (window.innerWidth >= 1024) {
      return;
    }
    this.sidebarVisible = !this.sidebarVisible;
  }
}
