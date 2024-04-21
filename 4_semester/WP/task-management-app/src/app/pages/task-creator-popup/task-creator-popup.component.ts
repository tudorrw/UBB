import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-task-popup',
  templateUrl: './task-creator-popup.component.html',
  styleUrls: ['./task-creator-popup.component.css']
})
export class TaskCreatorPopupComponent implements OnInit {
  taskForm!: FormGroup;
  selectedCategory: string = ''; // Property to store the currently selected category
  selectedPriority: string = ''; // Property to store the currently selected priority
  priorities: string[] = ['Low', 'Medium', 'High', 'Critical']; // Array of priority options
  categories: string[] = ['Personal', 'Work', 'Study', 'Home']; // Array of category options
  constructor(
    public dialogRef: MatDialogRef<TaskCreatorPopupComponent>,
    private formBuilder: FormBuilder
  ) {
    this.taskForm = this.formBuilder.group({
      title: ['', Validators.required],
      description: [''],
      priority: ['', Validators.required], // Adding validators for priority
      category: ['', Validators.required], // Adding validators for priority
      dueDate: ['']
    });
  }


  ngOnInit(): void {
    this.taskForm = this.formBuilder.group({
      title: ['', Validators.required],
      description: [''],
      priority: ['', Validators.required], // Adding validators for priority
      category: ['', Validators.required], // Adding validators for priority
      dueDate: ['']
    });

    // Listen to changes in the category form control
    this.taskForm.get('category')?.valueChanges.subscribe((category: string) => {
      console.log(category);
      this.selectedCategory = category; // Update the selected category
    });

    // Listen to changes in the priority form control
    this.taskForm.get('priority')?.valueChanges.subscribe((priority: string) => {
      console.log(priority);
      this.selectedPriority = priority; // Update the selected priority
    });
  }

  saveTask(): void {
    if (this.taskForm.valid) {
      const taskData = this.taskForm.value;
      this.dialogRef.close(taskData);
    } else {
      // Form is invalid, mark all fields as touched to display validation errors
      this.markFormGroupTouched(this.taskForm);
    }
  }

  closeDialog(): void {
    this.dialogRef.close();
  }

  // Helper function to mark all form controls as touched
  private markFormGroupTouched(formGroup: FormGroup) {
    Object.values(formGroup.controls).forEach(control => {
      control.markAsTouched();

      if (control instanceof FormGroup) {
        this.markFormGroupTouched(control);
      }
    });
  }
}
