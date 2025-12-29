import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ChatRequest { senderId: string; message: string; }
export interface ChatResponse { replies: { text: string }[]; }

@Injectable({ providedIn: 'root' })
export class ChatService {
  private apiUrl = 'api/chat'; // hoặc '/api/chat' nếu có proxy
  constructor(private http: HttpClient) {}

  sendMessage(body: { sender: string; message: string }): Observable<ChatResponse> {
    return this.http.post<ChatResponse>(this.apiUrl, body);
  }
}
