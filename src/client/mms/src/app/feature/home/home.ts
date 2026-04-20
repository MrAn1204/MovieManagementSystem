import { Component, computed, OnInit, signal } from '@angular/core';
import { ChartjsComponent } from "@coreui/angular-chartjs";
import { RouterLink } from "@angular/router";
import { ChartData, ChartOptions } from 'chart.js';
import { Table } from "../../shared/component/table/table";
import { TableColumnModel } from '../../shared/model/table-column.model';
import { StatisticsService } from '../../service/statistics/statistics.service';
import { StatisticsSummaryModel } from '../../model/statistics/statistics-summary.model';
import { UpcomingMovieStatisticsModel } from '../../model/statistics/upcoming-movie-statistics.model';
import { TodayScheduleStatisticsModel } from '../../model/statistics/today-schedule-statistics';

@Component({
  selector: 'app-home',
  imports: [ChartjsComponent, RouterLink, Table],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home implements OnInit {
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
    maintainAspectRatio: false,
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
