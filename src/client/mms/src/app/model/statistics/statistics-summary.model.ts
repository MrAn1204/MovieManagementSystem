import { TodayScheduleStatisticsModel } from './today-schedule-statistics';
import { UpcomingMovieStatisticsModel } from './upcoming-movie-statistics.model';

export interface StatisticsSummaryModel {
  movieCount: number;
  ticketCount: number;
  roomCount: number;
  scheduleCount: number;
  promotionCount: number;
  userCount: number;
  monthlyTicketsSold: number[];
  upcomingMovies: UpcomingMovieStatisticsModel[];
  todaySchedules: TodayScheduleStatisticsModel[];
}
