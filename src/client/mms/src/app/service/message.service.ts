import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  private readonly url = 'http://localhost:8080/api/app-messages';

  messages: Record<string, string> = {};

  constructor(private readonly http: HttpClient) { }

  async loadAll(): Promise<void> {
    this.http.get<Record<string, string>>(this.url).subscribe(res => {
      this.messages = res;
    });
  }

  get(key: string, args?: Record<string, any>): string {
    let message = this.messages[key];

    if (!message) {
      return key;
    }

    if (args) {
      for (const argKey in args) {
        message = message.replace(`{${argKey}}`, args[argKey]);
      }
    }

    return message;
  }
}
