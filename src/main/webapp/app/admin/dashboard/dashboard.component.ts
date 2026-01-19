import { Component, OnInit } from '@angular/core';
import { DashboardService, DashboardStatistics } from './dashboard.service';
import * as Highcharts from 'highcharts';
import HC_gantt from 'highcharts/modules/gantt';
import { CommonModule } from '@angular/common';
import { HighchartsChartModule } from 'highcharts-angular';

// Initialize Gantt module
HC_gantt(Highcharts);

@Component({
  selector: 'jhi-dashboard',
  standalone: true,
  imports: [CommonModule, HighchartsChartModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export default class DashboardComponent implements OnInit {
  Highcharts: typeof Highcharts = Highcharts;

  statistics: DashboardStatistics | null = null;
  loading = true;

  // Chart options
  pieChartOptions: Highcharts.Options = {};
  ganttChartOptions: Highcharts.Options = {};
  revenueChartOptions: Highcharts.Options = {};

  constructor(private dashboardService: DashboardService) {}

  ngOnInit(): void {
    this.loadStatistics();
  }

  loadStatistics(): void {
    this.loading = true;
    this.dashboardService.getStatistics().subscribe({
      next: data => {
        this.statistics = data;
        this.initializeCharts();
        this.loading = false;
      },
      error: error => {
        console.error('Error loading dashboard statistics:', error);
        this.loading = false;
      },
    });
  }

  initializeCharts(): void {
    if (!this.statistics) return;

    this.initPieChart();
    this.initRevenueChart();
    this.initGanttChart();
  }

  initPieChart(): void {
    if (!this.statistics) return;

    const seasonData = Object.entries(this.statistics.toursBySeason).map(([name, value]) => ({
      name: this.translateSeason(name),
      y: value,
    }));

    this.pieChartOptions = {
      chart: {
        type: 'pie',
        backgroundColor: 'transparent',
      },
      title: {
        text: 'Phân bố Tours theo Mùa',
        style: {
          color: '#1e293b',
          fontSize: '18px',
          fontWeight: '600',
        },
      },
      tooltip: {
        pointFormat: '{series.name}: <b>{point.y} tours ({point.percentage:.1f}%)</b>',
      },
      plotOptions: {
        pie: {
          allowPointSelect: true,
          cursor: 'pointer',
          dataLabels: {
            enabled: true,
            format: '<b>{point.name}</b>: {point.y} tours',
          },
          colors: ['#3b82f6', '#10b981', '#f59e0b', '#ef4444'],
        },
      },
      series: [
        {
          type: 'pie',
          name: 'Tours',
          data: seasonData,
        },
      ],
      credits: {
        enabled: false,
      },
    };
  }

  initRevenueChart(): void {
    if (!this.statistics) return;

    const categories = this.statistics.monthlyRevenue.map(m => m.month);
    const data = this.statistics.monthlyRevenue.map(m => m.revenue);

    this.revenueChartOptions = {
      chart: {
        type: 'column',
        backgroundColor: 'transparent',
      },
      title: {
        text: 'Doanh thu 12 tháng gần nhất',
        style: {
          color: '#1e293b',
          fontSize: '18px',
          fontWeight: '600',
        },
      },
      xAxis: {
        categories: categories,
        crosshair: true,
      },
      yAxis: {
        min: 0,
        title: {
          text: 'Doanh thu (VNĐ)',
        },
      },
      tooltip: {
        headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
        pointFormat:
          '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
          '<td style="padding:0"><b>{point.y:,.0f} VNĐ</b></td></tr>',
        footerFormat: '</table>',
        shared: true,
        useHTML: true,
      },
      plotOptions: {
        column: {
          pointPadding: 0.2,
          borderWidth: 0,
          color: '#2563eb',
        },
      },
      series: [
        {
          type: 'column',
          name: 'Doanh thu',
          data: data,
        },
      ],
      credits: {
        enabled: false,
      },
    };
  }

  initGanttChart(): void {
    if (!this.statistics || !this.statistics.activeTourTimelines.length) return;

    const ganttData = this.statistics.activeTourTimelines.map((tour, index) => ({
      name: tour.name,
      start: new Date(tour.startDate).getTime(),
      end: new Date(tour.endDate).getTime(),
      y: index,
    }));

    this.ganttChartOptions = {
      chart: {
        type: 'xrange',
        backgroundColor: 'transparent',
      },
      title: {
        text: 'Lịch trình Tours đang hoạt động',
        style: {
          color: '#1e293b',
          fontSize: '18px',
          fontWeight: '600',
        },
      },
      xAxis: {
        type: 'datetime',
      },
      yAxis: {
        title: {
          text: '',
        },
        categories: this.statistics.activeTourTimelines.map(t => t.name),
        reversed: true,
      },
      tooltip: {
        formatter: function (): string {
          const point = this.point as any;
          const start = new Date(point.x).toLocaleDateString('vi-VN');
          const end = new Date(point.x2).toLocaleDateString('vi-VN');
          return `<b>${point.name}</b><br/>Từ: ${start}<br/>Đến: ${end}`;
        },
      },
      series: [
        {
          type: 'xrange',
          name: 'Tour',
          borderColor: 'transparent',
          pointWidth: 20,
          data: ganttData.map(d => ({
            x: d.start,
            x2: d.end,
            y: d.y,
            name: d.name,
          })),
          dataLabels: {
            enabled: false,
          },
          color: '#10b981',
        },
      ],
      credits: {
        enabled: false,
      },
    };
  }

  translateSeason(season: string): string {
    const translations: { [key: string]: string } = {
      SPRING: 'Mùa Xuân',
      SUMMER: 'Mùa Hè',
      AUTUMN: 'Mùa Thu',
      WINTER: 'Mùa Đông',
    };
    return translations[season] || season;
  }

  formatCurrency(value: number): string {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND',
    }).format(value);
  }
}
