import { Component, computed, OnInit, signal } from '@angular/core';
import { ChartjsComponent } from "@coreui/angular-chartjs";
import { MovieService } from '../../service/movie/movie.service';
import { ScheduleService } from '../../service/schedule/schedule.service';
import { RoomService } from '../../service/room/room.service';
import { TicketService } from '../../service/ticket/ticket.service';
import { PromotionService } from '../../service/promotion/promotion.service';
import { UserService } from '../../service/user/user.service';
import { RouterLink } from "@angular/router";
import { MovieModel } from '../../model/movie/movie.model';
import { ScheduleModel } from '../../model/schedule/schedule.model';
import { RoomModel } from '../../model/room/room.model';
import { TicketModel } from '../../model/ticket/ticket.model';
import { UserModel } from '../../model/user/user.model';
import { PromotionModel } from '../../model/promotion/promotion.model';
import { ChartData, ChartOptions } from 'chart.js';
import { Table } from "../../shared/component/table/table";
import { TableColumnModel } from '../../shared/model/table-column.model';

@Component({
  selector: 'app-home',
  imports: [ChartjsComponent, RouterLink, Table],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home implements OnInit {
  movies = signal<MovieModel[]>([]);
  schedules = signal<ScheduleModel[]>([]);
  rooms = signal<RoomModel[]>([]);
  tickets = signal<TicketModel[]>([]);
  users = signal<UserModel[]>([]);
  promotions = signal<PromotionModel[]>([]);

  movieColumns: TableColumnModel<MovieModel>[] = [
    { key: 'name', label: 'Name', type: 'string' },
    { key: 'releaseDate', label: 'Release Date', type: 'date' },
  ]

  upcomingMovies = computed(() => {
    const now = new Date();

    return this.movies().filter(movie => {
      const releaseDate = new Date(movie.releaseDate);
      return releaseDate > now;
    });
  });

  ticketSold = computed<ChartData>(() => {
    const labels = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

    const data = new Array(12).fill(0);

    this.tickets().forEach(ticket => {
      if (!ticket.paid) {
        return;
      }

      const date = new Date(ticket.createdAt);

      if (date.getFullYear() !== new Date().getFullYear()) {
        return;
      }

      data[date.getMonth()]++;
    });

    return {
      labels,
      datasets: [
        {
          label: 'Tickets Sold',
          data,
        }
      ]
    };
  });

  chartOptions: ChartOptions = {
    responsive: true,
    maintainAspectRatio: false,
  }

  constructor(
    private readonly movieService: MovieService,
    private readonly scheduleService: ScheduleService,
    private readonly roomService: RoomService,
    private readonly ticketService: TicketService,
    private readonly promotionService: PromotionService,
    private readonly userService: UserService,
  ) {

  }

  ngOnInit(): void {
    this.movieService.getAll().subscribe(movies => {
      this.movies.set(movies);
    });

    this.scheduleService.getAll().subscribe(schedules => {
      this.schedules.set(schedules);
    });

    this.roomService.getAll().subscribe(rooms => {
      this.rooms.set(rooms);
    });

    this.ticketService.getAll().subscribe(tickets => {
      this.tickets.set(tickets);
    });

    this.promotionService.getAll().subscribe(promotions => {
      this.promotions.set(promotions);
    });

    this.userService.getAll().subscribe(users => {
      this.users.set(users);
    });
  }
}
