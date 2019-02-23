#include <stdio.h>
#include <sys/stat.h>
#include <dirent.h>
#include <cstring>
#include <errno.h>
#include <stdlib.h>
#include <climits>

struct stat buffer;
int status;

long readSize(char* filePath) {
    long size = 0;

    status = lstat(filePath, &buffer);
    if (status == -1) {
        printf("Error %s %s\n", filePath, strerror(errno));
        return 0;
    }
    if (S_ISDIR(buffer.st_mode)) {
        DIR *d;
        struct dirent *dir;
        d = opendir(filePath);
        if (d) {
            while ((dir = readdir(d)) != NULL) {
                if (strcmp(dir->d_name, ".") != 0 && strcmp(dir->d_name, "..") != 0) {
                    char* newPath =(char *) malloc(strlen(filePath) + strlen(dir->d_name) + 2);

                    strcpy(newPath, filePath);
                    strcat(newPath, "/");
                    strcat(newPath, dir->d_name);

                    size += readSize(newPath);
                }
            }
            closedir(d);
        }
    } else if (S_ISREG(buffer.st_mode)) {
        size += buffer.st_size;
        return size;
    } else {
        return 0;
    }
    return size;
}

int main(int argc, char* argv[]) {

    char* filePath;
    char actualpath [PATH_MAX+1];

    if (argc == 1) {
        filePath = ".";
        printf("%s %ld\n", realpath(filePath, actualpath), readSize(filePath));
    } else {
        for (int i = 1; i < argc; i++) {
            filePath = argv[i];
            printf("%s %ld\n", realpath(filePath, actualpath), readSize(filePath));
        }
    }

    return 0;
}