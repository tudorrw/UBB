import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { TaskCreatorPopupComponent } from './pages/task-creator-popup/task-creator-popup.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'task-management-app';
  my_text: string = "I'm in love with";


  changeText(): void {
    this.my_text = "Patricia";
  }
}