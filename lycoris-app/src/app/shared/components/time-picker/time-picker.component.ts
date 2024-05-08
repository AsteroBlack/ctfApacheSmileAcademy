import {Component, EventEmitter, OnInit, Output, Input, OnChanges, SimpleChanges} from '@angular/core';
import { element } from 'protractor';

@Component({
  selector: 'app-time-picker',
  templateUrl: './time-picker.component.html',
  styleUrls: ['./time-picker.component.css']
})
export class TimePickerComponent implements OnInit/*, OnChanges*/ {
  @Output() getHour = new EventEmitter();
  @Input() enableHours:any[]=[]
  //@Output() enableHoursChange = new EventEmitter();
  // private possibleValues: Array<any> = ['0', 1, 2, 3, 4, 5, 6, 7, 8, 9, '', ''];
  // private matrix: number[][];
  hourSelected:any={}
  dayHours:any[]=[]
  constructor() { }

  ngOnInit() {
    //this.generateMatrix(3, 4);
    this.initializeDayHours()
    //this.checkHoursToDisable()
  }

  // ngOnChanges(change: SimpleChanges){
  //   //console.log("change event", change);
  //   if(change.enableHours.currentValue&&change.enableHours.currentValue.length){
  //     this.checkHoursToDisable()
  //   }
  // }
  initializeDayHours(){
    // for (let index = 7; index < 20; index++) {
    //   if(index<10) this.dayHours.push({"hour":"0"+index.toString(),"selected":false,"disabled":false})
    //   else this.dayHours.push({"hour":index.toString(),"selected":false,"disabled":false})
    // }
    this.enableHours.forEach(enableHour => {
      this.dayHours.push({
        hour:enableHour,
        selected:false,
        disabled:false
      })
    });
  }
  setDisablingForAllHours(anyArray:any[]){
    anyArray.forEach(element=>{
      element.disabled=true
    })
  }
  // checkHoursToDisable(){
  //   if(this.enableHours&&this.enableHours.length){
  //   this.setDisablingForAllHours(this.dayHours)
  //   for (let i = 0; i < this.enableHours.length; i++) {
  //     for (let j = 0; j < this.dayHours.length; j++) {
  //       if(this.enableHours[i]===this.dayHours[j].hour) this.dayHours[j].disabled=false
  //       //else this.dayHours[j].disabled=false
  //     }
  //   }
  // }
  // else for (let j = 0; j < this.dayHours.length; j++) {
  //   this.dayHours[j].disabled=true
  // }
  // }

  // shuffleArray(arr) {
  //   for (let i = arr.length-1; i >= 0; i--) {
  //     let randomIndex = Math.floor(Math.random()*(i+1));
  //     let itemAtIndex = arr[randomIndex];

  //     arr[randomIndex] = arr[i];
  //     arr[i] = itemAtIndex;
  //   }
  //   return arr;
  // }

  // generateMatrix(row, col) {
  //   this.matrix = [];
  //   let values = this.shuffleArray(this.possibleValues);
  //   for(let i = 0; i < row; i++) {
  //     this.matrix[i] = [];
  //     for(let j = 0; j < col; j++) {
  //       this.matrix[i][j] = values[i*col+j];
  //     }
  //   }
  // }

  // selectValue(val) {
  //   if(val) {
  //     console.log(val);
  //     this.clickOnValue.emit(val);
  //   }
  // }

  // errase() {
  //   this.clickOnValue.emit('#');
  // }
  selectHour(index) {
    for(let i = 0; i < this.dayHours.length; i++){
      if (this.hourSelected.hour == this.dayHours[index].hour){
        if(this.dayHours[index].hour==this.dayHours[i].hour) continue;
        else this.dayHours[i]["selected"] = false;
      }
      else this.dayHours[i]["selected"] = false;
    };
    if(this.hourSelected.hour==this.dayHours[index].hour)this.dayHours[index]["selected"]=!this.dayHours[index]["selected"]
    else this.dayHours[index]["selected"] = true
    this.hourSelected = this.dayHours[index];
    if(this.hourSelected&&!this.hourSelected.disabled) this.getHour.emit(this.hourSelected.hour)
    else this.getHour.emit('')
    
  }
}
