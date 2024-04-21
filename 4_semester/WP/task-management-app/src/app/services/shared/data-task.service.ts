import { Injectable } from '@angular/core';
import { AngularFirestore } from '@angular/fire/compat/firestore';
import { Task } from '../model/task';

@Injectable({
  providedIn: 'root'
})
export class DataTaskService {

  constructor(private afs: AngularFirestore) { }

  addTask(task: Task) {
    task.id = this.afs.createId();
    console.log(task);
    return this.afs.collection('/Tasks').doc(task.id).set(task);
  }


  getAllTasks() {
    //return this.afs.collection('/Tasks').snapshotChanges();
    return this.afs.collection('/Tasks', ref => ref.where('userId', '==', localStorage['token'])).snapshotChanges();
  }

  updateTask(task: Task) {
    return this.afs.collection('/Tasks').doc(task.id).update(task);
  }

  getTaskById(id: string) {
    return this.afs.collection('/Tasks').doc(id).ref;
  }

  deleteTask(task: Task) {
    return this.afs.collection('/Tasks').doc(task.id).delete();
  }

  deleteTaskById(id: string) {
    return this.afs.collection('/Tasks').doc(id).delete();
  }




}
