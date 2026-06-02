FROM ubuntu:latest
LABEL authors="prvm"

ENTRYPOINT ["top", "-b"]