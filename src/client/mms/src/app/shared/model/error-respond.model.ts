export interface ErrorRespondModel {
  messages: Record<string, string>;
  status: number;
  error: string;
  timestamp: string;
}
