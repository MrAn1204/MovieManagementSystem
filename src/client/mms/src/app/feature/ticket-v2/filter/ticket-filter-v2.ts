import { Component, OnInit, signal } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { FormOptionModel } from '../../../shared/model/form-option.model';
import { FormSelect } from "../../../shared/component-v2/form/form-select/form-select";
import { FormInput } from "../../../shared/component-v2/form/form-input/form-input";
import { Filter } from '../../../shared/component-v2/filter/filter';
import { MovieService } from '../../../service/movie/movie.service';
import { RoomService } from '../../../service/room/room.service';
import { PromotionService } from '../../../service/promotion/promotion.service';

@Component({
  selector: 'app-ticket-filter-v2',
  imports: [ReactiveFormsModule, FormSelect, FormInput],
  templateUrl: './ticket-filter-v2.html',
  styleUrl: './ticket-filter-v2.css',
})
export class TicketFilterV2 extends Filter<TicketFilterForm> implements OnInit {
  movies = signal<FormOptionModel[]>([]);
  rooms = signal<FormOptionModel[]>([]);
  promotions = signal<FormOptionModel[]>([]);

  constructor(
    private readonly movieService: MovieService,
    private readonly roomService: RoomService,
    private readonly promotionService: PromotionService,
  ) {
    super();
  }

  ngOnInit(): void {
    this.loadMovies();
    this.loadRooms();
    this.loadPromotions();
  }

  private loadMovies(): void {
    this.movieService.getAll().subscribe((movies) => {
      const movieOptions: FormOptionModel[] = movies.map((movie) => ({
        label: movie.name,
        value: movie.id,
      }));

      movieOptions.unshift({ label: 'Any Movie', value: '' });
      this.movies.set(movieOptions);
    });
  }

  private loadRooms(): void {
    this.roomService.getAll().subscribe((rooms) => {
      const roomOptions: FormOptionModel[] = rooms.map((room) => ({
        label: room.name,
        value: room.id,
      }));

      roomOptions.unshift({ label: 'Any Room', value: '' });
      this.rooms.set(roomOptions);
    });
  }

  private loadPromotions(): void {
    this.promotionService.getAll().subscribe((promotions) => {
      const promotionOptions: FormOptionModel[] = promotions.map((promotion) => ({
        label: promotion.title,
        value: promotion.id,
      }));

      promotionOptions.unshift({ label: 'Any Promotion', value: '' });
      this.promotions.set(promotionOptions);
    });
  }
}

export type TicketFilterForm = {
  showTime: FormControl<string | undefined>;
  movieId: FormControl<string | undefined>;
  roomId: FormControl<string | undefined>;
  promotionId: FormControl<string | undefined>;
}
