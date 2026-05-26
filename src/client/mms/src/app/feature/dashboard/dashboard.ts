import { Component, computed, OnInit, signal } from '@angular/core';
import { ChartjsComponent } from "@coreui/angular-chartjs";
import { Table } from "../../shared/component/table/table";
import { ChartData, ChartOptions } from 'chart.js';
import { StatisticsSummaryModel } from '../../model/statistics/statistics-summary.model';
import { TodayScheduleStatisticsModel } from '../../model/statistics/today-schedule-statistics';
import { UpcomingMovieStatisticsModel } from '../../model/statistics/upcoming-movie-statistics.model';
import { StatisticsService } from '../../service/statistics/statistics.service';
import { TableColumnModel } from '../../shared/model/table-column.model';
import { RouterLink } from "@angular/router";
import { AuthService } from '../../service/auth/auth.service';
import { RoleName } from '../../shared/model/role-config.model';
import { Button } from "../../shared/component/button/button";
import { MovieDetail } from '../movie/detail/movie-detail';
import { DialogDataModel } from '../../shared/model/dialog/dialog-data.model';
import { DialogService } from '../../service/dialog/dialog.service';
import { MovieDetailModel } from '../../model/movie/movie-detail.model';
import { MovieService } from '../../service/movie/movie.service';
import { ScheduleService } from '../../service/schedule/schedule.service';
import { SpinnerService } from '../../service/ui/spinner/spinner.service';
import { finalize } from 'rxjs';
import { ScheduleDetailModel } from '../../model/schedule/schedule-detail.model';
import { ScheduleDetail } from '../schedule/detail/schedule-detail';

@Component({
  selector: 'app-dashboard',
  imports: [ChartjsComponent, Table, RouterLink, Button],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {
  summary = signal<StatisticsSummaryModel | null>(null);

  movieColumns: TableColumnModel<UpcomingMovieStatisticsModel>[] = [
    { key: 'name', label: 'Name', type: 'string' },
    { key: 'releaseDate', label: 'Release Date', type: 'date' },
  ]

  scheduleColumns: TableColumnModel<TodayScheduleStatisticsModel>[] = [
    { key: 'movieName', label: 'Movie Name', type: 'string' },
    { key: 'showTime', label: 'Show Time', type: 'time' },
    { key: 'roomName', label: 'Room Name', type: 'string' },
  ];

  upcomingMovies = computed(() => this.summary()?.upcomingMovies ?? []);
  todaySchedules = computed(() => this.summary()?.todaySchedules ?? []);

  ticketSold = computed<ChartData>(() => {
    const labels = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
    const data = [...(this.summary()?.monthlyTicketsSold ?? new Array(12).fill(0))];

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
    plugins: {
      legend: {
        display: false,
      },
    }
  }

  constructor(
    private readonly authService: AuthService,
    private readonly statisticsService: StatisticsService,
    private readonly dialogService: DialogService,
    private readonly spinner: SpinnerService,
    private readonly movieService: MovieService,
    private readonly scheduleService: ScheduleService,
  ) {

  }

  ngOnInit(): void {
    this.statisticsService.getSummary().subscribe(summary => {
      this.summary.set(summary);
    });
  }

  isVisible(roles: RoleName[] = ['ADMIN']): boolean {
    return this.authService.includeRoles(roles);
  }

  viewMovie(id: string): void {
    this.spinner.show();

    this.movieService.getById(id)
      .pipe(finalize(() => this.spinner.hide()))
      .subscribe(movie => {
        const dialogData: DialogDataModel<MovieDetailModel> = {
          title: 'Movie Details',
          model: movie,
        }

        this.dialogService.openDialog(MovieDetail, dialogData);
      });
  }

  viewSchedule(id: string): void {
    this.spinner.show();

    this.scheduleService.getById(id)
      .pipe(finalize(() => this.spinner.hide()))
      .subscribe(schedule => {
        const dialogData: DialogDataModel<ScheduleDetailModel> = {
          title: 'Schedule Details',
          model: schedule,
        }

        this.dialogService.openDialog(ScheduleDetail, dialogData);
      });
  }
}
