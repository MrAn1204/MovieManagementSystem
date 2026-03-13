import { Component, inject, OnInit, signal } from '@angular/core';
import { InputField } from "../../../shared/component/form/input/input-field";
import { ImageField } from "../../../shared/component/form/image/image-field";
import { SelectField } from "../../../shared/component/form/select/select-field";
import { MultiselectField } from "../../../shared/component/form/multiselect/multiselect-field";
import { Textarea } from "../../../shared/component/form/textarea/textarea-field";
import { MovieModel } from '../../../model/movie.model';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { GenreService } from '../../../service/genre/genre.service';
import { StudioService } from '../../../service/studio/studio.service';
import { TalentService } from '../../../service/talent/talent.service';
import { LanguageService } from '../../../service/language/language.service';
import { FormOptionModel } from '../../../shared/model/form-option.model';
import { CreateEdit } from "../../../shared/component/create-edit/create-edit";
import { BaseDialog } from '../../../shared/component/dialog/base/base-dialog';
import { DIALOG_DATA } from '@angular/cdk/dialog';
import { DialogFormDataModel } from '../../../shared/model/dialog/dialog-form-data.model';

@Component({
  selector: 'app-movie-create-edit',
  imports: [InputField, ImageField, SelectField, MultiselectField, Textarea, ReactiveFormsModule, CreateEdit],
  templateUrl: './movie-create-edit.html',
  styleUrl: './movie-create-edit.css',
})
export class MovieCreateEdit extends BaseDialog implements OnInit {
  data: DialogFormDataModel<MovieModel> = inject(DIALOG_DATA);

  genres = signal<FormOptionModel[]>([]);
  studios = signal<FormOptionModel[]>([]);
  talents = signal<FormOptionModel[]>([]);
  languages = signal<FormOptionModel[]>([]);

  constructor(
    private readonly genreService: GenreService,
    private readonly studioService: StudioService,
    private readonly talentService: TalentService,
    private readonly languageService: LanguageService,
  ) {
    super();
  }

  get form(): FormGroup {
    return this.data.form;
  }

  ngOnInit(): void {
    this.loadOptions();
  }

  private loadOptions(): void {
    const model = this.data.model;

    const modelGenres = model?.genres?.map((genre) => genre.id) ?? [];
    const modelStudios = model?.studios?.map((studio) => studio.id) ?? [];
    const modelTalents = model?.talents?.map((talent) => talent.id) ?? [];
    const modelLanguage = model?.language?.id ?? null;

    this.genreService.getAll().subscribe((genres) => {
      this.genres.set(genres.map((genre) => ({
        label: genre.name,
        value: genre.id,
        selected: modelGenres.includes(genre.id) || false
      })));
    });

    this.studioService.getAll().subscribe((studios) => {
      this.studios.set(studios.map((studio) => ({
        label: studio.name,
        value: studio.id,
        selected: modelStudios.includes(studio.id) || false
      })));
    });

    this.talentService.getAll().subscribe((talents) => {
      this.talents.set(talents.map((talent) => ({
        label: talent.name,
        value: talent.id,
        selected: modelTalents.includes(talent.id) || false
      })));
    });

    this.languageService.getAll().subscribe((languages) => {
      this.languages.set(languages.map((language) => ({
        label: language.name,
        value: language.id,
        selected: modelLanguage === language.id || false
      })));
    });
  }

  onSubmit(): void {
    this.data.form.markAllAsTouched();
    if (this.data.form.invalid) {
      return;
    }
    this.dialogService.triggerSave();
  }
}
