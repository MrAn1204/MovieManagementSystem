import { Component, computed, OnInit, signal } from '@angular/core';
import { TableV2 } from "../../shared/component-v2/table/table";
import { ChartData } from 'chart.js';
import { StatisticsSummaryModel } from '../../model/statistics/statistics-summary.model';
import { TodayScheduleStatisticsModel } from '../../model/statistics/today-schedule-statistics';
import { UpcomingMovieStatisticsModel } from '../../model/statistics/upcoming-movie-statistics.model';
import { StatisticsService } from '../../service/statistics/statistics.service';
import { TableColumnModel } from '../../shared/model/table-column.model';
import { RouterLink } from "@angular/router";
import { AuthService } from '../../service/auth/auth.service';
import { RoleName } from '../../shared/model/role-config.model';
import { MovieDialogService } from '../../service/dialog-v2/movie/movie-dialog.service';
import { ScheduleDialogService } from '../../service/dialog-v2/schedule/schedule-dialog.service';
import { BaseChartDirective } from 'ng2-charts';

@Component({
  selector: 'app-dashboard-v2',
  imports: [TableV2, RouterLink, BaseChartDirective],
  templateUrl: './dashboard-v2.html',
  styleUrl: './dashboard-v2.css',
})
export class DashboardV2 implements OnInit {
  summary = signal<StatisticsSummaryModel | null>(null);

  movieColumns: TableColumnModel<UpcomingMovieStatisticsModel>[] = [
    { key: 'name', label: 'Name', type: 'string' },
    { key: 'releaseDate', label: 'Release Date', type: 'date' },
  ];

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
      datasets: [{ label: 'Tickets Sold', data }]
    };
  });

  constructor(
    private readonly authService: AuthService,
    private readonly statisticsService: StatisticsService,
    private readonly movieDialog: MovieDialogService,
    private readonly scheduleDialog: ScheduleDialogService,
  ) {}

  ngOnInit(): void {
    this.statisticsService.getSummary().subscribe(summary => {
      this.summary.set(summary);
    });
  }

  isVisible(roles: RoleName[] = ['ADMIN']): boolean {
    return this.authService.includeRoles(roles);
  }

  viewMovie(id: string): void {
    this.movieDialog.displayInfo(id);
  }

  viewSchedule(id: string): void {
    this.scheduleDialog.displayInfo(id);
  }
}
