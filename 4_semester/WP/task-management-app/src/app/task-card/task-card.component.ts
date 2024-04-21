import { Component, Input, OnInit } from '@angular/core';
import { Task } from '../services/model/task';
import { DataCategoryService } from '../services/shared/data-category.service';
import { DataPriorityService } from '../services/shared/data-priority.service';
import { TaskInMemory } from '../services/model/task-in-memory';
import { CdkDragDrop } from '@angular/cdk/drag-drop';
import { DataTaskService } from '../services/shared/data-task.service';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';
import { MatDialog } from '@angular/material/dialog';
@Component({
  selector: 'app-task-card',
  templateUrl: './task-card.component.html',
  styleUrl: './task-card.component.css'
})

export class TaskCardComponent {

  deleteTask(taskId: string) {
    const dialogRef = this.dialog.open(ConfirmationDialogComponent);

    dialogRef.afterClosed().subscribe((result: boolean) => {
      console.log('The dialog was closed', result);
      if (result === true) {
        console.log('Confirmed', taskId);
        console.log(taskId);
        this.data_task.deleteTaskById(taskId); // Delete task if confirmed
      } else {
        console.log("Deletion canceled."); // Handle cancelation
      }
    });

  }


  @Input() task: TaskInMemory | null = null;

  constructor(private data_task: DataTaskService, private dialog: MatDialog) { }

  get isTaskInitialized(): boolean {
    console.log("task", this.task)
    return this.task !== null;
  }
}


/* export class TaskCardComponent implements OnInit {
  @Input() task: Task= {} as Task;
  priorityName: string = '';
  categoryName: string = '';

  constructor(private categoryService: DataCategoryService, private priorityService: DataPriorityService) {}

  async ngOnInit(): Promise<void> {
    console.log("card task", this.task);
    //await this.fetchPriorityName();
    //await this.fetchCategoryName();
  } */

/*  private fetchPriorityName(): void {
   console.log("card task"+this.task)
   this.priorityService.getPriorityById(this.task.priority.id).subscribe((priority: any) => {
     console.log("cardddddddddd"+priority)
     console.log("cardddddddddd"+priority.name)
     console.log("cardddddddddd"+this.task.priority.id)

     this.priorityName = priority.name;
   });
 }

 private fetchCategoryName(): void {
   this.categoryService.getCategoryById(this.task.category.id).subscribe((category: any) => {
     this.categoryName = category.name;
   });
 } */
//}
