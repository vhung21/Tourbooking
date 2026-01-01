import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

interface InstagramImage {
  id: number;
  src: string;
  alt: string;
  link: string;
}

@Component({
  selector: 'jhi-footer',
  standalone: true,
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.scss',
  imports: [CommonModule, FormsModule, RouterLink],
})
export default class FooterComponent {
  email = '';
  currentYear = new Date().getFullYear();

  instagramImages: InstagramImage[] = [
    {
      id: 1,
      src: 'https://images.unsplash.com/photo-1528127269322-539801943592?w=150&h=150&fit=crop',
      alt: 'Hạ Long Bay',
      link: 'javascript:void(0)',
    },
    {
      id: 2,
      src: 'https://images.unsplash.com/photo-1583417319070-4a69db38a482?w=150&h=150&fit=crop',
      alt: 'Hội An',
      link: 'javascript:void(0)',
    },
    {
      id: 3,
      src: 'https://images.unsplash.com/photo-1559592413-7cec4d0cae2b?w=150&h=150&fit=crop',
      alt: 'Đà Nẵng',
      link: 'javascript:void(0)',
    },
    {
      id: 4,
      src: 'https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=150&h=150&fit=crop',
      alt: 'Phú Quốc',
      link: 'javascript:void(0)',
    },
    {
      id: 5,
      src: 'https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=150&h=150&fit=crop',
      alt: 'Sa Pa',
      link: 'javascript:void(0)',
    },
    {
      id: 6,
      src: 'https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=150&h=150&fit=crop',
      alt: 'Đà Lạt',
      link: 'javascript:void(0)',
    },
  ];

  onSubscribe(event: Event): void {
    event.preventDefault();
    if (this.email) {
      // TODO: Implement newsletter subscription
      console.log('Newsletter subscription:', this.email);
      alert('Cảm ơn bạn đã đăng ký nhận tin!');
      this.email = '';
    }
  }
}
