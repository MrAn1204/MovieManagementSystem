import { Component, OnInit, signal } from '@angular/core';
import { ControlContainer, FormGroupDirective, ReactiveFormsModule } from '@angular/forms';
import { InputField } from '../../../shared/component/form/input/input-field';
import { SelectField } from '../../../shared/component/form/select/select-field';
import { FormOptionModel } from '../../../shared/model/form-option.model';
import { FlowbiteService } from '../../../service/flowbite.service';
import { MovieService } from '../../../service/movie/movie.service';
import { RoomService } from '../../../service/room/room.service';
import { PromotionService } from '../../../service/promotion/promotion.service';

@Component({
  selector: 'app-ticket-filter',
  imports: [InputField, SelectField, ReactiveFormsModule],
  viewProviders: [{ provide: ControlContainer, useExisting: FormGroupDirective }],
  templateUrl: './ticket-filter.html',
  styleUrl: './ticket-filter.css',
})
export class TicketFilter implements OnInit {
  movies = signal<FormOptionModel[]>([]);
  rooms = signal<FormOptionModel[]>([]);
  promotions = signal<FormOptionModel[]>([]);

  constructor(
    private readonly movieService: MovieService,
    private readonly roomService: RoomService,
    private readonly promotionService: PromotionService,
    private readonly flowbiteService: FlowbiteService,
  ) {
  }

  ngOnInit(): void {
    this.flowbiteService.loadFlowbite((flowbite) => {
      flowbite.initFlowbite();
    });

    this.loadMovies();
    this.loadRooms();
    this.loadPromotions();
  }

  private loadMovies(): void {
    this.movieService.getAll().subscribe((movies) => {
      const movieOptions: FormOptionModel[] = movies.map((movie) => ({
        label: movie.name,
        value: movie.id,
        selected: false,
      }));

      movieOptions.unshift({
        label: 'Any Movie',
        value: '',
        selected: true,
      });

      this.movies.set(movieOptions);
    });
  }

  private loadRooms(): void {
    this.roomService.getAll().subscribe((rooms) => {
      const roomOptions: FormOptionModel[] = rooms.map((room) => ({
        label: room.name,
        value: room.id,
        selected: false,
      }));

      roomOptions.unshift({
        label: 'Any Room',
        value: '',
        selected: true,
      });

      this.rooms.set(roomOptions);
    });
  }

  private loadPromotions(): void {
    this.promotionService.getAll().subscribe((promotions) => {
      const promotionOptions: FormOptionModel[] = promotions.map((promotion) => ({
        label: promotion.title,
        value: promotion.id,
        selected: false,
      }));

      promotionOptions.unshift({
        label: 'Any Promotion',
        value: '',
        selected: true,
      });

      this.promotions.set(promotionOptions);
    });
  }
}
