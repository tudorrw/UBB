#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>
//ca se se inchida citirea trebie sa se introduca un caracter sau un string, orice inafara de numere
int main(void){
    int s2c[2], c2s[2]; //server to client pipe, client to server pipe
    int note, sum_of_notes = 0, number_of_notes;
    float final_note = 0;

    if(pipe(c2s) == -1){
        perror("An error occured with opening the \"parent to child\"pipe\n");
        return 1;
    }

    if(pipe(s2c) == -1){
        perror("An error occured with opening the \"child to parent\"pipe\n");
        return 1;
    }  

    pid_t pid = fork();
    if(pid < 0){
        perror("Fork failed!\n");
        return 1;
    }
    else if(pid == 0){
        close(s2c[0]);
        close(c2s[1]);
        
        read(c2s[0], &sum_of_notes, sizeof(int));
    
        read(c2s[0], &number_of_notes, sizeof(int));

        if(number_of_notes){
           final_note = sum_of_notes / number_of_notes;
           printf("%f\n", final_note);
        }else{
            printf("Ungultige Eingabe\n");
        }

        write(s2c[1], &final_note, sizeof(float));
        

        close(c2s[0]);
        close(s2c[1]);

        exit(1);

        
    }else{
        close(s2c[1]);
        close(c2s[0]);  

        number_of_notes = 0;
        while(scanf("%d", &note) == 1 && note){
            sum_of_notes += note;
            number_of_notes++;
        } 
        // {
        //     printf("Empfang vom Server: Bitte geben Sie gültige numerische Werte für die Noten ein.\n");
        //     close(c2s[1]);
        //     close(s2c[0]);
        //     wait(NULL);
        //     return 1;
        // }

        write(c2s[1], &sum_of_notes, sizeof(int));
        write(c2s[1], &number_of_notes, sizeof(int));

        read(s2c[0], &final_note, sizeof(float));
        
        if (final_note){
            printf("Empfang vom Server: Der Durchschnitt der Noten beträgt %f\n", final_note);
        }else{
            printf("Empfang vom Server: Bitte geben Sie mindestens eine gültige Note ein.\n");
        }
        
        close(s2c[0]);
        close(c2s[1]);
        wait(NULL);
    }
    return 0;
}