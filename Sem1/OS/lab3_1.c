#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <memory.h>

void terminalCommand() {

    char   result[100];
    FILE*  pipe_writer;

    if(( pipe_writer=popen("ifconfig", "w")) == NULL) {
        printf(stderr, "Error open pipe");
        exit(1);
    }

    while (fgets(result, 100, pipe_writer) != NULL) {
        printf("%s", result);
    }

    pclose(pipe_writer);
}

char* createLetterCommand(const char* contact) {

    char* template = "echo \"You got 100 on OS exam!\" | mail -s \"Congratulations\" ";
    char* l = malloc(sizeof(template) + sizeof(contact) + 10);

    strcpy(l, template);
    strcat(l, contact);

    printf("Command to run: %s\n", l);
    return l;
}

void sendMail(int argc, char** argv) {

    FILE*  pipe_writer;

    if (argc < 2) {
        printf("%s\n", "I need some contacts to send mail");
        exit(1);
    }

    for (int i = 1; i < argc; i++) {

        if (pipe_writer = popen(createLetterCommand(argv[i]), "w") == NULL) {
            printf("%s %s", "Error open pipe and send mail to ", argv[i]);
            exit(1);
        }
    }
    pclose(pipe_writer);
}

int main(int argc, char** argv) {
    terminalCommand();
    sendMail(argc, argv);
    return 0;
}