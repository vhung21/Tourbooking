import { Component, ElementRef, ViewChild } from '@angular/core';
import { finalize } from 'rxjs';
import { ChatService } from './chat.service';
import {FormsModule} from "@angular/forms";
import {ChatMessage} from "./chat.model";
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-chat-widget',
  templateUrl: './chatWidget.component.html',
  styleUrls: ['./chatWidget.component.scss'],
  imports: [
    FormsModule,
    NgIf,
    NgForOf
  ]
})
export class ChatWidgetComponent {
  @ViewChild('scrollRef') scrollRef!: ElementRef<HTMLDivElement>;

  isOpen = false;
  input = '';
  sending = false;

  messages: ChatMessage[] = [
    { role: 'bot', text: 'Chào bạn! Bạn cần mình hỗ trợ gì?', time: new Date() },
  ];

  sender = this.getOrCreateSenderId();

  constructor(private chatService: ChatService) {}

  toggle() {
    this.isOpen = !this.isOpen;
    if (this.isOpen) this.scrollToBottom();
  }

  send() {
    const text = this.input.trim();
    if (!text || this.sending) return;

    this.messages = [...this.messages, { role: 'user', text, time: new Date() }];
    this.input = '';
    this.scrollToBottom();

    this.sending = true;
    this.chatService.sendMessage({ sender: this.sender, message: text })
      .pipe(finalize(() => (this.sending = false)))
      .subscribe({
        next: (res) => {
          const replies = res?.replies ?? [];
          if (replies.length === 0) {
            this.messages.push({ role: 'bot', text: '(Không có phản hồi)', time: new Date(), });
          } else {
            for (const r of replies) this.messages.push({ role: 'bot', text: r.text, time: new Date(),});
          }
          this.scrollToBottom();
        },
        error: () => {
          this.messages.push({ role: 'bot', text: 'Lỗi kết nối. Bạn thử lại nhé.', time: new Date(), });
          this.scrollToBottom();
        },
      });
  }

  onEnter(e: KeyboardEvent) {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      this.send();
    }
  }

  private scrollToBottom() {
    setTimeout(() => {
      const el = this.scrollRef?.nativeElement;
      if (el) el.scrollTop = el.scrollHeight;
    }, 0);
  }

  private getOrCreateSenderId(): string {
    const key = 'chat_sender_id';
    const exist = localStorage.getItem(key);
    if (exist) return exist;
    const id = 'user-' + Math.random().toString(16).slice(2) + Date.now();
    localStorage.setItem(key, id);
    return id;
  }
}
