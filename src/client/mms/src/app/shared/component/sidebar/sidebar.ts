import { Component, input } from '@angular/core';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-sidebar',
  imports: [RouterLink],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css',
})
export class Sidebar {
  readonly routes = [
    { path: '/', label: 'Home', icon: 'fa-house' }, 
    { path: '/movie', label: 'Movie', icon: 'fa-film' }, 
    { path: '/schedule', label: 'Schedule', icon: 'fa-calendar' }, 
    { path: '/room', label: 'Room', icon: 'fa-door-closed' }, 
    { path: '/ticket', label: 'Ticket', icon: 'fa-ticket' },
    { path: '/promotion', label: 'Promotion', icon: 'fa-percent' }, 
    { path: '/user', label: 'User', icon: 'fa-users' }
  ]
  visible = input<boolean>();

  constructor() {}

  
}
