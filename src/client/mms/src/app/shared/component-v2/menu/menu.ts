import { Component } from '@angular/core';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';
import {MatButtonModule} from '@angular/material/button';


@Component({
  selector: 'app-menu',
  imports: [MatIconModule, MatMenuModule, MatButtonModule],
  templateUrl: './menu.html',
  styleUrl: './menu.css',
})
export class Menu {

}
