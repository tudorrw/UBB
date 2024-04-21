import { Component, Inject } from '@angular/core';
import { EventApi } from '@fullcalendar/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { TaskInMemory } from '../services/model/task-in-memory';


@Component({
  selector: 'app-task-popup',
  templateUrl: './task-popup.component.html',
  styleUrl: './task-popup.component.css'
})

export class TaskPopupComponent {
  constructor(
    public dialogRef: MatDialogRef<TaskPopupComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { event: EventApi, task: TaskInMemory }
  ) { }
  onClose(): void {
    this.dialogRef.close();
  }
}
