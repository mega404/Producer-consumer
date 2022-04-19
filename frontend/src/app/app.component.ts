import { Component, OnInit } from '@angular/core';
import { waitForAsync } from '@angular/core/testing';
import { fabric } from 'fabric';
import { delay, last, subscribeOn } from 'rxjs';
import { LineComponent } from './line/line.component';
import { MachineComponent } from './machine/machine.component';
import { QueueComponent } from './queue/queue.component';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { FormsModule } from '@angular/forms';

import 'fabric-history';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'porduce-consumer';


  change_url  = "http://localhost:8080/change";
  map_url  = "http://localhost:8080/map";
  update_url = "http://localhost:8080/update";
  stop_request = "http://localhost:8080/stop"
  replay_request = "http://localhost:8080/replay"
  item_request = "http://localhost:8080/items"
  new_request ="http://localhost:8080/new"


  num_of_objects : any = 0;
  last : any;
  replay1 = false;
  all_shapes : any[] = [];
  all_changes = ["Q1", "M1", "red", "3000"]//, ["M1", "Q2", "red", "0"]]
  change : string = "";
  canvas : any;
  tempCanvas : any;
  index_array  = [0,0,0,0];
  name_arr : any[] = [];
  x :any;
  items : number  = 7 ;
  changes = ""
  Stop : boolean = false;
  first_queue = true;
  whole_map : Map<string, Array<string>> = new Map();
  constructor(
    private queue : QueueComponent,
    private machine : MachineComponent,
    private line : LineComponent,
    private http : HttpClient,
    ){}


  ngOnInit(): void {
    this.canvas = new fabric.Canvas('canvas',{
      selection : true,
      isDrawingMode : false
    })
    this.update();
    //this.update
  }

  update () {
    //type Event = DoneInvokeEvent<any>;
      var eventSource = new EventSource(this.update_url)
      eventSource.onmessage = changes => {
        this.all_changes = changes.data.replaceAll("[\"", '').replaceAll("\"",'').replaceAll("]",'').split(',')
        //alert(this.all_changes)
        this.apply(this.all_changes)
        //if(this.replay1){
          //alert(this.all_changes)
       // }
      }
      eventSource.addEventListener("lastestChanges",  function my(event){
        console.log(event)
      });
  }

  getRandomInt() {
    return Math.floor(Math.random() * 14) + 7;
  }

  generate_queue(){
    var queue
    if (this.first_queue){
      if (this.num_of_objects == 0) {
        this.items = this.getRandomInt()
      }else{
        this.items = parseInt(this.num_of_objects)
      }
       queue = this.queue.generate(this.items)
       this.first_queue = false
    }else{
      queue = this.queue.generate(0) 
    }
    this.canvas.add(queue[0]);
    this.canvas.add(queue[1]);
    //this.x = queue[0]
    var sel = new fabric.ActiveSelection(queue, {
      canvas: this.canvas,
    });
    //console.log(sel)
    //this.canvas.setActiveObject([,queue[1]]);
    this.all_shapes.push([queue[0], queue[1]]);
    console.log(this.all_shapes)
    this.canvas.setActiveObject(sel);
    this.canvas.getActiveObject().toGroup();
    this.last = this.canvas.getActiveObject();
    this.lock()
    this.canvas.requestRenderAll();
  }
  
  generate_machine(){
    var machine = this.machine.generate()
    this.canvas.add(machine[0]);
    this.canvas.add(machine[1]);
    this.x = machine[0]
    this.all_shapes.push(machine[0]);
    console.log(this.all_shapes)
    var sel = new fabric.ActiveSelection(machine, {
      canvas: this.canvas,
    });
    this.canvas.setActiveObject(sel);
    this.canvas.getActiveObject().toGroup();
    this.last = this.canvas.getActiveObject();
    this.lock()
    this.canvas.requestRenderAll();
  }

  lock(){
    this.canvas.getActiveObject().set("lockScalingX", 'true');
    this.canvas.getActiveObject().set("lockScalingY", 'true');
    this.canvas.getActiveObject().set("lockRotation", 'true');
    this.canvas.renderAll();
  }

  // flash(seconds : number, color:string){
  //   //this.canvas.setActiveObject(this.x);
  //   var s = this.canvas.getActiveObject().fill;
  //   //var delayInMilliseconds = 1000; //1 second
  //   this.white(color)
  //   setTimeout(() => {
  //     this.color(s) 
  //   //your code to be executed after 1 second
  //   }, seconds);
  // }

  flash(){
    this.white()
     setTimeout(() => {
       this.color("green") 
     //your code to be executed after 1 second
     }, 200);
  }

  white(){
    this.canvas.getActiveObject().set("fill", 'white');
    this.canvas.renderAll();
  }

  color(s:string){
    this.canvas.getActiveObject().set("fill", s);
    this.canvas.renderAll();
  }
    
  simulate(){
    this.Stop = false
    this.http.post(this.item_request, this.items).subscribe();
    this.http.post(this.map_url, Object.fromEntries(this.whole_map)).subscribe();
  }

  apply(x : string[]){
     if (x[0].substring(0,1) == 'Q'){
      let sh;
      for (let j=0; j<this.all_shapes.length; j++){
        let st
        if (Array.isArray(this.all_shapes[j]) && this.all_shapes[j][0].get("name") !== null) st = this.all_shapes[j][0].get("name")
          console.log(typeof(this.all_shapes[j]))
        if (Array.isArray(this.all_shapes[j]) && x[0] == this.all_shapes[j][0].name){
          console.log("iamhere")
          sh = this.all_shapes[j][1]
          console.log(sh);
          this.canvas.setActiveObject(sh)
          this.process("decrement")
          console.log("not here")
          break
        }
       }
     }
     else if(x[0].substring(0,1) == 'M'){
      let sh;
      //alert()
      for (let j=0; j<this.all_shapes.length; j++){
        if (x[0] == this.all_shapes[j].name){
          sh = this.all_shapes[j]
          this.canvas.setActiveObject(sh)
          //this.flash()
          this.canvas.getActiveObject().set("fill", "green");
          this.canvas.renderAll();
          //this.flash(parseInt(x[3]), x[2] )
          //setTimeout(function(){}, parseInt(x[3]))
        }
      } 
    }
     if (x[1].substring(0,1) == 'Q'){
       let sh; 
       for (let j=0; j<this.all_shapes.length; j++){
         if (Array.isArray(this.all_shapes[j]) && x[1] == this.all_shapes[j][0].get("name")){
           sh = this.all_shapes[j][1]
           this.canvas.setActiveObject(sh)
           console.log("this is sh" + sh)
           this.process("increment")
           break;
         }
       }
     }else{
       console.log("kjdjkcn")
       let sh; 
       for (let j=0; j<this.all_shapes.length; j++){
         if (x[1] == this.all_shapes[j].name){
           sh = this.all_shapes[j]
           this.canvas.setActiveObject(sh)
           this.canvas.getActiveObject().set("fill", x[2]);
           this.canvas.renderAll();
           //this.flash(parseInt(x[3]), x[2] )
           //setTimeout(function(){}, parseInt(x[3]))
         }
       } 
   }
}


  process(state: string){
    let tex_arr = this.canvas.getActiveObject().get("text").split(" ")
    let num = tex_arr[tex_arr.length-1]
    let new_num
    if (state == "decrement") {
      new_num = parseInt(num) - 1
    }else {
      new_num = parseInt(num) + 1
    }
    
    tex_arr[tex_arr.length-1] = new_num
    this.canvas.getActiveObject().set("text",tex_arr.join(" "))
    this.canvas.renderAll() 
  }

  join(){
    if(this.name_arr[0].substring(0,1) != this.name_arr[1].substring(0,1)){
      var angle = Math.atan((this.index_array[3]-this.index_array[1])/(this.index_array[2]-this.index_array[0]))
      console.log(angle*180/Math.PI)
      var length = Math.sqrt(Math.pow(this.index_array[3]-this.index_array[1],2)+Math.pow(this.index_array[2]-this.index_array[0],2))
      console.log(length)
      var line = this.line.generate(angle*180/Math.PI, length,this.index_array[3],this.index_array[2])

      this.canvas.add(line);
      this.last = line;
      this.add_element();
      console.log(this.whole_map);
    }else 
      alert('Error')
    //this.update()
  }

  
  update_data(){
    try{
      this.index_array[0] = this.index_array[2]
      this.index_array[1] = this.index_array[3]
      this.name_arr[0] = this.name_arr[1]
      this.index_array[2] = this.canvas.getActiveObject().left// + this.canvas.getActiveObject().width;
      this.index_array[3] = this.canvas.getActiveObject().top
      this.name_arr[1] = this.canvas.getActiveObject()._objects[0].name
      //this.index_array[5] = this.canvas.getActiveObject().name
    
      console.log(this.index_array)
      //console.log(this.canvas.getActiveObject()._objects[0].name)
    }catch{} 
  }

  add_element(){
    if (this.whole_map.has(this.name_arr[0])) {
      var arr = this.whole_map.get(this.name_arr[0]);
      arr!.push(this.name_arr[1]);
      this.whole_map.set(this.name_arr[0], arr!)
    }else {
      this.whole_map.set(this.name_arr[0], [this.name_arr[1]])
    }
  }

  new(){
    //this.canvas.clear();
    this.http.post(this.new_request, "new").subscribe()
    location.reload()
  }

  reset(){
    let sh;
    for (let j=0; j<this.all_shapes.length; j++){
      let st
      if (Array.isArray(this.all_shapes[j]) && this.all_shapes[j][0].get("name") !== null) st = this.all_shapes[j][0].get("name")
        console.log(typeof(this.all_shapes[j]))
      if ((Array.isArray(this.all_shapes[j]) && "Q1" == this.all_shapes[j][0].name)||
      (Array.isArray(this.all_shapes[j]) && ('Q' + (this.queue.i-1)) == this.all_shapes[j][0].name)){
        //alert(st)
        console.log("iamhere")
        sh = this.all_shapes[j][1]
        console.log(sh);
        this.canvas.setActiveObject(sh)
        if(st == "Q1")
          this.process_reset("first")
        else
          this.process_reset("last")
        console.log("not here")
      }
    }
  }

  process_reset(x : string){
    let tex_arr = this.canvas.getActiveObject().get("text").split(" ")
    let num = tex_arr[tex_arr.length-1]
    
    if(x=="first")
      tex_arr[tex_arr.length-1] = this.items
    else
      tex_arr[tex_arr.length-1] = 0
    this.canvas.getActiveObject().set("text",tex_arr.join(" "))
  }

  replay(){
    this.http.post(this.replay_request, "replay").subscribe()
    //this.m_reset();
    this.reset()
    this.replay1 = true
  }

  stop(){
    this.Stop = true;
    this.http.post(this.stop_request, "stop").subscribe()
  }

  m_reset(){
    let sh;
    for (let j=0; j<this.all_shapes.length; j++){
      if (this.all_shapes[j].name.substring(0,1) == 'M'){
        sh = this.all_shapes[j]
        this.canvas.setActiveObject(sh)
        //this.flash()
        this.canvas.getActiveObject().set("fill", "green");
        this.canvas.renderAll();
        //this.flash(parseInt(x[3]), x[2] )
        //setTimeout(function(){}, parseInt(x[3]))
      }
    } 
  }

}


/*else{
          let sh; 
          for (let j=0; j<this.all_shapes.length; j++){
            if (this.all_changes[i][0] == this.all_shapes[j].name){
              sh = this.all_shapes[j]
            }
          }
          this.canvas.setActiveObject(sh)
          this.flash(parseInt(this.all_changes[i][3]), this.all_changes[i][2] )
        }*/
