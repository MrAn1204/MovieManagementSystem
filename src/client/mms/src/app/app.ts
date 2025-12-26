import { Component, OnInit, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Sidebar } from './shared/component/sidebar/sidebar';
import { Header } from './shared/component/header/header';
import { FlowbiteService } from './service/flowbite.service';
import { Table } from './shared/component/table/table';
import { Search } from './shared/component/search/search';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Header, Sidebar, Table, Search],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  protected readonly title = signal('mms');
  
  sidebarVisible: boolean = false;
  filterVisible: boolean = false;
  
  constructor(private readonly flowbiteService: FlowbiteService) {}

  ngOnInit(): void {
    this.flowbiteService.loadFlowbite((flowbite) => {
      flowbite.initFlowbite();
    });
  }
  
  toggleSidebar(): void {
    this.sidebarVisible = !this.sidebarVisible;
  }
}
