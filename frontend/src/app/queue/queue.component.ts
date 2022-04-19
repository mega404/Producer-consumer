import { Component, Injectable, OnInit } from '@angular/core';
import { fabric } from 'fabric';

@Injectable({
  providedIn : 'root'
})
export class QueueComponent {

  i = 1
  top = 200
  left = 200
  x = 0

  getRandomColor() {
    var letters = '0123456789ABCDEF'.split('');
    var color = '#';
    for (var i = 0; i < 6; i++ ) {
        color += letters[Math.round(Math.random() * 15)];
    }
    return color;
  }
  generate(no_of_elements : number){
    var queue = new fabric.Rect({
      name : 'Q' + this.i,
      width:60 ,
      height:50,
      stroke:"",
      fill: "yellow",  //this.getRandomColor(),
      strokeWidth:1,
      selectable:true,
      scaleX:1,
      scaleY:1,
      lockScalingX : true,
      lockScalingY : true,
      lockRotation : true,
      top: this.top,
      left: this.left,
    })

    var text = new fabric.Text(('Q'+this.i + "\nn : " + no_of_elements),{
        fill : 'black',
        top : this.top,
        left : this.left,
        scaleX : .7,
        scaleY : .5
    })

    var ret_arr = [queue, text]

    this.i += 1;
    if(this.i>9){
      this.x=18
    }
    return ret_arr;
  }

}
