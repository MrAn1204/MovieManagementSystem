import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ErrorMessageModel } from "../shared/model/ErrorMessageModel";
import { firstValueFrom } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  private readonly url = 'http://localhost:8080/api/app-messages';

  private messages: Record<string, string> = {};

  constructor(private readonly http: HttpClient) { }

  async loadAll(): Promise<void> {
    await firstValueFrom(this.http.get<Record<string, string>>(this.url)).then(res => {
      this.messages = res;
    });
  }

  get(error: ErrorMessageModel | string): string {
    if (typeof error === 'string') {
      return error;
    }

    const { message, args } = error;

    let displayMessage = this.messages[message] ?? message;

    if (args) {
      for (const argKey in args) {
        displayMessage = displayMessage.replace(`{${argKey}}`, args[argKey]);
      }
    }

    return displayMessage;
  }
}
