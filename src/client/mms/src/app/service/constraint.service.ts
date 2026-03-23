import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ConstraintService {
  private readonly url = 'http://localhost:8080/api/app-constraints';

  private constraints: Record<string, string> = {};

  constructor(private readonly http: HttpClient) { }

  async loadAll(): Promise<void> {
    await firstValueFrom(this.http.get<Record<string, string>>(this.url)).then(res => {
      this.constraints = res;
    });
  }

  getConstraint(key: string): any {
    return this.constraints[key] || key;
  }

  get(...keys: string[]): Record<string, any> {
    const result: Record<string, any> = {};

    keys.forEach(key => {
      result[key] = this.constraints[key] || key;
    });

    return result;
  }
}
