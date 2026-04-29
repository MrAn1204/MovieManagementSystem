import { Component } from '@angular/core';
import { RouterLink } from "@angular/router";
import { Button } from "../../../shared/component/button/button";

@Component({
  selector: 'app-not-found-page',
  imports: [RouterLink, Button],
  templateUrl: './not-found-page.html',
  styleUrl: './not-found-page.css',
})
export class NotFoundPage {

}
