import { DocumentReference } from "@angular/fire/compat/firestore";
import { Category } from "./category";
import { Priority } from "./priority";
import { Stage } from "./stage";

export interface Task {
    id: string,
    title: string,
    description: string,
    priority: DocumentReference<Priority>,
    category: DocumentReference<Category>,
    stage: DocumentReference<Stage>,
    dueDate: Date,
    userId: string
}
