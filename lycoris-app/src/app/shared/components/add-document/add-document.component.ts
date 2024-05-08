import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges } from '@angular/core';
import { UtilitiesService } from 'src/app/core/service/utilities.service';
import * as _ from "lodash";

@Component({
  selector: 'app-add-document',
  templateUrl: './add-document.component.html',
  styleUrls: ['./add-document.component.scss']
})
export class AddDocumentComponent implements OnInit {

  imageDisplay : any = {};
  currentItemImage : any = {};
  itemImageATraiter : any = {};
  ListeImagesUploader : Array<any> = [];

  constructor(private utilities: UtilitiesService) { }
  
  @Output() addDocument: EventEmitter<any> = new EventEmitter();
  @Input() isRefresh?: any;

  sendDocumentMainPage() {
    let listTToEmit: any[] = [];

    this.ListeImagesUploader.forEach(element => {
       let  itemImg = {...element};
       itemImg.fullBase64 = null;
      //  itemImg.fileName = null;

       listTToEmit.push(itemImg);

    });
    this.addDocument.emit(listTToEmit); 
  }

  uploadFile(event: any) {
    this.itemImageATraiter.messageError = null;
    let item = {
      fileName: null,
      fileBase64: null
    };
    let reader = new FileReader();

    let file = event.target.files[0];

    if (file.size > 1000000) {
      this.itemImageATraiter.messageError = "Veuillez vérifier l'extension du fichier.";
      event.target.value = null;
      return;
    }

    item.fileName = file.name;
    let Tabextension = file.name.split('.');
    let extension = Tabextension[Tabextension.length - 1];

    //verifier si l'extension est accepter
    const listeExtensionImagesValide = this.utilities.getExtensionsImage();

    if (!_.find(listeExtensionImagesValide, { 'extension': extension.toLowerCase() })) {
        this.itemImageATraiter.messageError = "Veuillez vérifier l'extension du fichier.";
        event.target.value = null;
        return;
    }

    reader.onload = (readerEvent) => {

      let data = (readerEvent.target as any).result;
    
      item.fileBase64 = data.split(',')[1];
      this.currentItemImage = {
        fileBase64: item.fileBase64,
        nomFichier: item.fileName,
        extension: extension,
        fullBase64 : data,
        fileName:item.fileName,
        typeDocument:extension?.toLowerCase() == "pdf" ? "PDF" : "IMG"
      };

      this.ListeImagesUploader.push(this.currentItemImage);
      this.sendDocumentMainPage();
    };

    reader.readAsDataURL(event.target.files[0]);
  }

  gotoUploadFile()
  {
    let inputFile = document.getElementById("id-joindre-file");
    if(inputFile)
    {
      inputFile.click();
    }
  }

  deleteImg(indice :any)
  {
    this.ListeImagesUploader.splice(indice,1);
    this.sendDocumentMainPage();
  }

  ngOnChanges(changes: SimpleChanges) {
    if(changes['isRefresh'] && changes['isRefresh'].currentValue)
    {
      this.ListeImagesUploader = [];
    }
  }
  
  openFile(file :any){
    
  }

  ngOnInit() {
  }


}
