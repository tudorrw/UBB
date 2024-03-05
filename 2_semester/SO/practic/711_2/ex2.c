#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>
int main(int argc, char *argv[]){
    
    float radius, length = -1, area = -1;
    int p2c[2], c2p[2];

    if(pipe(p2c) == -1){
        perror("An error occured with opening the \"parent to child\"pipe\n");
        return 1;
    }

    if(pipe(c2p) == -1){
        perror("An error occured with opening the \"child to parent\"pipe\n");
        return 1;
    }       


    pid_t pid = fork();
    if(pid < 0){
        perror("Fork failed!\n");
        return 1;
    }
    else if(pid == 0){
        // printf("Der Server hat die Informationen erhaltet!\n");
        read(p2c[0], &radius, sizeof(float));
        if(radius > 0.00){
            length = 2 * radius * 3.14;
            area = radius * radius * (314/100);
            printf("Length %f\n", length);
            printf("Area: %f\n", area);
        }
        else{
            printf("Ungultige Eingabe!\n");
        }
        write(c2p[1], &length, sizeof(float));
        write(c2p[1], &area, sizeof(float));
        close(p2c[0]);
        close(p2c[1]);
        close(c2p[0]);
        close(c2p[1]);
        exit(0);
    }
    else{   //
        printf("Radius: ");   //se citeste radius
        scanf("%f", &radius);
        
        write(p2c[1], &radius, sizeof(float));  //trimite prin pipe serverului
        read(c2p[0], &length, sizeof(float));
        read(c2p[0], &area, sizeof(float));
        if(area != -1){
            printf("Empfang vom Server:  Die Länge ist %f und der Flächeninhalt:  %f\n", length, area);
        }
        else{
            printf("Empfang vom Server: Bitte geben Sie einen positiven Wert");
        }
        close(p2c[0]);
        close(p2c[1]);
        close(c2p[0]);
        close(c2p[1]);

        wait(NULL);
    }
    return 0;

}
