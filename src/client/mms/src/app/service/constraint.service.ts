import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class ConstraintService {
  private readonly url = 'http://localhost:8080/api/app-constraints';

  private constraints: Record<string, string> = {};

  constructor(private readonly http: HttpClient) { }

  loadAll(): void {
    this.http.get<Record<string, string>>(this.url).subscribe(res => {
      this.constraints = res;
    });
  }

  get(...keys: string[]): Record<string, any> {
    const result: Record<string, any> = {

    };
    keys.forEach(key => {
      result[key] = this.constraints[key] || key;
    });

    return result;
  }
}
