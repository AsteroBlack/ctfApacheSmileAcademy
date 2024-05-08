import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class UtilitiesService {

  constructor(private snackBar: MatSnackBar) { }

  //Format millier
  getFormatMillierMontant(montant:any) {
    if (!montant || !parseFloat(montant)) return;

    console.log("le montant");
    try {
      try {
        montant = montant.replace(/ /g, '');
        montant = montant.replace(",", ".");
      } catch (error) {

      }

      var nombre = montant;
      nombre += '';
      var sep = ' ';
      var reg = /(\d+)(\d{3})/;

      while (reg.test(nombre)) {
        nombre = nombre.replace(reg, '$1' + sep + '$2');
      }
    } catch (error) {

    }

    return nombre;
  }


  showNotification(colorName:any, text:any, placementFrom:any, placementAlign:any) {
   let snackBarRef= this.snackBar.open(text, "", {
      duration: 10000,
      verticalPosition: 'top',
      horizontalPosition: 'right',
      panelClass: colorName,
    });

    return snackBarRef

  }

  showRequestErrorNotification(error, request: string) {
    console.error('Request generated Error', request)
    console.error('Request Error', error)
    this.showNotification(
      'snackbar-danger',
      error,
      'bottom',
      'center'
    )
  }

  showRequestSuccessNotification(message: string) {
    this.showNotification(
      'snackbar-success',
      message,
      'bottom',
      'center'
    )
  }

  formatMsgServeur(msg :any) {
    var messageError = msg;

    if (msg == 'Operation effectuee avec succes' || msg == 'Operation effectuee avec succes:') {
      msg = 'Opération effectuée avec succès.';
      return msg;
    }else if(msg == 'Champ non renseigne: motif'){
      messageError= 'Veuillez renseigner le motif'
      return messageError
    }

    if (msg) {
      messageError = messageError.replace("Operation effectuee avec succes:", "Opération effectuée avec succès.");
      messageError = messageError.replace("Impossible de se connecter a la base de donnees:", "");
      messageError = messageError.replace("La base de donnees est indisponible:", "");
      messageError = messageError.replace("Permission refusee par la base de donnees:", "");
      messageError = messageError.replace("Le serveur de base de donnees a refuse la requete:", "");
      messageError = messageError.replace("Authentification echouee:", "");
      messageError = messageError.replace("Donnee existante:", "");
      messageError = messageError.replace("Liste vide : il n'y a pas de donnees respectant ce critere:", "");
      messageError = messageError.replace("il n'y a pas de donnees respectant ce critere", "");
      messageError = messageError.replace("Champ non renseigne:", "");
      messageError = messageError.replace("Utilisateur deja connecte:", "");
      messageError = messageError.replace("la requete attendue n'est pas celle fournie:", "");
      messageError = messageError.replace("Le type est incorrect:", "");
      messageError = messageError.replace("Le format de la date est incorrect:", "");
      messageError = messageError.replace("le serveur a signale un format invalide:", "");
      messageError = messageError.replace("le code de la langue n'est pas valide:", "");
      messageError = messageError.replace("La periode de date n'est pas valide", "");
      messageError = messageError.replace("une erreur est survenue lors de l'enregistrement:", "");
      messageError = messageError.replace("le name de l'entite n'est pas valide:", "");
      messageError = messageError.replace("Veuillez renseigner une seule valeur pour cette donnee:", "");
      messageError = messageError.replace("La somme des pourcentages ne doit exceder 100:", "");
      messageError = messageError.replace("Erreur de generation de fichier:", "");
      messageError = messageError.replace("login et/ou mot de passe incorrect(s):", "");
      messageError = messageError.replace("Operation interdite/refusee:", "");
      messageError = messageError.replace("Ccette donnees ne peut etre supprimee car elle est utilisee:", "");
      messageError = messageError.replace("cette donnees est trop superieure:", "");
      messageError = messageError.replace("Vous n'etes pas autoriser a effectuer cette operation.", "");
      messageError = messageError.replace("Donnee inexistante:", "");
      messageError = messageError.replace("Erreur interne:", "");
      messageError = messageError.replace("Donnee(s) non trouvee(s):", "");
      messageError = messageError.replace("Le serveur de base de donnees a refuse la requete:", "");
      messageError = messageError.replace("cette donnees ne peut etre supprimee car elle est utilisee:", "");
      messageError = messageError.replace("Vous n'etes pas autoriser a effectuer cette operation.", "");
      messageError = messageError.replace("Champ non renseigne: motif", "Veuillez renseigner le motif");
    }

    return messageError;
  };

  getExtensionsImage() {
    return [
      { extension: 'png' },
      { extension: 'jpeg' },
      { extension: 'jpg' }
    ];
  }

  getMessageEndPointNotAvailble() {
    return "Connexion momentanément interrompue, veuillez réessayer ultérieurement !";
  }

}
