import { Injectable } from '@angular/core';
import { AngularFirestore } from '@angular/fire/compat/firestore';

@Injectable({
  providedIn: 'root'
})
export class DataPriorityService {

  constructor(private afs: AngularFirestore) { }

  getAllPriorities() {
    return this.afs.collection('/Priorities').snapshotChanges();
  }

  getPriorityById(id: string) {
    return this.afs.collection('/Priorities').doc(id).snapshotChanges();
  }

  getPriorityByIdRef(id: string) {
    return this.afs.collection('/Priorities').doc(id).ref;
  }
}
