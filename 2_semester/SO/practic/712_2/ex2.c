#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>

int main(void){
    int s2c[2], c2s[2];
    float invoice, modified_invoice = 0;

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

        if (read(c2s[0], &invoice, sizeof(float)) == -1) {
            perror("Error reading temperature from pipe\n");
            exit(1);
        }
        if(invoice > 0){
            modified_invoice = invoice + invoice / 10;
            printf("Preis mit Servicebebuhr: %f\n", modified_invoice);
        }
        else
            printf("Preis mit Servicebebuhr: Ungultige Eingabe\n");
        write(s2c[1], &modified_invoice, sizeof(float));
    
        close(c2s[0]);
        close(s2c[1]);

        exit(1);

        
    }else{
        close(s2c[1]);
        close(c2s[0]);
        printf("Betrag auf der Rechnung: ");
        if (scanf("%f", &invoice) != 1) {
            printf("Empfanng vom Server: Bitte geben Sie einen gültigen Wert ein.\n");
            close(c2s[1]);
            close(s2c[0]);
            wait(NULL);
            return 1;
        }
        if (write(c2s[1], &invoice, sizeof(float)) == -1) {
            close(c2s[1]);
            close(s2c[0]);
            wait(NULL);
            return 1;
        }
        read(s2c[0], &modified_invoice, sizeof(float));
        if(modified_invoice){
            printf("Empfang vom Server: %f\n", modified_invoice);
        }
        else{
            printf("Empfanng vom Server: Bitte geben Sie einen gültigen Wert ein.\n");
        }

        close(s2c[0]);
        close(c2s[1]);
        wait(NULL);
    }
    return 0;
}