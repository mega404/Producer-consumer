import { Component, Injectable, OnInit } from '@angular/core';
import { fabric } from 'fabric';
import { Line } from 'fabric/fabric-impl';

@Injectable({
  providedIn : 'root'
})
export class LineComponent {

  
top = 200;
left = 200;

  generate(angle : any, length : any,top :any,left :any){
    var line = new fabric.Line( [0,0,length-55,0] ,{
      //                            ,right_vertical

      stroke:"black",
      fill:"green",
      strokeWidth:1,
      selectable:true,
      scaleX:1,
      scaleY:1,
      //angle:angle,
      top:top+30,
      left :left+60
    })
    console.log(line)
    var triangle = new fabric.Triangle({
      width: 10, 
      height: 15, 
      fill: 'black', 
      left: left+57, 
      top: top+36,
      angle: -90 
  });
    var objs = [line, triangle];
    var arrow = new fabric.Group(objs);
        //canvas.add(alltogetherObj);
    arrow.set("angle", angle)
    return arrow;
  }
  

}
