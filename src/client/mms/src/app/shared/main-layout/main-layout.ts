import { Component, OnInit } from '@angular/core';
import { Header } from "../component/header/header";
import { Sidebar } from "../component/sidebar/sidebar";
import { FlowbiteService } from '../../service/flowbite.service';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-main-layout',
  imports: [Header, Sidebar, RouterOutlet],
  templateUrl: './main-layout.html',
  styleUrl: './main-layout.css',
})
export class MainLayout implements OnInit {
  sidebarVisible: boolean = false;
  filterVisible: boolean = false;

  constructor(private readonly flowbiteService: FlowbiteService) { }

  ngOnInit(): void {
    this.flowbiteService.loadFlowbite((flowbite) => {
      flowbite.initFlowbite();
    });
  }

  toggleSidebar(): void {
    this.sidebarVisible = !this.sidebarVisible;
  }
}
