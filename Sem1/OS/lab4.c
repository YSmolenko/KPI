#include <stdio.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include <netdb.h>
#include <string.h>
#include <unistd.h>

#define PAGE "/"
#define PORT 80

int createTcpSocket() {
    int sock;
    if ((sock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0){
        printf("Can't create TCP socket.");
        exit(1);
    }
    return sock;
}


char *getIp(char *host) {
    struct hostent *hent;
    int iplen = 15;
    char *ip = (char *)malloc(iplen + 1);

    if((hent = gethostbyname(host)) == NULL) {
        herror("Can't get IP");
        exit(1);
    }

    if(inet_ntop(AF_INET, (void *)hent -> h_addr_list[0], ip, iplen) == NULL) {
        perror("Can't resolve host");
        exit(1);
    }
    return ip;
}

char *buildGetQuery(char *host, char *page) {
    char *query;
    char *getpage = page;
    char *tpl = "GET /%s HTTP/1.0\r\nHost: %s\r\nUser-Agent: %s\r\n\r\n";
    if(getpage[0] == '/') {
        getpage = getpage + 1;
        printf(stderr,"Removing leading \"/\", converting %s to %s\n");
    }
    query = (char *)malloc(strlen(host) + strlen(getpage) + strlen(tpl) - 5);
    sprintf(query, tpl, getpage, host);
    return query;
}

int main(int argc, char** argv) {
    struct sockaddr_in* remote;
    int sock, tmpres, port;
    char *ip, *host, *page, *get;
    char buf[BUFSIZ + 1];

    if(argc < 1) {
        printf("%s", "No arguments");
        exit(1);
    }

    host = argv[1];
    port = PORT;
    if(argc > 2) {
        page = argv[2];
        if(argv[3]) {
            port = atoi(argv[3]);
        }
    } else {
        page = PAGE;
    }

    sock = createTcpSocket();
    ip = getIp(host);
    remote = (struct sockaddr_in *)malloc(sizeof(struct sockaddr_in *));

    remote->sin_family = AF_INET;

    tmpres = inet_pton(AF_INET, ip, (void *)(&(remote -> sin_addr.s_addr)));

    if( tmpres < 0) {
        perror("Can't set remote -> sin_addr.s_addr");
        exit(1);
    } else if (tmpres == 0) {
        fprintf(stderr, "%s is not a valid IP address\n", ip);
        exit(1);
    }
    remote -> sin_port = htons(port);

    if(connect(sock, (struct sockaddr *)remote, sizeof(struct sockaddr)) < 0){
        perror("Could not connect");
        exit(1);
    }
    get = buildGetQuery(host, page);
    printf("The query is:\n%s\n", get);

    int sent = 0;
    while(sent < strlen(get)) {
        tmpres = send(sock, get + sent, strlen(get)-sent, 0);
        if(tmpres == -1) {
            perror("Can't send query");
            exit(1);
        }
        sent += tmpres;
    }

    memset(buf, 0, sizeof(buf));
    while((tmpres = recv(sock, buf, BUFSIZ, 0)) > 0) {
        printf("%s", buf);
    }
    if(tmpres < 0) {
        perror("Error receiving data");
    }
    free(get);
    free(remote);
    free(ip);
    close(sock);

    return 0;
}