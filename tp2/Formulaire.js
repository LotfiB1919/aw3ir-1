

function ValiderFormulaire()
{

   var nom = document.getElementById("nom").value;
   var prenom = document.getElementById("prenom").value;
   var email = document.getElementById("email").value;
   var adresse = document.getElementById("adresse").value;
   var message;
 
   
   

    if((nom.length < 5) || (nom.length > 20 )) {
     
      if(nom.length =0){
         message ="La saisie du nom est obligatoire";
   
      }else{
        message ="La taille du nom doit etre entre 5 et 20 caractères";
      }

      document.getElementById("bodyModel").style.backgroundColor ="red"; 
      document.getElementById("bodyModel").innerHTML = message;
      document.getElementById("messageSection").style.display="block";

      
      
       
    }else if((prenom.length < 5) || (prenom.length > 20 )){
        if(nom.length =0){
           message ="La saisie du nom est obligatoire";
     
        }else{
        message ="La taille du nom doit etre entre 5 et 20 caractères";
        }
        document.getElementById("bodyModel").style.backgroundColor ="red"; 
       document.getElementById("bodyModel").innerHTML = message;
       document.getElementById("messageSection").style.display="block";
      
        
       
    }else if((adresse.length <5) || (adresse.length >50 )){
        if(nom.length =0){
           message ="La saisie de l'adresse est obligatoire";
     
        }else{
        message ="La taille de l'adresse doit etre entre 5 et 50 caractères";
        }

       document.getElementById("bodyModel").style.backgroundColor ="red"; 
       document.getElementById("bodyModel").innerHTML = message;
       document.getElementById("messageSection").style.display="block";
       
      
       
    }else if(!validateEmail(email)){
     document.getElementById("bodyModel").style.backgroundColor ="red"; 

       document.getElementById("bodyModel").innerHTML = "Veuillez respecter le format d'une adresse e-mail";
       document.getElementById("messageSection").style.display="block";
       
   
       
    }else{
     document.getElementById("bodyModel").style.backgroundColor ="green"; 

       
       document.getElementById("bodyModel").innerHTML = "Bienvenue " + document.querySelector("#nom").value +" "+ document.querySelector("#prenom").value;
       document.getElementById("messageSection").style.display="block";
       
       

    };
    
    


   
   
}
//---------------------------------------------------------------------------------------------------------------------
function validateEmail(email) {
   const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
   return re.test(String(email).toLowerCase());

}

//----------------------------------------------------------------------------------------------------------------------


$(function () {

   $('.datepicker').datepicker({
       clearBtn: true,
       format: "dd/mm/yyyy"
   });

});