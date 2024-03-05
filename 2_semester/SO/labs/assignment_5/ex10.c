#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>

int main(int argc, char *argv[]){
    if(argc != 2){
        perror("invalid number of arguments!\n");
        return 1;
    }
    int fd = open(argv[1], O_RDONLY);
    if (fd == -1) {
        perror("Error opening the file");
        return 1;
    }
    char buff[256];
    read(fd, buff, 9);
    buff[9] = '\0';
    printf("%s", buff);
    return 0;
}