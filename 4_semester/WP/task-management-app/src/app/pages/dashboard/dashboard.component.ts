import { TaskCreatorPopupComponent } from '../task-creator-popup/task-creator-popup.component';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { Task } from '../../services/model/task';
import { DataTaskService } from '../../services/shared/data-task.service';
import { Observer, flatMap, from, map } from 'rxjs';
import { DataCategoryService } from '../../services/shared/data-category.service';
import { DataPriorityService } from '../../services/shared/data-priority.service';
import { Category } from '../../services/model/category';
import { Priority } from '../../services/model/priority';
import { DocumentReference } from '@angular/fire/compat/firestore';
import { coerceStringArray } from '@angular/cdk/coercion';
import { TaskCardComponent } from '../../task-card/task-card.component';
import { Stage } from '../../services/model/stage';
import { DataStageService } from '../../services/shared/data-stage.service';
import { TaskInMemory } from '../../services/model/task-in-memory';
import { CdkDragDrop } from '@angular/cdk/drag-drop';
import { s } from '@fullcalendar/core/internal-common';
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  async onTaskDrop(event: CdkDragDrop<Task[]>, dropId: string) {
    console.log("dropId", dropId)
    console.log(event.item.dropContainer.id);
    //console.log(event.item.data);
    //console.log(event.previousContainer.data[event.previousIndex])
    //console.log(event.previousContainer)
    console.log(event.item.dropContainer)
    console.log(event.container.id)
    console.log('event', event)
    console.log("Drop container id:", event.container.id);
    console.log("Task id:", event.item.element.nativeElement.id);
    console.log(event.item.element.nativeElement.getAttributeNames())
    console.log(event.item.element.nativeElement)
    const id = event.item.element.nativeElement.getAttribute('id');
    console.log(event.item.element.nativeElement.getAttribute('id'))

    // Define the path to the document you want to update
    const docRef = this.data_task.getTaskById(id || '');

    // Define the data you want to update
    const newData = {
      stage: this.data_stage.getStageByIdRef(event.container.id) as DocumentReference<Stage>
    };
    console.log("stage", event.container.id)
    console.log('dataaaaaaaaaaaaaaaa', newData)
    console.log('ref', newData.stage)
    console.log('ref2', event.item.dropContainer.id)
    console.log("doc", docRef)

    // Call the update method with the document reference and the new data
    await docRef.update(newData)
      .then(() => {
        console.log('Document updated successfully!');
      });

    console.log("result", docRef.get());

    // Get the document snapshot
    const docSnapshot = await docRef.get();

    // Check if the document exists

    // Extract data from the document snapshot
    const data = docSnapshot.data() as Task;

    this.id = data.id
    this.title = data.title;
    this.description = data.description;
    this.priority = data.priority,
      this.category = data.category,
      this.stage = newData.stage,
      this.dueDate = data.dueDate,
      this.userId = localStorage['token'] ?? ''
    console.log(newData.stage)

    // You can also call the addTask function here if needed


    this.data_task.deleteTaskById(id || '');

    this.addTask();



    // Now you may want to update the task in your data source (e.g., database) to reflect the new stage
  }
  hide: boolean = true;
  loginForm!: FormGroup;

  taskList: TaskInMemory[] = [];
  categoriesList: Category[] = [];
  priorityList: Priority[] = [];
  stageList: Stage[] = [];

  taskObj: Task = {
    id: '',
    title: '',
    description: '',
    priority: {} as DocumentReference<Priority>,
    category: {} as DocumentReference<Category>,
    stage: {} as DocumentReference<Stage>,
    dueDate: new Date(""),
    userId: ''
  };
  id: string = '';
  title: string = '';
  description: string = '';
  priority: DocumentReference<Priority> = {} as DocumentReference<Priority>;
  category: DocumentReference<Category> = {} as DocumentReference<Category>;
  dueDate: Date = new Date("");;
  userId: string = '';
  stage: DocumentReference<Stage> = {} as DocumentReference<Stage>;


  tasks = {
    todo: ['Task 1', 'Task 2', 'Task 3'],
    planned: ['Task 4'],
    inProgress: [],
    finished: [],
    verified: []
  };
  navbarCollapsed = false;

  toggleNavbar() {
    this.navbarCollapsed = !this.navbarCollapsed;
  }
  constructor(
    private authService: AuthService,
    public dialog: MatDialog,
    private router: Router,
    private data_task: DataTaskService,
    private data_category: DataCategoryService,
    private data_priority: DataPriorityService,
    private data_stage: DataStageService
  ) {

    this.getAllCategories();
    this.getAllPriorities();
    this.getAllStages();
    //console.log("cat"+this.categoriesList);
    //console.log("prio"+this.priorityList);
  }

  ngOnInit(): void {
    //console.log(this.authService.isLoggedIn());
    if (this.authService.isLoggedIn() == false) {
      this.router.navigate(['/login']);
    }

    this.getAllCategories();
    this.getAllPriorities();
    //console.log("cat"+this.categoriesList);
    //console.log("prio"+this.priorityList);

    this.getAllTasks().subscribe(taskList => {
      this.taskList = taskList;
      console.log(this.taskList);
    });

  }


  logoutUser() {
    this.authService.logout();
  }

  openTaskCreator(): void {
    const dialogRef = this.dialog.open(TaskCreatorPopupComponent, {
      width: '300px', // adjust width as needed
      data: {} // you can pass data to the dialog if needed
    });

    dialogRef.afterClosed().subscribe(result => {
      //console.log(result);
      this.title = result.title;
      this.description = result.description;
      this.priority = this.data_priority.getPriorityByIdRef(this.getPriorityFromName(result.priority ?? '') ?? '') as DocumentReference<Priority>;
      this.category = this.data_category.getCategoryByIdRef(this.getCategoryFromName(result.category ?? '') ?? '') as DocumentReference<Category>;
      this.dueDate = result.dueDate;
      this.userId = localStorage['token'] ?? '';
      this.stage = this.data_stage.getStageByIdRef('1') as DocumentReference<Stage>;
      this.addTask();

      // Call getAllTasks and update this.taskList after the subscription completes
      this.getAllTasks().subscribe(taskList => {
        this.taskList = taskList;
        //console.log(this.taskList);
      });
    });
  }

  getAllTasks() {
    return this.data_task.getAllTasks().pipe(
      flatMap((res: any[]) => {
        return from(Promise.all(res.map(async (e: any) => {
          const data = e.payload.doc.data();
          data.id = e.payload.doc.id;
          console.log("pre", data);
          //console.log("categoru",data.category)
          const categorySnapshot = await data.category.get();
          const categoryData = categorySnapshot.data();
          //console.log("categoryData",categoryData)
          //console.log("categoryData",categoryData.name)

          data.category = categoryData.name;

          const prioritySnapshot = await data.priority.get();
          const priorityData = prioritySnapshot.data();
          data.priority = priorityData.name;
          //console.log("priorityData",priorityData.name)
          const stageSnapshot = await data.stage.get();
          const stageData = stageSnapshot.data();
          data.stage = stageData.name;
          //console.log("stageData",stageData.name)

          data.dueDate = data.dueDate.toDate();
          //console.log("final",data);
          return data;
        })));
      })
    );
  }


  addTask() {
    this.taskObj = {
      id: this.id,
      title: this.title,
      description: this.description,
      priority: this.priority,
      category: this.category,
      dueDate: this.dueDate,
      userId: this.userId,
      stage: this.stage
    };
    console.log("task" + this.taskObj);
    this.data_task.addTask(this.taskObj).then(() => {
      console.log('Task added successfully!');
    });

    this.resetForm();
  }

  resetForm() {
    this.id = '';
    this.title = '';
    this.description = '';
    this.priority = {} as DocumentReference<Priority>;
    this.category = {} as DocumentReference<Category>;
    this.dueDate = new Date("");
    this.userId = '';
  }

  deleteTask(task: Task) {
    if (window.confirm('Are you sure you want to delete this task?')) {
      this.data_task.deleteTask(task).then(() => {
        console.log('Task deleted successfully!');
      });
    }
  }

  updateTask(task: Task) {
    this.data_task.updateTask(task).then(() => {
      console.log('Task updated successfully!');
    });
  }


  getAllCategories() {
    this.data_category.getAllCategories().subscribe(res => {
      this.categoriesList = res.map((e: any) => {
        //console.log(e.payload.doc.data());
        //console.log(this.data_category.getCategoryByIdRef(e.payload.doc.id));
        return {
          id: e.payload.doc.id,
          name: e.payload.doc.data()['name'],
          color: e.payload.doc.data()['color']
        };
      });
    });
  }

  getAllPriorities() {
    this.data_priority.getAllPriorities().subscribe(res => {
      this.priorityList = res.map((e: any) => {
        //console.log(e.payload.doc.data());
        return {
          id: e.payload.doc.id,
          name: e.payload.doc.data()['name'],
          color: e.payload.doc.data()['color']
        };
      });
    });
  }

  getAllStages() {
    this.data_stage.getAllStages().subscribe(res => {
      this.stageList = res.map((e: any) => {
        console.log(e.payload.doc.data());
        return {
          id: e.payload.doc.id,
          name: e.payload.doc.data()['name'],
          color: e.payload.doc.data()['color']
        };
      });
    });
  }

  getCategoryFromName(categoryName: string) {
    const category = this.categoriesList.find(category => category.name.toLowerCase() === categoryName);
    return category ? category.id : null;
  }

  getPriorityFromName(priorityName: string) {
    const priority = this.priorityList.find(priority => priority.name.toLowerCase() === priorityName);
    return priority ? priority.id : null;
  }

  filterTasksByCategory(category: string) {
    return this.taskList.filter(task => task.category === category);
  }

  filterTasksByStage(stage: string) {
    return this.taskList.filter(task => task.stage === stage);
  }

  filterTasksByPriority(priority: string) {
    return this.taskList.filter(task => task.priority === priority);
  }

}