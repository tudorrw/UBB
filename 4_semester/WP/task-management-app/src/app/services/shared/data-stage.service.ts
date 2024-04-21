import { Injectable } from '@angular/core';
import { AngularFirestore } from '@angular/fire/compat/firestore';

@Injectable({
  providedIn: 'root'
})
export class DataStageService {

  constructor(private afs: AngularFirestore) { }

  getAllStages() {
    return this.afs.collection('/Stage').snapshotChanges();
  }

  getStageById(id: string) {
    return this.afs.collection('/Stage').doc(id).snapshotChanges();
  }

  getStageByIdRef(id: string) {
    return this.afs.collection('/Stage').doc(id).ref;
  }
}
