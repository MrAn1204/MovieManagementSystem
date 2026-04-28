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

@Component({
  selector: 'app-dashboard',
  imports: [ChartjsComponent, Table, RouterLink],
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
    private readonly statisticsService: StatisticsService,
  ) {

  }

  ngOnInit(): void {
    this.statisticsService.getSummary().subscribe(summary => {
      this.summary.set(summary);
    });
  }
}
