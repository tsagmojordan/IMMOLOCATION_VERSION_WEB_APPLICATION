
function notification(){

    const dateEntreeLocataire = document.getElementById("dateEntree");
    var tempsActuel = new Date().getMinutes();
    var nom=document.getElementById('nomLocataire');
    var a = document.Numero_cni
    var delai=dateEntreeLocataire-tempsActuel;
    alert(nom);
    /*if (delai == 0){

         return alert("jour de payement arrivé");

    }else if (delai>0) {

        return alert("retard de payement de " +delai);

    }else if(delai<0){

        return alert("date de payement dans " +delai);

    } else{

        return alert("une erreur c'est produite pendant le calcul");

    }*/

}