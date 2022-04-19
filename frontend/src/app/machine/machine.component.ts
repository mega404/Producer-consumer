import { Component, Injectable, OnInit } from '@angular/core';
import { fabric } from 'fabric';

@Injectable({
  providedIn : 'root'
})
export class MachineComponent {

  
i = 1;
top = 200;
left = 200;
x = 0
y = 5
  generate(){
    var machine = new fabric.Circle({
      name : 'M' + this.i,
      radius:30+this.x,
      stroke:"",
      fill:"green",
      strokeWidth:1,
      selectable:true,
      scaleX:1,
      scaleY:1,
      top:this.top,
      left :this.left
    })

    var text = new fabric.Text(('M'+this.i),{
      fill : 'black',
      top : this.top+this.y,
      left : this.left+2
  })

  var ret_arr = [machine, text]

  this.i += 1;
  if(this.i>9){
    this.x=12
    this.y = 15
  }
    return ret_arr;
  }

}
