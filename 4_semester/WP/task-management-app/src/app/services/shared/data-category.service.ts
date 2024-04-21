import { Injectable } from '@angular/core';
import { AngularFirestore } from '@angular/fire/compat/firestore';

@Injectable({
  providedIn: 'root'
})
export class DataCategoryService {

  constructor(private afs: AngularFirestore) { }

  getAllCategories() {
    return this.afs.collection('/Categories').snapshotChanges();
  }

  getCategoryById(id: string) {
    return this.afs.collection('/Categories').doc(id).snapshotChanges();
  }

  getCategoryByIdRef(id: string) {
    return this.afs.collection('/Categories').doc(id).ref;
  }
}
