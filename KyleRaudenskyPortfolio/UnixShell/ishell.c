#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>


//This is a simulated unix shell program with limited functionality. This shell can execute any command that calls an exec function, as well as cd, 

#define BUF_SIZE 50


void call_fcn(char **args) {
	pid_t pid;
  	int term;

	if ((pid = fork()) == 0) {
 		// child process
   			int i = execvp(args[0], args);
   			exit(i);
  	}
  	else {
  		wait(&term); // wait for child
  		if (term == 0) {
  			printf("[ishell: program terminated successfully]\n");
  		}
  		else {
  			printf("[ishell: program terminated abnormally][%d]\n", term);
  		}
  	}
}



int main() {

	int comm = 0;
	char tokes[BUF_SIZE];
	char *args[20];

	for (int d = 0; d<20; d++) {
		args[d] = calloc(40, sizeof(char));
	}

	int x = 0;
	int y = 0;
	
	char *newLine = "\n";
	char *exitLine = "exit\n";

	while (1) {

		printf("ishell> ");
		fflush(stdout);
		fgets(tokes, BUF_SIZE+1, stdin);



		y = strncmp(tokes, newLine, 4);
		if (y == 0) {

			if (comm == 0) {
				comm++;
			}

			else {
				args[0] = "ls";
				args[1] = NULL;
    			call_fcn(args);
    			comm = 0;
			}

			continue;
		}

		y = strncmp(tokes, exitLine, 4);

		if (y == 0) {
			exit(0);
		}

		x = 0;
		char buf1[BUF_SIZE] = "";
		char buf2[BUF_SIZE] = "";

		char *token = strtok(tokes, "\n");
		char *token2 = strtok(tokes, ";");

		strcpy(buf1, token2);
		token2 = strtok(NULL, ";");
		if (token2 != NULL) {
			y = 1; strcpy(buf2, token2);
		}

		token = strtok(tokes, " ");
		while (token != NULL) {
  			args[x] = token;
   			token = strtok(NULL, " ");
   			x++;
		}

		args[x] = NULL;

		call_fcn(args);

		if (y == 1) {
			x = 0;
			token = strtok(buf2, " ");
			while (token) {
  				args[x] = token;
 				token = strtok(NULL, " ");
   				x++;
			}
			args[x] = NULL;

			call_fcn(args);
		}
    }
    exit(0);
}
