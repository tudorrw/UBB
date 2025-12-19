// layout.component.ts
import { Component } from '@angular/core';

@Component({
  selector: 'app-layout',
  template: `
    <app-top-bar></app-top-bar>
    <div class="content">
      <router-outlet></router-outlet>
    </div>
  `,
  styleUrls: ['./layout.component.scss'],
})
export class LayoutComponent {}
