import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppComponent } from './app.component';
import { MachineComponent } from './machine/machine.component';
import { QueueComponent } from './queue/queue.component';
import { LineComponent } from './line/line.component';
import { HttpClientModule } from '@angular/common/http';

import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    MachineComponent,
    QueueComponent,
    LineComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
