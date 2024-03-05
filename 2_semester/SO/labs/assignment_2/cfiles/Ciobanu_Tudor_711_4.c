#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
void primZerlegung(int number){
    int d = 2, n = number;

    while(n > 1){
        int p = 0;
        while(n % d == 0){
            p++;
            n /= d;
        }
        if(p){
            int pid = fork();
            if(pid < 0){
                perror("Fork failed!\n");
                exit(EXIT_FAILURE);
            }
            else if(pid == 0){
                printf("%d ist ein Teiler für %d und der Ordnung der Multiplizität ist %d\n", d, number, p);
                exit(EXIT_SUCCESS);
            }
            else wait(NULL);  //die Eltern Prozess wartet mit wait(NULL) auf die Beendigung des untergeordneten Prozesses
        }
        if(d == 2){
            d++;
        }
        else{
            d += 2;
        }
        if(d * d > n && n > 1){
            d = n;
        }
    }
}

int main() {
    int number;
    printf("Enter the integer: ");
    scanf("%d", &number);
    primZerlegung(number);
    return 0;
}