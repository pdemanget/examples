import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { DemoComponent } from './demo/demo.component';
import { Routes, RouterModule } from '@angular/router';


const appRoutes: Routes = [

  { path: 'demo', component: DemoComponent },

  { path: '', component: HomeComponent }

];
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    DemoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
