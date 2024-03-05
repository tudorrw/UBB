#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main(int argc, char *argv[]){
    if(argc != 2){ 
        perror("Error: Insufficient number of arguments.\n");
        return 1;
    }

    int fd[2]; //pipe Kind zu den Eltern 
    if(pipe(fd) == -1){
        perror("An error occured with opening the pipe\n");
        return 1;
    }

    pid_t pid = fork();  

    if(pid < 0){
        perror("Fork failed\n");
        return 1;
    } 
    else if(pid == 0){
        //kinder code
        close(fd[0]); 
        const char* filename = argv[1];
        FILE *file = fopen(filename, "r"); 
        if(file == NULL){
            perror("File not found!\n");
            return 1;
        }

        int lines = 0;
        char chunk[1024];
        while(fgets(chunk, sizeof(chunk), file) != NULL){
            lines++;
        }
        fclose(file);
        write(fd[1], &lines, sizeof(int));
        close(fd[1]);
    }
    else{
        //Eltern code
        close(fd[1]);
        int lines;
        read(fd[0], &lines, sizeof(int));
        close(fd[0]);
        printf("Got from server %d lines in the given file.\n", lines);
        wait(NULL); //wartet auf Kind Prozess um die Anleitung zu beenden
    }
}