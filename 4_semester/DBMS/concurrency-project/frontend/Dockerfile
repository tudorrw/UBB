FROM node:17-alpine3.15

COPY package.json package-lock.json ./

RUN npm install
COPY . .
RUN npm build

