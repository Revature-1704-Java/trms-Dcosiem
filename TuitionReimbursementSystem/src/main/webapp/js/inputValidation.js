function validateForm() {
    var x = document.forms["myForm"]["employeeid"].value;
    if (x == "") {
        alert("Name must be filled out");
        return false;
    }
}