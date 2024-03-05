#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <dirent.h>

#include <sys/stat.h>
#include <sys/types.h>
#include <errno.h>
#include <string.h>
#include <fcntl.h>

int main(int argc, char *argv[]){
    
    if(argc != 2){
        perror("Error: Invalid number of argumnerts!\n");
        exit(EXIT_FAILURE);
    }
    
    if(mkfifo("myfifo", S_IRUSR | S_IWUSR) == -1){
        if(errno != EEXIST){
            printf("Could not create fifo file!\n");
            exit(EXIT_FAILURE);
        }
    }

    int w_fifo, r_fifo;
    char buffer[] = "Unable to read directory!\n";
    char buf[1024];

    pid_t pid = fork();
    if(pid < 0){
        perror("Fork failed\n");
        exit(EXIT_FAILURE);
    } 
    else if(pid == 0){
        printf("child process\n");

        if ((w_fifo = open("myfifo", O_WRONLY)) < 0){
            perror("error opening fifo in child process\n");
            exit(EXIT_FAILURE);
        }
        DIR *folder;
        struct dirent *entry;

        folder = opendir(argv[1]);
        if(folder == NULL){
            write(w_fifo, buffer, strlen(buffer));
            close(w_fifo);
            exit(EXIT_SUCCESS);
        }
        while(( entry = readdir(folder)) ){
            char *extension = strrchr(entry->d_name, '.');
            if(extension != NULL){
                if (strcmp(extension, ".txt") == 0){
                    write(w_fifo, entry->d_name, strlen(entry->d_name));
                    write(w_fifo, "\n");
                }
            }
            
        }
        closedir(folder);
        close(w_fifo);
        printf("closing child process\n");
        exit(EXIT_SUCCESS);
        
    }
    else{
        if((r_fifo = open("myfifo", O_RDONLY)) < 0){
            perror("error opening fifo in parent process");
            exit(EXIT_FAILURE);
        }

        while(wait(NULL) != pid);

        int num_bytes = read(r_fifo, buf, sizeof(buf) - 1);
        buf[num_bytes] = '\0';

        printf("Parent process\n");
        printf("%s", buf);

        close(r_fifo);
    }
    printf("finish\n");
    return 0;
}