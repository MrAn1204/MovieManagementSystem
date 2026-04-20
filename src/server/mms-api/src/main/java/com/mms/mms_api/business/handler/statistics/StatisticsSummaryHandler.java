package com.mms.mms_api.business.handler.statistics;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.handler.BaseHandler;
import com.mms.mms_api.business.query.statistics.StatisticsSummaryQuery;
import com.mms.mms_api.data.InvoiceRepository;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.data.PromotionRepository;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.data.TicketRepository;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.data.projection.MonthlyTicketSalesProjection;
import com.mms.mms_api.dto.statistics.StatisticsSummaryDto;
import com.mms.mms_api.dto.statistics.TodayScheduleStatisticsDto;
import com.mms.mms_api.dto.statistics.UpcomingMovieStatisticsDto;

/**
 * Handles dashboard statistics summary requests.
 */
@Component
public class StatisticsSummaryHandler extends BaseHandler<StatisticsSummaryQuery, StatisticsSummaryDto> {
    private final MovieRepository movieRepository;
    private final TicketRepository ticketRepository;
    private final RoomRepository roomRepository;
    private final ScheduleRepository scheduleRepository;
    private final PromotionRepository promotionRepository;
    private final UserRepository userRepository;
    private final InvoiceRepository invoiceRepository;

    /**
     * Creates a StatisticsSummaryHandler.
     */
    public StatisticsSummaryHandler(MovieRepository movieRepository,
            TicketRepository ticketRepository,
            RoomRepository roomRepository,
            ScheduleRepository scheduleRepository,
            PromotionRepository promotionRepository,
            UserRepository userRepository,
            InvoiceRepository invoiceRepository) {
        this.movieRepository = movieRepository;
        this.ticketRepository = ticketRepository;
        this.roomRepository = roomRepository;
        this.scheduleRepository = scheduleRepository;
        this.promotionRepository = promotionRepository;
        this.userRepository = userRepository;
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * Executes the statistics summary query.
     */
    @Override
    public StatisticsSummaryDto execute(StatisticsSummaryQuery request) {
        LocalDate today = LocalDate.now();

        LocalDateTime todayStart = today.atStartOfDay();
        LocalDateTime tomorrowStart = today.plusDays(1).atStartOfDay();

        LocalDate firstDayOfYear = today.withDayOfYear(1);
        LocalDateTime yearStart = firstDayOfYear.atStartOfDay();
        LocalDateTime nextYearStart = firstDayOfYear.plusYears(1).atStartOfDay();

        StatisticsSummaryDto result = new StatisticsSummaryDto();

        result.setMovieCount(movieRepository.count());
        result.setTicketCount(ticketRepository.count());
        result.setRoomCount(roomRepository.count());
        result.setScheduleCount(scheduleRepository.count());
        result.setPromotionCount(promotionRepository.count());
        result.setUserCount(userRepository.count());

        List<Long> monthlyTicketsSold = getTicketsSold(yearStart, nextYearStart);
        result.setMonthlyTicketsSold(monthlyTicketsSold);

        List<UpcomingMovieStatisticsDto> upcomingMovies = getUpcomingMovies(today);
        result.setUpcomingMovies(upcomingMovies);

        List<TodayScheduleStatisticsDto> todaySchedules;
        todaySchedules = getTodaySchedules(todayStart, tomorrowStart);
        result.setTodaySchedules(todaySchedules);

        return result;
    }

    private List<UpcomingMovieStatisticsDto> getUpcomingMovies(LocalDate today) {
        return movieRepository
                .findUpcomingMovies(today, today.plusYears(1))
                .stream()
                .map(projection -> {
                    UpcomingMovieStatisticsDto dto = new UpcomingMovieStatisticsDto();
                    dto.setId(projection.getId());
                    dto.setName(projection.getName());
                    dto.setReleaseDate(projection.getReleaseDate());
                    return dto;
                })
                .toList();
    }

    private List<TodayScheduleStatisticsDto> getTodaySchedules(LocalDateTime todayStart, LocalDateTime tomorrowStart) {
        return scheduleRepository
                .findUpcomingSchedules(todayStart, tomorrowStart)
                .stream()
                .map(projection -> {
                    TodayScheduleStatisticsDto dto = new TodayScheduleStatisticsDto();
                    dto.setId(projection.getId());
                    dto.setMovieName(projection.getMovieName());
                    dto.setRoomName(projection.getRoomName());
                    dto.setShowTime(projection.getShowTime());

                    return dto;
                })
                .toList();
    }

    private List<Long> getTicketsSold(LocalDateTime yearStart, LocalDateTime nextYearStart) {
        List<Long> monthlyTicketsSold = new ArrayList<>(12);
        for (int monthIndex = 0; monthIndex < 12; monthIndex++) {
            monthlyTicketsSold.add(0L);
        }

        List<MonthlyTicketSalesProjection> monthlyRows = invoiceRepository.countSoldTicketsByMonth(yearStart,
                nextYearStart);
        for (MonthlyTicketSalesProjection monthlyRow : monthlyRows) {
            Integer monthNumber = monthlyRow.getMonthNumber();
            if (monthNumber == null || monthNumber < 1 || monthNumber > 12) {
                continue;
            }

            monthlyTicketsSold.set(monthNumber - 1, monthlyRow.getTicketCount());
        }
        return monthlyTicketsSold;
    }
}