export type Role = 'user' | 'bot';

export interface ChatMessage {
  role: Role;
  text: string;
  time: Date;
}

export interface ChatRequest {
  sender: string;
  message: string;
}

export interface ChatReply {
  text: string;
}

export interface ChatResponse {
  replies: ChatReply[];
}
