startup
we convert the npm commands to yarn

npm install -g @angular/cli
is replaced with
yarn global add @angular/cli

export PATH="$(yarn global bin):$PATH" 

ng new forms
cd forms
ng generate component home

