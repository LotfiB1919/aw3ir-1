window.onload = function () {   

  const gps = document.getElementById("gps");
  gps.addEventListener("click",getLocation,false);

  function validateEmail(email) {
    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
  } 
};

function calcNbChar(id) {
  document.querySelector(`#${id} + span`).textContent = document.querySelector(`#${id}`).value.length + " car.";
}

function check_date(){
  var d = document.getElementById("dateofbirth").value.split('-');
  var date = new Date(parseInt(d[0],10),parseInt(d[1],10)-1,parseInt(d[2],10));
  var today = new Date();
  if (date.getTime() > today){
    document.getElementById("daterror").style.display = "block";
    document.getElementById("dateofbirth").value = '';
  }
  else {
    document.getElementById("daterror").style.display = "none";
  }

}

function show_data(){
  var contactList = contactStore.getList()
  for (var index in contactList) {
    console.log(contactList[index].name+" "+contactList[index].firstname+" "+contactList[index].date+" "+contactList[index].adress+" "+contactList[index].mail);
  }
}


function addcontact(){
  var name = document.getElementById('nom').value;
  var first_name = document.getElementById('prenom').value;
  var date_of_birth = document.getElementById("dateofbirth").value;
  var address = document.getElementById("address").value;
  var mail = document.getElementById("mail").value;
  contactStore.add(name,first_name,date_of_birth,address,mail);
  document.getElementById("contactlist").getElementsByTagName("tbody")[0].innerHTML = 
  document.getElementById("contactlist").getElementsByTagName("tbody")[0].innerHTML +
  "<tr><td>" +
  name +
  "</td><td>" +
  first_name +
  "</td><td>" +
  date_of_birth +
  "</td><td>" +
  address +
  "</td><td>"+
  mail +
  "</td></tr>";
  document.getElementById("form").reset();
  calcNbChar("nom");
  calcNbChar("prenom");
  show_data();
}